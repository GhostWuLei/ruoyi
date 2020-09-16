package com.ruoyi.project.devsys.domain.vo;


import com.ruoyi.project.devsys.domain.DevKks;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class KKSSelectTreeVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 节点ID */
    private String id;

    /** 节点名称 */
    private String label;

    /** 是否有子节点 */
    private boolean hasChildren;


    public KKSSelectTreeVo() {
    }

    public KKSSelectTreeVo(DevKks kks) {
        this.id = kks.getNewKks();
        this.label = kks.getEquName();
        this.hasChildren = kks.isHasChildren();
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isHasChildren() {
        return hasChildren;
    }

    public void setHasChildren(boolean hasChildren) {
        this.hasChildren = hasChildren;
    }
}
