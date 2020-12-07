package com.ruoyi.project.devsys.service.impl;

import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.framework.config.RuoYiConfig;
import com.ruoyi.project.devsys.domain.DevEquip;
import com.ruoyi.project.devsys.domain.DevFile;
import com.ruoyi.project.devsys.domain.DevSubsidiary;
import com.ruoyi.project.devsys.mapper.DevSubsidiaryMapper;
import com.ruoyi.project.devsys.service.IDevFileService;
import com.ruoyi.project.devsys.service.IDevSubsidiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 附属设备明细Service业务层处理
 *
 * @author wulei
 * @date 2020-10-30
 */
@Service
public class DevSubsidiaryServiceImpl implements IDevSubsidiaryService
{
    @Autowired
    private DevSubsidiaryMapper devSubsidiaryMapper;
    @Autowired
    private IDevFileService fileService;

    /**
     * 查询附属设备明细
     *
     * @param subsidiaryId 附属设备明细ID
     * @return 附属设备明细
     */
    @Override
    public DevSubsidiary selectDevSubsidiaryById(Long subsidiaryId)
    {
        return devSubsidiaryMapper.selectDevSubsidiaryById(subsidiaryId);
    }

    /**
     * 查询附属设备明细列表
     *
     * @param devSubsidiary 附属设备明细
     * @return 附属设备明细
     */
    @Override
    public List<DevSubsidiary> selectDevSubsidiaryList(DevSubsidiary devSubsidiary)
    {
        return devSubsidiaryMapper.selectDevSubsidiaryList(devSubsidiary);
    }

    /**
     * 新增附属设备明细
     *
     * @param devSubsidiary 附属设备明细
     * @return 结果
     */
    @Override
    public int insertDevSubsidiary(DevSubsidiary devSubsidiary)
    {
        devSubsidiary.setCreateTime(DateUtils.getNowDate());
        return devSubsidiaryMapper.insertDevSubsidiary(devSubsidiary);
    }

    /**
     * 修改附属设备明细
     *
     * @param devSubsidiary 附属设备明细
     * @return 结果
     */
    @Override
    public int updateDevSubsidiary(DevSubsidiary devSubsidiary)
    {
        devSubsidiary.setUpdateTime(DateUtils.getNowDate());
        return devSubsidiaryMapper.updateDevSubsidiary(devSubsidiary);
    }

    /**
     * 批量删除附属设备明细
     *
     * @param subsidiaryIds 需要删除的附属设备明细ID
     * @return 结果
     */
    @Override
    public int deleteDevSubsidiaryByIds(Long[] subsidiaryIds)
    {
        return devSubsidiaryMapper.deleteDevSubsidiaryByIds(subsidiaryIds);
    }

    /**
     * 删除附属设备明细信息
     *
     * @param subsidiaryId 附属设备明细ID
     * @return 结果
     */
    @Override
    public int deleteDevSubsidiaryById(Long subsidiaryId)
    {
        return devSubsidiaryMapper.deleteDevSubsidiaryById(subsidiaryId);
    }

    @Override
    public boolean uploadFile(Long subsidiaryId, MultipartFile[] files) {
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
                    String fpath = FileUploadUtils.upload(RuoYiConfig.getsubsidiary(), file);
                    String substring = fpath.substring(0, fpath.lastIndexOf("/"));
                    List<DevFile> devFiles = fileService.selectsubsidiaryIdById(subsidiaryId);
                    for (DevFile devFile : devFiles) {
                        String fpath1 = devFile.getFpath();
                        String substring1 = fpath1.substring(0, fpath.lastIndexOf("/"));
                        if (devFile.getFname().equals(file.getOriginalFilename())) {
                            if(substring.equals(substring1)|| devFile.getSubsidiaryId().equals(subsidiaryId)){
                                fileService.deleteDevFileByFileId(devFile.getFileId());
                                fileService.deleteAnne(devFile.getFpath());
                            }
                        }
                    }
                    DevFile devFile = new DevFile();
                    devFile.setSubsidiaryId(subsidiaryId);
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
    public String importUser(List<DevSubsidiary> devSubsidiaries, boolean updateSupport, String username, Long equipId) {
        if(StringUtils.isNull(devSubsidiaries)|| devSubsidiaries.size() == 0){
            throw new CustomException("导入的数据不能为空");
        }
        int insertNum = 0;
        int updateNum = 0;
        int repeatNum = 0;
        StringBuilder successMsg = new StringBuilder();
        for (DevSubsidiary devSubsidiary : devSubsidiaries) {
            DevSubsidiary Name = devSubsidiaryMapper.selectName(devSubsidiary.getEquipName());
            if (StringUtils.isNull(Name)) {
                devSubsidiary.setCreateBy(username);
                devSubsidiary.setEquipId(equipId);
                this.insertDevSubsidiary(devSubsidiary);
                insertNum++;
            } else {
                if (updateSupport) {
                    //允许修改
                    devSubsidiary.setEquipId(equipId);
                    devSubsidiary.setCreateBy(username);
                    this.updateDevSubsidiary(devSubsidiary);
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
    public List<DevSubsidiary> selectDevSubsidiaryListIn(List<DevEquip> devEquipList) {
        List<Long> list=new ArrayList<>();
        for (DevEquip devEquip : devEquipList) {
            list.add(devEquip.getParentId());
            list.add(devEquip.getEquipId());
        }
        List<DevSubsidiary>  devSubsidiaries=devSubsidiaryMapper.selectDevSubsidiaryListIn(list);
        return devSubsidiaries;
    }

    @Override
    public int deleteequipId(Long equipId) {
        return devSubsidiaryMapper.deleteequipId(equipId);
    }
}
