package com.ruoyi.project.devsys.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 重大技改对象 dev_reform
 * 
 * @author wulei
 * @date 2020-06-16
 */
public class DevReform extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 技改ID */
    private Long reformId;

    /** 技改名称 */
    @Excel(name = "技改名称")
    private String reformName;

    /** 设备ID */
    private Long equipId;

    /** 专业 */
    @Excel(name = "专业")
    private String major;

    /** 完成情况 */
    @Excel(name = "完成情况")
    private String completeStatus;

    /** 执行单位 */
    @Excel(name = "执行单位")
    private String workUnit;

    /** 完成时间 */
    @Excel(name = "完成时间", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date finishTime;

    /** 是否验收 */
    @Excel(name = "是否验收")
    private String isCheck;

    /** 验收者 */
    @Excel(name = "验收者")
    private String checker;

    /** 附件名称 */
    private String fname;

    /** 附件路径 */
    private String fpath;

    public void setReformId(Long reformId) 
    {
        this.reformId = reformId;
    }

    public Long getReformId() 
    {
        return reformId;
    }
    public void setReformName(String reformName) 
    {
        this.reformName = reformName;
    }

    public String getReformName() 
    {
        return reformName;
    }
    public void setEquipId(Long equipId) 
    {
        this.equipId = equipId;
    }

    public Long getEquipId() 
    {
        return equipId;
    }
    public void setMajor(String major) 
    {
        this.major = major;
    }

    public String getMajor() 
    {
        return major;
    }
    public void setCompleteStatus(String completeStatus) 
    {
        this.completeStatus = completeStatus;
    }

    public String getCompleteStatus() 
    {
        return completeStatus;
    }
    public void setWorkUnit(String workUnit) 
    {
        this.workUnit = workUnit;
    }

    public String getWorkUnit() 
    {
        return workUnit;
    }
    public void setFinishTime(Date finishTime) 
    {
        this.finishTime = finishTime;
    }

    public Date getFinishTime() 
    {
        return finishTime;
    }
    public void setIsCheck(String isCheck) 
    {
        this.isCheck = isCheck;
    }

    public String getIsCheck() 
    {
        return isCheck;
    }
    public void setChecker(String checker) 
    {
        this.checker = checker;
    }

    public String getChecker() 
    {
        return checker;
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
            .append("reformId", getReformId())
            .append("reformName", getReformName())
            .append("equipId", getEquipId())
            .append("major", getMajor())
            .append("completeStatus", getCompleteStatus())
            .append("workUnit", getWorkUnit())
            .append("finishTime", getFinishTime())
            .append("isCheck", getIsCheck())
            .append("checker", getChecker())
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
