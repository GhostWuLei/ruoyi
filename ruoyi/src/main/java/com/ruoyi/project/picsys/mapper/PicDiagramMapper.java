package com.ruoyi.project.picsys.mapper;

import java.util.List;
import com.ruoyi.project.picsys.domain.PicDiagram;
import org.apache.ibatis.annotations.Param;

/**
 * 系统图Mapper接口
 * 
 * @author wulei
 * @date 2020-06-28
 */
public interface PicDiagramMapper 
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
     * 修改系统图
     * 
     * @param picDiagram 系统图
     * @return 结果
     */
    public int updatePicDiagram(PicDiagram picDiagram);

    /**
     * 删除系统图
     * 
     * @param nodeId 系统图ID
     * @return 结果
     */
    public int deletePicDiagramById(Long nodeId);

    /**
     * 批量删除系统图
     * 
     * @param nodeIds 需要删除的数据ID
     * @return 结果
     */
    public int deletePicDiagramByIds(Long[] nodeIds);

    /**
     * 校验同级设备目录下系统图名称或目录名称是否唯一
     *
     * @param nodeName
     * @param parentId
     * @return
     */
    PicDiagram checkDiagramNameUnique(@Param("nodeName") String nodeName, @Param("parentId") Long parentId);

    /**
     * 根据节点ID查询下面的子节点
     *
     * @param nodeId
     * @return
     */
    List<PicDiagram> selectChildrenDiagramById(Long nodeId);

    /**
     * 修改子元素关系
     *
     * @param diagrams
     */
    void updateDiagramChildren(@Param("diagrams") List<PicDiagram> diagrams);

    /**
     * 判断是否存在下级节点
     *
     * @param nodeId
     * @return
     */
    int hasChildByDiagramId(Long nodeId);

    /**
     * 获取所有的目录列表
     * @param nodeType
     * @return
     */
    List<PicDiagram> selectPicDiagramListByNodeType(String nodeType);

    /**
     * 判断节点名称是否唯一 除自己
     *
     * @param nodeId
     * @param nodeName
     * @return
     */
    int checkNodeNameUniqueExcludeSelf(@Param("nodeId") Long nodeId, @Param("nodeName") String nodeName);

    /**
     * 判断节点名称是否唯一
     *
     * @param nodeName
     * @return
     */
    int checkNodeNameUnique(String nodeName);

    /**
     * 根据nodeName获取nodeId
     *
     * @param nodeName
     * @return
     */
    Long getNodeIdByNodeName(String nodeName);
}
