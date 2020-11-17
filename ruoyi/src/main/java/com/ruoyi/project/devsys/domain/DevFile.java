package com.ruoyi.project.devsys.domain;

import com.ruoyi.framework.web.domain.BaseEntity;
import lombok.Data;

@Data
public class DevFile {
    private static final long serialVersionUID = 1L;
    private Long fileId;//文件id

    private Long spareId;//备品备件id

    private Long repairId;//检修记录id

    private Long faultId;//故障报记录id

    private Long alterationId;//异动变更id

    private Long subsidiaryId;//附属设备明细id

    private Long informationId;//设备信息id

    private Long equipId;

    private String fname;

    private String fpath;

}
