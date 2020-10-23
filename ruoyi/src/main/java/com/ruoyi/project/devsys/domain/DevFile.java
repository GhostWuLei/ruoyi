package com.ruoyi.project.devsys.domain;

import com.ruoyi.framework.web.domain.BaseEntity;
import lombok.Data;

@Data
public class DevFile {
    private static final long serialVersionUID = 1L;
    private Long fileId;

    private Long spareId;

    private Long repairId;

    private String fname;

    private String fpath;

}
