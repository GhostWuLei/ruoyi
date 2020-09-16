package com.ruoyi.common.utils.demo;

import java.util.List;

public class Ctree {
    private String id;
    private String pid;
    private String  nodeName;
    private String url;

    private List<Ctree> children;

    public Ctree(String id, String pid, String nodeName, String url) {
        this.id = id;
        this.pid = pid;
        this.nodeName = nodeName;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Ctree> getChildren() {
        return children;
    }

    public void setChildren(List<Ctree> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "Ctree{" +
                "id='" + id + '\'' +
                ", pid='" + pid + '\'' +
                ", nodeName='" + nodeName + '\'' +
                ", url='" + url + '\'' +
                ", children=" + children +
                '}';
    }
}