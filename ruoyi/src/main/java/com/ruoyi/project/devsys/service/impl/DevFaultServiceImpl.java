package com.ruoyi.project.devsys.service.impl;

import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.framework.config.RuoYiConfig;
import com.ruoyi.project.devsys.domain.DevEquip;
import com.ruoyi.project.devsys.domain.DevFault;
import com.ruoyi.project.devsys.domain.DevFile;
import com.ruoyi.project.devsys.mapper.DevEquipMapper;
import com.ruoyi.project.devsys.mapper.DevFaultMapper;
import com.ruoyi.project.devsys.service.IDevFaultService;
import com.ruoyi.project.devsys.service.IDevFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 故障记录Service业务层处理
 *
 * @author wulei
 * @date 2020-10-30
 */
@Service
public class DevFaultServiceImpl implements IDevFaultService
{
    @Autowired
    private DevFaultMapper devFaultMapper;
    @Autowired
    private IDevFileService fileService;
    @Autowired
    private DevEquipMapper devEquipMapper;

    /**
     * 查询故障记录
     *
     * @param faultId 故障记录ID
     * @return 故障记录
     */
    @Override
    public DevFault selectDevFaultById(Long faultId)
    {
        return devFaultMapper.selectDevFaultById(faultId);
    }

    /**
     * 查询故障记录列表
     *
     * @param devFault 故障记录
     * @return 故障记录
     */
    @Override
    public List<DevFault> selectDevFaultList(DevFault devFault)
    {
        return devFaultMapper.selectDevFaultList(devFault);
    }

    /**
     * 新增故障记录
     *
     * @param devFault 故障记录
     * @return 结果
     */
    @Override
    public int insertDevFault(DevFault devFault)
    {
        devFault.setCreateTime(DateUtils.getNowDate());
        return devFaultMapper.insertDevFault(devFault);
    }

    /**
     * 修改故障记录
     *
     * @param devFault 故障记录
     * @return 结果
     */
    @Override
    public int updateDevFault(DevFault devFault)
    {
        devFault.setUpdateTime(DateUtils.getNowDate());
        return devFaultMapper.updateDevFault(devFault);
    }

    /**
     * 批量删除故障记录
     *
     * @param faultIds 需要删除的故障记录ID
     * @return 结果
     */
    @Override
    public int deleteDevFaultByIds(Long[] faultIds)
    {
        return devFaultMapper.deleteDevFaultByIds(faultIds);
    }

    /**
     * 删除故障记录信息
     *
     * @param faultId 故障记录ID
     * @return 结果
     */
    @Override
    public int deleteDevFaultById(Long faultId)
    {
        return devFaultMapper.deleteDevFaultById(faultId);
    }

    @Override
    public boolean uploadFile(Long faultId, MultipartFile[] files) {
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
                    String fpath = FileUploadUtils.upload(RuoYiConfig.getfault(), file);
                    String substring = fpath.substring(0, fpath.lastIndexOf("/"));
                    List<DevFile> devFiles = fileService.selectfaultById(faultId);
                    for (DevFile devFile : devFiles) {
                        String fpath1 = devFile.getFpath();
                        String substring1 = fpath1.substring(0, fpath.lastIndexOf("/"));
                        if (devFile.getFname().equals(file.getOriginalFilename())) {
                            if(substring.equals(substring1)|| devFile.getFaultId().equals(faultId)){
                                fileService.deleteDevFileByFileId(devFile.getFileId());
                                fileService.deleteAnne(devFile.getFpath());
                            }
                        }
                    }
                    DevFile devFile = new DevFile();
                    devFile.setFaultId(faultId);
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
    public String importUser(List<DevFault> devFaultList, boolean updateSupport, String username, Long equipId) {
        if(StringUtils.isNull(devFaultList)|| devFaultList.size() == 0){
            throw new CustomException("导入的数据不能为空");
        }
        int insertNum = 0;
        int updateNum = 0;
        int repeatNum = 0;
        StringBuilder successMsg = new StringBuilder();
        for (DevFault devFault : devFaultList) {
            DevFault devSpareName = devFaultMapper.selectByName(devFault.getAppearance());
            if (StringUtils.isNull(devSpareName)) {
                devFault.setCreateBy(username);
                devFault.setEquipId(equipId);
                this.insertDevFault(devFault);
                insertNum++;
            } else {
                if (updateSupport) {
                    //允许修改
                    devFault.setEquipId(equipId);
                    devFault.setUpdateBy(username);
                    this.updateDevFault(devFault);
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
    public List<DevFault> selectDevFaultListIn(List<DevEquip> devEquips) {
        List<Long> list=new ArrayList<>();
        for (DevEquip devEquip : devEquips) {
            list.add(devEquip.getParentId());
            list.add(devEquip.getEquipId());
        }
        return devFaultMapper.selectDevFaultListIn(list);
    }

    @Override
    public int deleteequipId(Long equipId) {
        return devFaultMapper.deleteequipId(equipId);
    }
}
