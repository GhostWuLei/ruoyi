package com.ruoyi.project.devsys.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 异动变更对象 dev_alteration
 * 
 * @author wulei
 * @date 2020-06-16
 */
public class DevAlteration extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 变更ID */
    private Long alterationId;

    /** 变更名称 */
    @Excel(name = "变更名称")
    private String alterationName;

    /** 设备ID */
    private Long equipId;

    /** 变更内容 */
    @Excel(name = "变更内容")
    private String alterationContent;

    /** 变更类别 */
    @Excel(name = "变更类别")
    private String alterationType;

    /** 变更时间 */
    @Excel(name = "变更时间", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date alterationTime;

    /** 申请者 */
    @Excel(name = "申请者")
    private String applyer;

    /** 完成情况 */
    @Excel(name = "完成情况")
    private String completeStatus;

    /** 验收时间 */
    @Excel(name = "验收时间", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date checkDate;

    /** 验收者 */
    @Excel(name = "验收者")
    private String checkMan;

    /** 专业 */
    @Excel(name = "专业")
    private String major;

    /** 附件名称 */
    private String fname;

    /** 附件路径 */
    private String fpath;

    public void setAlterationId(Long alterationId) 
    {
        this.alterationId = alterationId;
    }

    public Long getAlterationId() 
    {
        return alterationId;
    }
    public void setAlterationName(String alterationName) 
    {
        this.alterationName = alterationName;
    }

    public String getAlterationName() 
    {
        return alterationName;
    }
    public void setEquipId(Long equipId) 
    {
        this.equipId = equipId;
    }

    public Long getEquipId() 
    {
        return equipId;
    }
    public void setAlterationContent(String alterationContent) 
    {
        this.alterationContent = alterationContent;
    }

    public String getAlterationContent() 
    {
        return alterationContent;
    }
    public void setAlterationType(String alterationType) 
    {
        this.alterationType = alterationType;
    }

    public String getAlterationType() 
    {
        return alterationType;
    }
    public void setAlterationTime(Date alterationTime) 
    {
        this.alterationTime = alterationTime;
    }

    public Date getAlterationTime() 
    {
        return alterationTime;
    }
    public void setApplyer(String applyer) 
    {
        this.applyer = applyer;
    }

    public String getApplyer() 
    {
        return applyer;
    }
    public void setCompleteStatus(String completeStatus) 
    {
        this.completeStatus = completeStatus;
    }

    public String getCompleteStatus() 
    {
        return completeStatus;
    }
    public void setCheckDate(Date checkDate) 
    {
        this.checkDate = checkDate;
    }

    public Date getCheckDate() 
    {
        return checkDate;
    }
    public void setCheckMan(String checkMan) 
    {
        this.checkMan = checkMan;
    }

    public String getCheckMan() 
    {
        return checkMan;
    }
    public void setMajor(String major) 
    {
        this.major = major;
    }

    public String getMajor() 
    {
        return major;
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
            .append("alterationId", getAlterationId())
            .append("alterationName", getAlterationName())
            .append("equipId", getEquipId())
            .append("alterationContent", getAlterationContent())
            .append("alterationType", getAlterationType())
            .append("alterationTime", getAlterationTime())
            .append("applyer", getApplyer())
            .append("completeStatus", getCompleteStatus())
            .append("checkDate", getCheckDate())
            .append("checkMan", getCheckMan())
            .append("major", getMajor())
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
