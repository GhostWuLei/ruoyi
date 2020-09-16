package com.ruoyi.project.devsys.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ruoyi.framework.web.domain.TreeSelect;
import com.ruoyi.project.devsys.domain.DevKks;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * KKS编码的树结构实体类
 *
 * @author wulei
 */
public class KksTreeVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 节点ID */
    private String id;

    /** 节点名称 */
    private String label;

    /** 子节点 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<KksTreeVo> children;

    /**
     * 无参构造
     */
    public KksTreeVo() {

    }

    public KksTreeVo(DevKks kks) {
        this.id = kks.getNewKks();
        this.label = kks.getNewKks();
        this.children = kks.getChildren().stream().map(KksTreeVo::new).collect(Collectors.toList());
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

    public List<KksTreeVo> getChildren() {
        return children;
    }

    public void setChildren(List<KksTreeVo> children) {
        this.children = children;
    }
}
