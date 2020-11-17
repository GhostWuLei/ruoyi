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
    private Long equipId;

    /** 检修开始时间 */
    @Excel(name = "检修开始时间")
    private String startTime;

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
    @Excel(name = "工作负责人", readConverterExp = "可=以有多个")
    private String leader;

    /** 工作联系人（可以有多个） */
    @Excel(name = "工作联系人（是否需要）", readConverterExp = "可=以有多个")
    private String linkman;

    /** 备注 */
    @Excel(name = "备注")
    private String remark;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getRepairId() {
        return repairId;
    }

    public void setRepairId(Long repairId) {
        this.repairId = repairId;
    }

    public Long getEquipId() {
        return equipId;
    }

    public void setEquipId(Long equipId) {
        this.equipId = equipId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public String getRepairLevel() {
        return repairLevel;
    }

    public void setRepairLevel(String repairLevel) {
        this.repairLevel = repairLevel;
    }

    public String getRepairContent() {
        return repairContent;
    }

    public void setRepairContent(String repairContent) {
        this.repairContent = repairContent;
    }

    public String getHandleProblem() {
        return handleProblem;
    }

    public void setHandleProblem(String handleProblem) {
        this.handleProblem = handleProblem;
    }

    public String getRemainProblem() {
        return remainProblem;
    }

    public void setRemainProblem(String remainProblem) {
        this.remainProblem = remainProblem;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getRepairUnit() {
        return repairUnit;
    }

    public void setRepairUnit(String repairUnit) {
        this.repairUnit = repairUnit;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "DevRepair{" +
                "repairId=" + repairId +
                ", equipId=" + equipId +
                ", startTime='" + startTime + '\'' +
                ", finishTime=" + finishTime +
                ", repairLevel='" + repairLevel + '\'' +
                ", repairContent='" + repairContent + '\'' +
                ", handleProblem='" + handleProblem + '\'' +
                ", remainProblem='" + remainProblem + '\'' +
                ", clazz='" + clazz + '\'' +
                ", repairUnit='" + repairUnit + '\'' +
                ", leader='" + leader + '\'' +
                ", linkman='" + linkman + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
