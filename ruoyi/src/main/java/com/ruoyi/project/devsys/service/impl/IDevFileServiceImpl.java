package com.ruoyi.project.devsys.service.impl;


import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.config.RuoYiConfig;
import com.ruoyi.project.devsys.domain.DevFile;
import com.ruoyi.project.devsys.domain.DevSpare;
import com.ruoyi.project.devsys.domain.DevSubsidiary;
import com.ruoyi.project.devsys.mapper.DevFileMapper;
import com.ruoyi.project.devsys.service.IDevFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class IDevFileServiceImpl implements IDevFileService {
    @Autowired
    private DevFileMapper mapper;
    @Override
    public int insertDevFile(DevFile devFile) {
        return mapper.insertDevFile(devFile);
    }

    @Override
    public int updateDevFile(DevFile devFile) {
        return mapper.updateDevFile(devFile);
    }

    @Override
    public List<DevFile> selectDevFileList(DevFile devFile) {
        return mapper.selectDevFileList(devFile);
    }

    @Override
    public List<DevFile> selectDevFileById(Long spareId) {
        return mapper.selectDevFileById(spareId);
    }


    @Override
    public DevFile selectDevFileByName(String fname) {
        return mapper.selectDevFileByName(fname);
    }

    @Override
    public void deleteDevFileByFileId(Long fileId) {
         mapper.deleteDevFileByFileId(fileId);
    }

    @Override
    public DevFile selectDevFileid(Long fileId) {
        return mapper.selectDevFileid(fileId);
    }

    @Override
    public void deleteAnne(String fpath) {
        //将相对路径转换为绝对路径
        String newPath = fpath.replaceAll(Constants.RESOURCE_PREFIX, RuoYiConfig.getProfile());
        File file = new File(newPath);
        if(file.exists()){
            if(!file.delete()){
                throw new CustomException("删除失败", 401);
            }
        }
    }

    @Override
    public List<DevFile> selectinformationIdById(Long informationId) {
        return mapper.selectinformationIdById(informationId);
    }

    @Override
    public List<DevFile> selectrepairFileById(Long repairId) {
        return mapper.selectrepairFileById(repairId);
    }

    @Override
    public List<DevFile> selectfaultById(Long faultId) {
        return mapper.selectfaultById(faultId);
    }

    @Override
    public List<DevFile> selectalterationIdById(Long alterationId) {
        return mapper.selectalterationIdById(alterationId);
    }

    @Override
    public List<DevFile> selectsubsidiaryIdById(Long subsidiaryId) {
        return mapper.selectsubsidiaryIdById(subsidiaryId);
    }



}
