package com.ruoyi.project.devsys.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 设备定值对象 dev_constval
 * @author wulei
 * @date 2020-06-15
 */
public class DevConstval extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 定值ID */
    private Long constvalId;

    /** 定值名称 */
    @Excel(name = "定值名称")
    private String constvalName;

    /** 设备ID */
    @Excel(name = "设备ID")
    private Long equipId;

    /** 原定值 */
    @Excel(name = "原定值")
    private String oldValue;

    /** 新定值 */
    @Excel(name = "新定值")
    private String newValue;

    /** 执行者 */
    @Excel(name = "执行者")
    private String worker;

    /** 监督者 */
    @Excel(name = "监督者")
    private String supervisor;

    /** 要求执行时间 */
    @Excel(name = "要求执行时间", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date requireTime;

    /** 实际执行时间 */
    @Excel(name = "实际执行时间", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date actualTime;

    /** 最大值 */
    @Excel(name = "最大值")
    private String maxValue;

    /** 最小值 */
    @Excel(name = "最小值")
    private String minValue;

    /** 附件名称 */
    private String fname;

    /** 附件路径 */
    private String fpath;

    public void setConstvalId(Long constvalId) 
    {
        this.constvalId = constvalId;
    }

    public Long getConstvalId() 
    {
        return constvalId;
    }
    public void setConstvalName(String constvalName) 
    {
        this.constvalName = constvalName;
    }

    public String getConstvalName() 
    {
        return constvalName;
    }
    public void setEquipId(Long equipId) 
    {
        this.equipId = equipId;
    }

    public Long getEquipId() 
    {
        return equipId;
    }
    public void setOldValue(String oldValue) 
    {
        this.oldValue = oldValue;
    }

    public String getOldValue() 
    {
        return oldValue;
    }
    public void setNewValue(String newValue) 
    {
        this.newValue = newValue;
    }

    public String getNewValue() 
    {
        return newValue;
    }
    public void setWorker(String worker) 
    {
        this.worker = worker;
    }

    public String getWorker() 
    {
        return worker;
    }
    public void setSupervisor(String supervisor) 
    {
        this.supervisor = supervisor;
    }

    public String getSupervisor() 
    {
        return supervisor;
    }
    public void setRequireTime(Date requireTime) 
    {
        this.requireTime = requireTime;
    }

    public Date getRequireTime() 
    {
        return requireTime;
    }
    public void setActualTime(Date actualTime) 
    {
        this.actualTime = actualTime;
    }

    public Date getActualTime() 
    {
        return actualTime;
    }
    public void setMaxValue(String maxValue) 
    {
        this.maxValue = maxValue;
    }

    public String getMaxValue() 
    {
        return maxValue;
    }
    public void setMinValue(String minValue) 
    {
        this.minValue = minValue;
    }

    public String getMinValue() 
    {
        return minValue;
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
            .append("constvalId", getConstvalId())
            .append("constvalName", getConstvalName())
            .append("equipId", getEquipId())
            .append("oldValue", getOldValue())
            .append("newValue", getNewValue())
            .append("worker", getWorker())
            .append("supervisor", getSupervisor())
            .append("requireTime", getRequireTime())
            .append("actualTime", getActualTime())
            .append("maxValue", getMaxValue())
            .append("minValue", getMinValue())
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
