package com.ruoyi.project.devsys.mapper;

import com.ruoyi.project.devsys.domain.DevFile;
import com.ruoyi.project.devsys.domain.DevKks;
import com.ruoyi.project.devsys.domain.DevSubsidiary;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;
public interface DevFileMapper{
    public int insertDevFile(DevFile devFile);

    public int updateDevFile(DevFile devFile);

    public List<DevFile> selectDevFileList(DevFile devFile);

    public List<DevFile> selectDevFileById(@Param("spareId") Long spareId);

    public DevFile selectDevFileByName(@Param("fname") String fname);

    public void deleteDevFileByFileId(@Param("fileId") Long fileId);

    public DevFile selectDevFileid(@Param("fileId") Long fileId);

    List<DevFile> selectinformationIdById(@Param("informationId") Long informationId);

    List<DevFile> selectrepairFileById(@Param("repairId") Long repairId);

    List<DevFile> selectfaultById(@Param("faultId") Long faultId);

    List<DevFile> selectalterationIdById(Long alterationId);

    List<DevFile> selectsubsidiaryIdById(Long subsidiaryId);

}
