package com.ruoyi.project.devsys.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * kks编码对象 dev_kks
 * 
 * @author wulei
 * @date 2020-05-26
 */
public class DevKks extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** kks编码id */
    private Long kksId;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String equName;

    /** 新kks编码 */
    @Excel(name = "新kks编码")
    private String newKks;

    /** 父kks编码 */
    @Excel(name = "父kks编码")
    private String parentKks;

    /** 旧kks编码 可能没有所以可以为空 */
    @Excel(name = "旧kks编码")
    private String oldKks;

    @Excel(name = "所属系统图")
    private String diagramName;

    /** 设备规格 */
    private String equSpecifications;

    /** 部门 */
    private String department;

    /** 专业 */
    @Excel(name = "专业")
    private String major;

    /** 班组 */
    private String clazz;

    /** 子编码 */
    private List<DevKks> children = new ArrayList<DevKks>();

    private boolean hasChildren;

    public void setKksId(Long kksId) 
    {
        this.kksId = kksId;
    }

    public Long getKksId() 
    {
        return kksId;
    }
    public void setEquName(String equName) 
    {
        this.equName = equName;
    }

    public String getEquName() 
    {
        return equName;
    }
    public void setNewKks(String newKks) 
    {
        this.newKks = newKks;
    }

    @NotBlank(message = "新KKS编码不能为空")
    public String getNewKks() 
    {
        return newKks;
    }
    public void setOldKks(String oldKks) 
    {
        this.oldKks = oldKks;
    }

    public String getOldKks() 
    {
        return oldKks;
    }

    public String getDiagramName() {
        return diagramName;
    }

    public void setDiagramName(String diagramName) {
        this.diagramName = diagramName;
    }

    public void setEquSpecifications(String equSpecifications)
    {
        this.equSpecifications = equSpecifications;
    }

    public String getEquSpecifications() 
    {
        return equSpecifications;
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
    public void setParentKks(String parentKks) 
    {
        this.parentKks = parentKks;
    }

    @NotBlank(message = "父级KKS编码不能为空")
    public String getParentKks() 
    {
        return parentKks;
    }

    public List<DevKks> getChildren() {
        return children;
    }

    public void setChildren(List<DevKks> children) {
        this.children = children;
    }

    public boolean isHasChildren() {
        return hasChildren;
    }

    public void setHasChildren(boolean hasChildren) {
        this.hasChildren = hasChildren;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("kksId", getKksId())
            .append("equName", getEquName())
            .append("newKks", getNewKks())
            .append("oldKks", getOldKks())
            .append("diagramName", getDiagramName())
            .append("equSpecifications", getEquSpecifications())
            .append("department", getDepartment())
            .append("major", getMajor())
            .append("clazz", getClazz())
            .append("parentKks", getParentKks())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("hasChildren", isHasChildren())
            .toString();
    }
}
