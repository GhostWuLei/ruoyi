package com.ruoyi.project.devsys.service.impl;

import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.framework.config.RuoYiConfig;
import com.ruoyi.project.devsys.domain.DevAlteration;
import com.ruoyi.project.devsys.domain.DevEquip;
import com.ruoyi.project.devsys.domain.DevFile;
import com.ruoyi.project.devsys.mapper.DevAlterationMapper;
import com.ruoyi.project.devsys.service.IDevAlterationService;
import com.ruoyi.project.devsys.service.IDevFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 设备变更Service业务层处理
 *
 * @author wulei
 * @date 2020-10-30
 */
@Service
public class DevAlterationServiceImpl implements IDevAlterationService
{
    @Autowired
    private DevAlterationMapper devAlterationMapper;
    @Autowired
    private IDevFileService fileService;

    /**
     * 查询设备变更
     *
     * @param alterationId 设备变更ID
     * @return 设备变更
     */
    @Override
    public DevAlteration selectDevAlterationById(Long alterationId)
    {
        return devAlterationMapper.selectDevAlterationById(alterationId);
    }

    /**
     * 查询设备变更列表
     *
     * @param devAlteration 设备变更
     * @return 设备变更
     */
    @Override
    public List<DevAlteration> selectDevAlterationList(DevAlteration devAlteration)
    {
        return devAlterationMapper.selectDevAlterationList(devAlteration);
    }

    /**
     * 新增设备变更
     *
     * @param devAlteration 设备变更
     * @return 结果
     */
    @Override
    public int insertDevAlteration(DevAlteration devAlteration)
    {
        devAlteration.setCreateTime(DateUtils.getNowDate());
        return devAlterationMapper.insertDevAlteration(devAlteration);
    }

    /**
     * 修改设备变更
     *
     * @param devAlteration 设备变更
     * @return 结果
     */
    @Override
    public int updateDevAlteration(DevAlteration devAlteration)
    {
        devAlteration.setUpdateTime(DateUtils.getNowDate());
        return devAlterationMapper.updateDevAlteration(devAlteration);
    }

    /**
     * 批量删除设备变更
     *
     * @param alterationIds 需要删除的设备变更ID
     * @return 结果
     */
    @Override
    public int deleteDevAlterationByIds(Long[] alterationIds)
    {
        return devAlterationMapper.deleteDevAlterationByIds(alterationIds);
    }

    /**
     * 删除设备变更信息
     *
     * @param alterationId 设备变更ID
     * @return 结果
     */
    @Override
    public int deleteDevAlterationById(Long alterationId)
    {
        return devAlterationMapper.deleteDevAlterationById(alterationId);
    }

    @Override
    public boolean uploadFile(Long alterationId, MultipartFile[] files) {
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                // 兼容IE
                String fname = file.getOriginalFilename(); // IE浏览器返回的是路径 chrome浏览器返回的是文件名加后缀
                int unixSep = fname.lastIndexOf("/");
                int winSep = fname.lastIndexOf("\\");
                int pos = (winSep > unixSep ? winSep : unixSep);
                if (pos != -1) {
                    fname = fname.substring(pos + 1);
                }
                try {
                    String fpath = FileUploadUtils.upload(RuoYiConfig.getalteration(), file);
                    String substring = fpath.substring(0, fpath.lastIndexOf("/"));
                    List<DevFile> devFiles = fileService.selectalterationIdById(alterationId);
                    for (DevFile devFile : devFiles) {
                        String fpath1 = devFile.getFpath();
                        String substring1 = fpath1.substring(0, fpath.lastIndexOf("/"));
                        if (devFile.getFname().equals(file.getOriginalFilename())||devFile.getAlterationId().equals(alterationId)) {
                            if(substring.equals(substring1)){
                                fileService.deleteDevFileByFileId(devFile.getFileId());
                                fileService.deleteAnne(devFile.getFpath());
                            }
                        }
                    }
                    DevFile devFile = new DevFile();
                    devFile.setAlterationId(alterationId);
                    devFile.setFname(file.getOriginalFilename());
                    devFile.setFpath(fpath);
                    fileService.insertDevFile(devFile);
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }

            }
        }
        return true;
    }

    @Override
    public String importUser(List<DevAlteration> devAlterationList, boolean updateSupport, String username, Long equipId) {
        if(StringUtils.isNull(devAlterationList)|| devAlterationList.size() == 0){
            throw new CustomException("导入的数据不能为空");
        }
        int insertNum = 0;
        int updateNum = 0;
        int repeatNum = 0;
        StringBuilder successMsg = new StringBuilder();
        for (DevAlteration devAlteration : devAlterationList) {
            DevAlteration devSpareName = devAlterationMapper.selectByName(devAlteration.getEquipName());
            if (StringUtils.isNull(devSpareName)) {
                devAlteration.setCreateBy(username);
                devAlteration.setEquipId(equipId);
                this.insertDevAlteration(devAlteration);
                insertNum++;
            } else {
                if (updateSupport) {
                    //允许修改
                    devAlteration.setEquipId(equipId);
                    devAlteration.setUpdateBy(username);
                    this.updateDevAlteration(devAlteration);
                    updateNum++;
                } else {
                    repeatNum++;
                }
            }
        }
        successMsg.insert(0, "导入数据已完成！新增"+insertNum+"条，更新"+updateNum+"条，"+repeatNum+"条数据已存在，未修改");
        return successMsg.toString();
    }

    @Override
    public List<DevAlteration> selectDevAlterationListIn(List<DevEquip> devEquips) {
        List<Long> list=new ArrayList<>();
        for (DevEquip devEquip : devEquips) {
            list.add(devEquip.getEquipId());
            list.add(devEquip.getParentId());
        }
        List<DevAlteration> devAlterations=devAlterationMapper.selectDevAlterationListIn(list);
        return devAlterations;
    }

    @Override
    public int deleteequipId(Long equipId) {
        return devAlterationMapper.deleteequipId(equipId);
    }
}
