package com.ruoyi.project.picsys.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * 系统图对象 pic_diagram
 * 
 * @author wulei
 * @date 2020-06-28
 */
public class PicDiagram extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 节点ID */
    private Long nodeId;

    /** 父节点ID */
    private Long parentId;

    /** 祖级列表 */
    private String ancestors;

    /** 节点名称 */
    private String nodeName;

    /** 节点类型 */
    private String nodeType;

    /** 附件名称 */
    private String fname;

    /** 附件路径 */
    private String fpath;

    // ###################################################

    private String parentName;

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public List<PicDiagram> getChildren() {
        return children;
    }

    public void setChildren(List<PicDiagram> children) {
        this.children = children;
    }

    private List<PicDiagram> children = new ArrayList<>();

    public void setNodeId(Long nodeId) 
    {
        this.nodeId = nodeId;
    }

    public Long getNodeId() 
    {
        return nodeId;
    }
    public void setParentId(Long parentId) 
    {
        this.parentId = parentId;
    }

    @NotBlank(message = "上级目录不能为空")
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
    public void setNodeName(String nodeName) 
    {
        this.nodeName = nodeName;
    }

    @NotBlank(message = "节点名称不能为空")
    public String getNodeName() 
    {
        return nodeName;
    }
    public void setNodeType(String nodeType) 
    {
        this.nodeType = nodeType;
    }

    public String getNodeType() 
    {
        return nodeType;
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
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("nodeId", getNodeId())
            .append("parentId", getParentId())
            .append("ancestor", getAncestors())
            .append("nodeName", getNodeName())
            .append("nodeType", getNodeType())
            .append("fname", getFname())
            .append("fpath", getFpath())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
