package com.ruoyi.project.devsys.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

import java.util.Date;

/**
 * 设备变更对象 dev_alteration
 *
 * @author wulei
 * @date 2020-10-30
 */
public class DevAlteration extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 变更ID
     */
    private Long alterationId;

    private Long equipId;

    /**
     * 设备名称
     */
    @Excel(name = "变更日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createTime;

    /**
     * 设备名称
     */
    @Excel(name = "设备名称")
    private String equipName;

    /**
     * 变动原因
     */
    @Excel(name = "变动原因")
    private String reason;

    /**
     * 变动效果
     */
    @Excel(name = "变动效果")
    private String effect;

    /**
     * 负责人（可以有多个）
     */
    @Excel(name = "变动负责人", readConverterExp = "可=以有多个")
    private String leader;

    /**
     * 评价
     */
    @Excel(name = "评价")
    private String evaluate;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getAlterationId() {
        return alterationId;
    }

    public void setAlterationId(Long alterationId) {
        this.alterationId = alterationId;
    }

    public Long getEquipId() {
        return equipId;
    }

    public void setEquipId(Long equipId) {
        this.equipId = equipId;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getEquipName() {
        return equipName;
    }

    public void setEquipName(String equipName) {
        this.equipName = equipName;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
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
        return "DevAlteration{" +
                "alterationId=" + alterationId +
                ", equipId=" + equipId +
                ", createTime=" + createTime +
                ", equipName='" + equipName + '\'' +
                ", reason='" + reason + '\'' +
                ", effect='" + effect + '\'' +
                ", leader='" + leader + '\'' +
                ", evaluate='" + evaluate + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
