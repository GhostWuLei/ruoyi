package com.ruoyi.project.devsys.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 技术资料对象 dev_material
 * @author wulei
 * @date 2020-06-12
 */
public class DevMaterial extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 资料ID */
    private Long materialId;

    /** 资料名称 */
    @Excel(name = "资料名称")
    private String materialName;

    /** 设备ID */
    private Long equipId;

    /** 资料类型 */
    @Excel(name = "资料类型")
    private String materialType;

    /** 上传时间 */
    @Excel(name = "上传时间", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date upTime;

    /** 附件名称 */
    private String fname;

    /** 附件路径 */
    private String fpath;

    public void setMaterialId(Long materialId)
    {
        this.materialId = materialId;
    }

    public Long getMaterialId()
    {
        return materialId;
    }
    public void setMaterialName(String materialName)
    {
        this.materialName = materialName;
    }

    public String getMaterialName()
    {
        return materialName;
    }
    public void setEquipId(Long equipId)
    {
        this.equipId = equipId;
    }

    public Long getEquipId()
    {
        return equipId;
    }
    public void setMaterialType(String materialType)
    {
        this.materialType = materialType;
    }

    public String getMaterialType()
    {
        return materialType;
    }
    public void setUpTime(Date upTime)
    {
        this.upTime = upTime;
    }

    public Date getUpTime()
    {
        return upTime;
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
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("materialId", getMaterialId())
                .append("materialName", getMaterialName())
                .append("equipId", getEquipId())
                .append("materialType", getMaterialType())
                .append("upTime", getUpTime())
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
