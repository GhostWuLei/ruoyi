package com.ruoyi.project.devsys.service.impl;

import com.ruoyi.project.devsys.domain.DevFile;
import com.ruoyi.project.devsys.mapper.DevFileMapper;
import com.ruoyi.project.devsys.service.IDevFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        System.out.println("update devfile"+devFile);
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
    public int deleteDevFileById(Long spareId) {
        return mapper.deleteDevFileById(spareId);
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
}
