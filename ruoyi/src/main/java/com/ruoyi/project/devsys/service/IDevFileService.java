package com.ruoyi.project.devsys.service;

import com.ruoyi.project.devsys.domain.DevFile;
import com.ruoyi.project.devsys.domain.DevSubsidiary;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IDevFileService {
    public int insertDevFile(DevFile devFile);
    public int updateDevFile(DevFile devFile);
    public List<DevFile> selectDevFileList(DevFile devFile);
    public List<DevFile> selectDevFileById(Long spareId);
    public DevFile selectDevFileByName(String fname);
    void deleteDevFileByFileId(Long fileId);
    DevFile selectDevFileid(Long fileId);

    void deleteAnne(String fpath);


    List<DevFile> selectinformationIdById(Long informationId);

    List<DevFile> selectrepairFileById(Long repairId);

    List<DevFile> selectfaultById(Long faultId);

    List<DevFile> selectalterationIdById(Long alterationId);

    List<DevFile> selectsubsidiaryIdById(Long subsidiaryId);

}
