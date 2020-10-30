package com.ruoyi.project.devsys.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 故障记录对象 dev_fault
 * 
 * @author wulei
 * @date 2020-10-30
 */
public class DevFault extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 故障ID */
    private Long faultId;

    /** 设备ID */
    private Long equipId;

    /** 发现日期 */
    @Excel(name = "发现日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date findTime;

    /** 故障现象 */
    @Excel(name = "故障现象")
    private String appearance;

    /** 分析 */
    @Excel(name = "分析")
    private String analysis;

    /** 处理日期 */
    @Excel(name = "处理日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date handleTime;

    /** 处理过程 */
    @Excel(name = "处理过程")
    private String process;

    /** 损坏情况 */
    @Excel(name = "损坏情况")
    private String damage;

    public void setFaultId(Long faultId) 
    {
        this.faultId = faultId;
    }

    public Long getFaultId() 
    {
        return faultId;
    }
    public void setEquipId(Long equipId) 
    {
        this.equipId = equipId;
    }

    public Long getEquipId() 
    {
        return equipId;
    }
    public void setFindTime(Date findTime) 
    {
        this.findTime = findTime;
    }

    public Date getFindTime() 
    {
        return findTime;
    }
    public void setAppearance(String appearance) 
    {
        this.appearance = appearance;
    }

    public String getAppearance() 
    {
        return appearance;
    }
    public void setAnalysis(String analysis) 
    {
        this.analysis = analysis;
    }

    public String getAnalysis() 
    {
        return analysis;
    }
    public void setHandleTime(Date handleTime) 
    {
        this.handleTime = handleTime;
    }

    public Date getHandleTime() 
    {
        return handleTime;
    }
    public void setProcess(String process) 
    {
        this.process = process;
    }

    public String getProcess() 
    {
        return process;
    }
    public void setDamage(String damage) 
    {
        this.damage = damage;
    }

    public String getDamage() 
    {
        return damage;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("faultId", getFaultId())
            .append("equipId", getEquipId())
            .append("findTime", getFindTime())
            .append("appearance", getAppearance())
            .append("analysis", getAnalysis())
            .append("handleTime", getHandleTime())
            .append("process", getProcess())
            .append("damage", getDamage())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
