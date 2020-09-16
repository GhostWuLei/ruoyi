package com.ruoyi.project.devsys.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 检修记录对象 dev_repair
 * 
 * @author wulei
 * @date 2020-06-15
 */
public class DevRepair extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 检修ID */
    private Long repairId;

    /** 检修名称 */
    @Excel(name = "检修名称")
    private String repairName;

    /** 设备ID */
    private Long equipId;

    /** 检修时间 */
    @Excel(name = "检修时间", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date repairDate;

    /** 检修内容 */
    @Excel(name = "检修内容")
    private String repairContent;

    /** 检修单位 */
    @Excel(name = "检修单位")
    private String workUnit;

    /** 检修性质 */
    @Excel(name = "检修性质")
    private String repairKind;

    /** 检修类型 */
    @Excel(name = "检修类型")
    private String repairType;

    /** 附件名称 */
    private String fname;

    /** 附件路径 */
    private String fpath;

    public void setRepairId(Long repairId) 
    {
        this.repairId = repairId;
    }

    public Long getRepairId() 
    {
        return repairId;
    }
    public void setRepairName(String repairName) 
    {
        this.repairName = repairName;
    }

    public String getRepairName() 
    {
        return repairName;
    }
    public void setEquipId(Long equipId) 
    {
        this.equipId = equipId;
    }

    public Long getEquipId() 
    {
        return equipId;
    }
    public void setRepairDate(Date repairDate) 
    {
        this.repairDate = repairDate;
    }

    public Date getRepairDate() 
    {
        return repairDate;
    }
    public void setRepairContent(String repairContent) 
    {
        this.repairContent = repairContent;
    }

    public String getRepairContent() 
    {
        return repairContent;
    }
    public void setWorkUnit(String workUnit) 
    {
        this.workUnit = workUnit;
    }

    public String getWorkUnit() 
    {
        return workUnit;
    }
    public void setRepairKind(String repairKind) 
    {
        this.repairKind = repairKind;
    }

    public String getRepairKind() 
    {
        return repairKind;
    }
    public void setRepairType(String repairType) 
    {
        this.repairType = repairType;
    }

    public String getRepairType() 
    {
        return repairType;
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
            .append("repairId", getRepairId())
            .append("repairName", getRepairName())
            .append("equipId", getEquipId())
            .append("repairDate", getRepairDate())
            .append("repairContent", getRepairContent())
            .append("workUnit", getWorkUnit())
            .append("repairKind", getRepairKind())
            .append("repairType", getRepairType())
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
