package com.ruoyi.project.devsys.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 设备变更对象 dev_alteration
 * 
 * @author wulei
 * @date 2020-10-30
 */
public class DevAlteration extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 变更ID */
    private Long alterationId;

    /** 设备ID */
    @Excel(name = "设备ID")
    private Long equipId;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String equipName;

    /** 变动原因 */
    @Excel(name = "变动原因")
    private String reason;

    /** 变动效果 */
    @Excel(name = "变动效果")
    private String effect;

    /** 负责人（可以有多个） */
    @Excel(name = "负责人", readConverterExp = "可=以有多个")
    private String leader;

    /** 评价 */
    @Excel(name = "评价")
    private String evaluate;

    public void setAlterationId(Long alterationId) 
    {
        this.alterationId = alterationId;
    }

    public Long getAlterationId() 
    {
        return alterationId;
    }
    public void setEquipId(Long equipId) 
    {
        this.equipId = equipId;
    }

    public Long getEquipId() 
    {
        return equipId;
    }
    public void setEquipName(String equipName) 
    {
        this.equipName = equipName;
    }

    public String getEquipName() 
    {
        return equipName;
    }
    public void setReason(String reason) 
    {
        this.reason = reason;
    }

    public String getReason() 
    {
        return reason;
    }
    public void setEffect(String effect) 
    {
        this.effect = effect;
    }

    public String getEffect() 
    {
        return effect;
    }
    public void setLeader(String leader) 
    {
        this.leader = leader;
    }

    public String getLeader() 
    {
        return leader;
    }
    public void setEvaluate(String evaluate) 
    {
        this.evaluate = evaluate;
    }

    public String getEvaluate() 
    {
        return evaluate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("alterationId", getAlterationId())
            .append("equipId", getEquipId())
            .append("equipName", getEquipName())
            .append("reason", getReason())
            .append("effect", getEffect())
            .append("leader", getLeader())
            .append("evaluate", getEvaluate())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
