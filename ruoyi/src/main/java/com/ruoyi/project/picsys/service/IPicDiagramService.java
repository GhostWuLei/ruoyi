package com.ruoyi.project.picsys.service;

import java.util.List;

import com.ruoyi.framework.web.domain.TreeSelect;
import com.ruoyi.project.devsys.domain.DevEquip;
import com.ruoyi.project.picsys.domain.PicDiagram;

/**
 * 系统图Service接口
 * 
 * @author wulei
 * @date 2020-06-28
 */
public interface IPicDiagramService 
{
    /**
     * 查询系统图
     * 
     * @param nodeId 系统图ID
     * @return 系统图
     */
    public PicDiagram selectPicDiagramById(Long nodeId);

    /**
     * 查询系统图列表
     * 
     * @param picDiagram 系统图
     * @return 系统图集合
     */
    public List<PicDiagram> selectPicDiagramList(PicDiagram picDiagram);

    /**
     * 新增系统图
     * 
     * @param picDiagram 系统图
     * @return 结果
     */
    public int insertPicDiagram(PicDiagram picDiagram);


    /**
     * 批量删除系统图
     * 
     * @param nodeIds 需要删除的系统图ID
     * @return 结果
     */
    public int deletePicDiagramByIds(Long[] nodeIds);

    // #########################################################

    /**
     * 构建前端所需要系统图树结构
     *
     * @param diagramList
     * @return
     */
    List<TreeSelect> buildDiagramTreeSelect(List<PicDiagram> diagramList);

    /**
     * 构建前端所需要树结构
     * @param diagramList 设备列表
     * @return 树结构列表
     */
    List<PicDiagram> buildDiagramTree(List<PicDiagram> diagramList);

    /**
     * 校验系统图/目录名称在同级是否唯一
     *
     * @param diagram
     * @return
     */
    String checkDiagramNameUnique(PicDiagram diagram);

    /**
     * 修改系统图
     *
     * @param picDiagram 系统图
     * @return 结果
     */
    public int updatePicDiagram(PicDiagram picDiagram);

    /**
     * 判断是否存在下级目录或者系统图
     *
     * @param nodeId
     * @return
     */
    boolean hasChildByDiagramId(Long nodeId);

    /**
     * 删除系统图信息
     *
     * @param nodeId 系统图ID
     * @return 结果
     */
    public int deletePicDiagramById(Long nodeId);

    /**
     * 获取所有目录
     * @param nodeType
     * @return
     */
    List<PicDiagram> selectPicDiagramListByNodeType(String nodeType);

    /**
     * 获取所有的系统图信息 非目录
     *
     * @return
     */
    List<PicDiagram> selectDiagrams();


    /**
     * 判断节点名称是否唯一 除自己
     *
     * @param diagram
     * @return
     */
    boolean checkNodeNameUniqueExcludeSelf(PicDiagram diagram);

    /**
     * 判断节点是否唯一
     *
     * @param nodeName
     * @return
     */
    boolean checkNodeNameUnique(String nodeName);

    /**
     * 根据nodeName获取nodeId
     *
     * @param nodeName
     * @return
     */
    Long getNodeIdByNodeName(String nodeName);
}

