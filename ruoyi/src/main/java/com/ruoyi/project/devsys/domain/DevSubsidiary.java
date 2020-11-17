package com.ruoyi.project.devsys.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 附属设备明细对象 dev_subsidiary
 *
 * @author wulei
 * @date 2020-10-30
 */
public class DevSubsidiary extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 附属设备ID */
    private Long subsidiaryId;

    /** 设备ID */
    private Long equipId;

    /** KKS编码 */
    @Excel(name = "KKS码")
    private String kks;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String equipName;

    /** 设备类型 */
    @Excel(name = "设备类型")
    private String subsType;

    /** 型号 */
    @Excel(name = "型号")
    private String specification;

    /** 单位 */
    @Excel(name = "单位")
    private String unit;

    /** 生产厂家 */
    @Excel(name = "生产厂家")
    private String productor;

    @Excel(name = "备注")
    private String remark;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getSubsidiaryId() {
        return subsidiaryId;
    }

    public void setSubsidiaryId(Long subsidiaryId) {
        this.subsidiaryId = subsidiaryId;
    }

    public Long getEquipId() {
        return equipId;
    }

    public void setEquipId(Long equipId) {
        this.equipId = equipId;
    }

    public String getKks() {
        return kks;
    }

    public void setKks(String kks) {
        this.kks = kks;
    }

    public String getEquipName() {
        return equipName;
    }

    public void setEquipName(String equipName) {
        this.equipName = equipName;
    }

    public String getSubsType() {
        return subsType;
    }

    public void setSubsType(String subsType) {
        this.subsType = subsType;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getProductor() {
        return productor;
    }

    public void setProductor(String productor) {
        this.productor = productor;
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
        return "DevSubsidiary{" +
                "subsidiaryId=" + subsidiaryId +
                ", equipId=" + equipId +
                ", kks='" + kks + '\'' +
                ", equipName='" + equipName + '\'' +
                ", subsType='" + subsType + '\'' +
                ", specification='" + specification + '\'' +
                ", unit='" + unit + '\'' +
                ", productor='" + productor + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
