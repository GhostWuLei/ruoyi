package com.ruoyi.project.devsys.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 检修记录对象 dev_repair
 * 
 * @author wulei
 * @date 2020-10-26
 */
public class DevRepair extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 检修ID */
    private Long repairId;

    /** 设备ID */
    @Excel(name = "设备ID")
    private Long equipId;

    /** 检修开始时间 */
    @Excel(name = "检修开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /** 检修结束时间 */
    @Excel(name = "检修结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date finishTime;

    /** 检修级别 */
    @Excel(name = "检修级别")
    private String repairLevel;

    /** 检修内容 */
    @Excel(name = "检修内容")
    private String repairContent;

    /** 主要处理问题 */
    @Excel(name = "主要处理问题")
    private String handleProblem;

    /** 遗留问题 */
    @Excel(name = "遗留问题")
    private String remainProblem;

    /** 检修班组 */
    @Excel(name = "检修班组")
    private String clazz;

    /** 检修单位 */
    @Excel(name = "检修单位")
    private String repairUnit;

    /** 负责人（可以有多个） */
    @Excel(name = "负责人", readConverterExp = "可=以有多个")
    private String leader;

    /** 工作联系人（可以有多个） */
    @Excel(name = "工作联系人", readConverterExp = "可=以有多个")
    private String linkman;

    public void setRepairId(Long repairId) 
    {
        this.repairId = repairId;
    }

    public Long getRepairId() 
    {
        return repairId;
    }
    public void setEquipId(Long equipId) 
    {
        this.equipId = equipId;
    }

    public Long getEquipId() 
    {
        return equipId;
    }
    public void setStartTime(Date startTime) 
    {
        this.startTime = startTime;
    }

    public Date getStartTime() 
    {
        return startTime;
    }
    public void setFinishTime(Date finishTime) 
    {
        this.finishTime = finishTime;
    }

    public Date getFinishTime() 
    {
        return finishTime;
    }
    public void setRepairLevel(String repairLevel) 
    {
        this.repairLevel = repairLevel;
    }

    public String getRepairLevel() 
    {
        return repairLevel;
    }
    public void setRepairContent(String repairContent) 
    {
        this.repairContent = repairContent;
    }

    public String getRepairContent() 
    {
        return repairContent;
    }
    public void setHandleProblem(String handleProblem) 
    {
        this.handleProblem = handleProblem;
    }

    public String getHandleProblem() 
    {
        return handleProblem;
    }
    public void setRemainProblem(String remainProblem) 
    {
        this.remainProblem = remainProblem;
    }

    public String getRemainProblem() 
    {
        return remainProblem;
    }
    public void setClazz(String clazz) 
    {
        this.clazz = clazz;
    }

    public String getClazz() 
    {
        return clazz;
    }
    public void setRepairUnit(String repairUnit) 
    {
        this.repairUnit = repairUnit;
    }

    public String getRepairUnit() 
    {
        return repairUnit;
    }
    public void setLeader(String leader) 
    {
        this.leader = leader;
    }

    public String getLeader() 
    {
        return leader;
    }
    public void setLinkman(String linkman) 
    {
        this.linkman = linkman;
    }

    public String getLinkman() 
    {
        return linkman;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("repairId", getRepairId())
            .append("equipId", getEquipId())
            .append("startTime", getStartTime())
            .append("finishTime", getFinishTime())
            .append("repairLevel", getRepairLevel())
            .append("repairContent", getRepairContent())
            .append("handleProblem", getHandleProblem())
            .append("remainProblem", getRemainProblem())
            .append("clazz", getClazz())
            .append("repairUnit", getRepairUnit())
            .append("leader", getLeader())
            .append("linkman", getLinkman())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
