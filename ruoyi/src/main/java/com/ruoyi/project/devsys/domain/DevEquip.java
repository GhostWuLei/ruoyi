package com.ruoyi.project.devsys.domain;

import com.ruoyi.project.system.domain.SysDept;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * 设备对象 dev_equip
 * 
 * @author wulei
 * @date 2020-05-27
 */
public class DevEquip extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 设备ID */
    private Long equipId;

    /** 父设备ID */
    @Excel(name = "父设备ID")
    private Long parentId;

    /** 祖级列表 */
    @Excel(name = "祖级列表")
    private String ancestors;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String equipName;

    /** kks编码 */
    @Excel(name = "kks编码")
    private String equipKks;

    /** 显示顺序 */
    @Excel(name = "显示顺序")
    private Integer orderNum;

    /** 目录类型（0设备 1目录） */
    @Excel(name = "目录类型", readConverterExp = "0=设备,1=目录")
    private String catalogType;

    /** 设备状态（0正常 1停用） */
    @Excel(name = "设备状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /** 部门 */
    @Excel(name = "部门")
    private String department;

    /** 专业 */
    @Excel(name = "专业")
    private String major;

    /** 班组 */
    @Excel(name = "班组")
    private String clazz;

    //########################################################

    /** 父设备名称 */
    private String parentName;

    /** 子设备 */
    private List<DevEquip> children = new ArrayList<DevEquip>();


    public void setEquipId(Long equipId) 
    {
        this.equipId = equipId;
    }

    public Long getEquipId() 
    {
        return equipId;
    }
    public void setParentId(Long parentId) 
    {
        this.parentId = parentId;
    }

    public Long getParentId() 
    {
        return parentId;
    }
    public void setAncestors(String ancestors) 
    {
        this.ancestors = ancestors;
    }

    public String getAncestors() 
    {
        return ancestors;
    }
    public void setEquipName(String equipName) 
    {
        this.equipName = equipName;
    }

    public String getEquipName() 
    {
        return equipName;
    }
    public void setEquipKks(String equipKks) 
    {
        this.equipKks = equipKks;
    }

    public String getEquipKks() 
    {
        return equipKks;
    }
    public void setOrderNum(Integer orderNum) 
    {
        this.orderNum = orderNum;
    }

    public Integer getOrderNum() 
    {
        return orderNum;
    }
    public void setCatalogType(String catalogType) 
    {
        this.catalogType = catalogType;
    }

    public String getCatalogType() 
    {
        return catalogType;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setDepartment(String department) 
    {
        this.department = department;
    }

    public String getDepartment() 
    {
        return department;
    }
    public void setMajor(String major) 
    {
        this.major = major;
    }

    public String getMajor() 
    {
        return major;
    }
    public void setClazz(String clazz) 
    {
        this.clazz = clazz;
    }

    public String getClazz() 
    {
        return clazz;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public List<DevEquip> getChildren() {
        return children;
    }

    public void setChildren(List<DevEquip> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("equipId", getEquipId())
            .append("parentId", getParentId())
            .append("ancestors", getAncestors())
            .append("equipName", getEquipName())
            .append("equipKks", getEquipKks())
            .append("orderNum", getOrderNum())
            .append("catalogType", getCatalogType())
            .append("status", getStatus())
            .append("department", getDepartment())
            .append("major", getMajor())
            .append("clazz", getClazz())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }

}
