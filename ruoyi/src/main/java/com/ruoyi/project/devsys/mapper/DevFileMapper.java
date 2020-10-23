package com.ruoyi.project.devsys.mapper;


import com.ruoyi.project.devsys.domain.DevFile;
import com.ruoyi.project.devsys.domain.DevKks;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

public interface DevFileMapper {
    public int insertDevFile(DevFile devFile);

    public int updateDevFile(DevFile devFile);

    public List<DevFile> selectDevFileList(DevFile devFile);

    public List<DevFile> selectDevFileById(@Param("spareId") Long spareId);

    public int deleteDevFileById(@Param("spareId") Long spareId);

    public DevFile selectDevFileByName(@Param("fname") String fname);

    public void deleteDevFileByFileId(@Param("fileId") Long fileId);

    public DevFile selectDevFileid(@Param("fileId") Long fileId);
}
