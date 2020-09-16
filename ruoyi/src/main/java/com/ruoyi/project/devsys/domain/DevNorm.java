package com.ruoyi.project.devsys.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 设备规范对象 dev_norm
 * 
 * @author wulei
 * @date 2020-06-16
 */
public class DevNorm extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 规范ID */
    private Long normId;

    /** 规范名称 */
    @Excel(name = "规范名称")
    private String normName;

    /** 设备ID */
    private Long equipId;

    /** 规范值 */
    @Excel(name = "规范值")
    private String normValue;

    /** 计量单位 */
    @Excel(name = "计量单位")
    private String normUnit;

    /** 附件名称 */
    private String fname;

    /** 附件路径 */
    private String fpath;

    public void setNormId(Long normId) 
    {
        this.normId = normId;
    }

    public Long getNormId() 
    {
        return normId;
    }
    public void setNormName(String normName) 
    {
        this.normName = normName;
    }

    public String getNormName() 
    {
        return normName;
    }
    public void setEquipId(Long equipId) 
    {
        this.equipId = equipId;
    }

    public Long getEquipId() 
    {
        return equipId;
    }
    public void setNormValue(String normValue) 
    {
        this.normValue = normValue;
    }

    public String getNormValue() 
    {
        return normValue;
    }
    public void setNormUnit(String normUnit) 
    {
        this.normUnit = normUnit;
    }

    public String getNormUnit() 
    {
        return normUnit;
    }
    public void setFname(String fname) 
    {
        this.fname = fname;
    }

    public String getFname() 
    {
        return fname;
    }
    public void setFpath(String fpath) 
    {
        this.fpath = fpath;
    }

    public String getFpath() 
    {
        return fpath;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("normId", getNormId())
            .append("normName", getNormName())
            .append("equipId", getEquipId())
            .append("normValue", getNormValue())
            .append("normUnit", getNormUnit())
            .append("fname", getFname())
            .append("fpath", getFpath())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
