package com.ruoyi.project.devsys.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 设备信息对象 dev_information
 * 
 * @author wulei
 * @date 2020-10-30
 */
public class DevInformation extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 信息ID */
    private Long informationId;

    /** 设备ID */
    private Long equipId;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String equipName;

    /** 设备型号 */
    @Excel(name = "设备型号")
    private String specification;

    /** 设备参数 */
    @Excel(name = "设备参数")
    private String equipParam;

    /** 技术要求 */
    @Excel(name = "技术要求")
    private String techParam;

    /** 检修周期 */
    @Excel(name = "检修周期")
    private String cycle;

    /** 安装日期 */
    @Excel(name = "安装日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date installTime;

    /** 安装地点 */
    @Excel(name = "安装地点", width = 30, dateFormat = "yyyy-MM-dd")
    private Date installPlace;

    public void setInformationId(Long informationId) 
    {
        this.informationId = informationId;
    }

    public Long getInformationId() 
    {
        return informationId;
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
    public void setSpecification(String specification) 
    {
        this.specification = specification;
    }

    public String getSpecification() 
    {
        return specification;
    }
    public void setEquipParam(String equipParam) 
    {
        this.equipParam = equipParam;
    }

    public String getEquipParam() 
    {
        return equipParam;
    }
    public void setTechParam(String techParam) 
    {
        this.techParam = techParam;
    }

    public String getTechParam() 
    {
        return techParam;
    }
    public void setCycle(String cycle) 
    {
        this.cycle = cycle;
    }

    public String getCycle() 
    {
        return cycle;
    }
    public void setInstallTime(Date installTime) 
    {
        this.installTime = installTime;
    }

    public Date getInstallTime() 
    {
        return installTime;
    }
    public void setInstallPlace(Date installPlace) 
    {
        this.installPlace = installPlace;
    }

    public Date getInstallPlace() 
    {
        return installPlace;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("informationId", getInformationId())
            .append("equipId", getEquipId())
            .append("equipName", getEquipName())
            .append("specification", getSpecification())
            .append("equipParam", getEquipParam())
            .append("techParam", getTechParam())
            .append("cycle", getCycle())
            .append("installTime", getInstallTime())
            .append("installPlace", getInstallPlace())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
