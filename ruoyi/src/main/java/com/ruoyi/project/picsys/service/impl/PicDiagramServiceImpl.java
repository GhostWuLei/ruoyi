package com.ruoyi.project.picsys.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.constant.DiagramConstants;
import com.ruoyi.common.constant.EquipConstants;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.config.RuoYiConfig;
import com.ruoyi.framework.web.domain.TreeSelect;
import com.ruoyi.project.devsys.domain.DevKks;
import com.ruoyi.project.devsys.mapper.DevKksMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.picsys.mapper.PicDiagramMapper;
import com.ruoyi.project.picsys.domain.PicDiagram;
import com.ruoyi.project.picsys.service.IPicDiagramService;

/**
 * 系统图Service业务层处理
 * 
 * @author wulei
 * @date 2020-06-28
 */
@Service
public class PicDiagramServiceImpl implements IPicDiagramService 
{
    @Autowired
    private PicDiagramMapper diagramMapper;

    @Autowired
    private DevKksMapper kksMapper;

    /**
     * 查询系统图
     * 
     * @param nodeId 系统图ID
     * @return 系统图
     */
    @Override
    public PicDiagram selectPicDiagramById(Long nodeId)
    {
        return diagramMapper.selectPicDiagramById(nodeId);
    }

    /**
     * 查询系统图列表
     * 
     * @param picDiagram 系统图
     * @return 系统图
     */
    @Override
    public List<PicDiagram> selectPicDiagramList(PicDiagram picDiagram)
    {
        return diagramMapper.selectPicDiagramList(picDiagram);
    }

    /**
     * 新增系统图
     * 
     * @param diagram 系统图
     * @return 结果
     */
    @Override
    public int insertPicDiagram(PicDiagram diagram)
    {
        PicDiagram parentDiagram = diagramMapper.selectPicDiagramById(diagram.getParentId());
        diagram.setCreateTime(DateUtils.getNowDate());
        diagram.setAncestors(parentDiagram.getAncestors() + "," + diagram.getParentId());
        diagram.setCreateBy(SecurityUtils.getUsername());
        return diagramMapper.insertPicDiagram(diagram);
    }


    /**
     * 批量删除系统图
     * 
     * @param nodeIds 需要删除的系统图ID
     * @return 结果
     */
    @Override
    public int deletePicDiagramByIds(Long[] nodeIds)
    {
        return diagramMapper.deletePicDiagramByIds(nodeIds);
    }



    // ##################################################################

    /**
     * 修改系统图
     *
     * @param diagram 系统图
     * @return 结果
     */
    @Override
    public int updatePicDiagram(PicDiagram diagram)
    {
        PicDiagram newParentDiagram = diagramMapper.selectPicDiagramById(diagram.getParentId());
        PicDiagram oldDiagram = diagramMapper.selectPicDiagramById(diagram.getNodeId());
        if(StringUtils.isNotNull(newParentDiagram) && StringUtils.isNotNull(oldDiagram)){
            String newAncestors = newParentDiagram.getAncestors() + "," + newParentDiagram.getNodeId();
            String oldAncestors = oldDiagram.getAncestors();
            diagram.setAncestors(newAncestors);
            // 修改子节点的祖级列表
            updateDiagramChildren(diagram.getNodeId(), newAncestors, oldAncestors);

        }
        diagram.setUpdateTime(DateUtils.getNowDate());
        diagram.setUpdateBy(SecurityUtils.getUsername());
        return diagramMapper.updatePicDiagram(diagram);
    }

    /**
     * 判断是否存在下级目录或者系统图
     *
     * @param nodeId
     * @return
     */
    @Override
    public boolean hasChildByDiagramId(Long nodeId) {
        int result = diagramMapper.hasChildByDiagramId(nodeId);
        return result > 0 ? true : false;
    }

    /**
     * 递归修改子节点关系
     *
     * @param nodeId 被修改的节点id
     * @param newAncestors 新的父id集合
     * @param oldAncestors 旧的父id集合
     */
    private void updateDiagramChildren(Long nodeId, String newAncestors, String oldAncestors) {
        List<PicDiagram> childrens = diagramMapper.selectChildrenDiagramById(nodeId);
        for (PicDiagram child : childrens) {
            child.setAncestors(child.getAncestors().replace(oldAncestors, newAncestors));
        }
        if(childrens.size() > 0){
            diagramMapper.updateDiagramChildren(childrens);
        }
    }

    /**
     * 构建前端所需要系统图树结构
     *
     * @param diagramList
     * @return
     */
    @Override
    public List<TreeSelect> buildDiagramTreeSelect(List<PicDiagram> diagramList) {
        List<PicDiagram> diagramTrees = buildDiagramTree(diagramList);
        System.out.println(diagramTrees.stream().map(TreeSelect::new).collect(Collectors.toList()));
        return diagramTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    /**
     * 构建前端所需要的树结构
     *
     * @param diagramList
     * @return
     */
    @Override
    public List<PicDiagram> buildDiagramTree(List<PicDiagram> diagramList) {
        List<PicDiagram> returnList = new ArrayList<>();
        List<Long> tempList = new ArrayList<>();
        for (PicDiagram diagram : diagramList) {
            tempList.add(diagram.getNodeId());
        }
        for(Iterator<PicDiagram> iterator = diagramList.iterator(); iterator.hasNext();){
            PicDiagram diagram = (PicDiagram) iterator.next();
            if(!tempList.contains(diagram.getParentId())){
                // 说明这个diagram是顶级节点
                recursionFn(diagramList, diagram);
                returnList.add(diagram);
            }
        }
        if (returnList.isEmpty()){
            returnList = diagramList;
        }
        return returnList;
    }

    /**
     * 校验系统图/目录名称在同级是否唯一
     *
     * @param diagram
     * @return
     */
    @Override
    public String checkDiagramNameUnique(PicDiagram diagram) {
        Long nodeId = StringUtils.isNull(diagram.getNodeId()) ? -1L : diagram.getNodeId();
        PicDiagram info = diagramMapper.checkDiagramNameUnique(diagram.getNodeName(), diagram.getParentId());
        if(StringUtils.isNotNull(info) && info.getNodeId().longValue() != nodeId.longValue()){
            return DiagramConstants.DIAGRAM_NOT_UNIQUE;
        }
        return DiagramConstants.DIAGRAM_UNIQUE;
    }

    /**
     * 递归列表
     *
     * @param list 所有系统图的列表
     * @param d 第一次调用时是顶级节点
     */
    private void recursionFn(List<PicDiagram> list, PicDiagram d) {
        List<PicDiagram> childList = getChildList(list, d);
        d.setChildren(childList);
        for (PicDiagram tempChild : childList) {
            if(hasChild(list, tempChild)){
                //还有子节点
                Iterator<PicDiagram> iterator = childList.iterator();
                while (iterator.hasNext()){
                    PicDiagram n = iterator.next();
                    recursionFn(list, n);
                }
            }
        }

    }

    /**
     * 判断是否存在子节点
     *
     * @param list
     * @param tempChild
     * @return
     */
    private boolean hasChild(List<PicDiagram> list, PicDiagram tempChild) {
        return getChildList(list, tempChild).size() > 0 ? true : false;
    }

    /**
     * 得到孩子节点列表
     *
     * @param list
     * @param d
     * @return
     */
    private List<PicDiagram> getChildList(List<PicDiagram> list, PicDiagram d) {
        List<PicDiagram> tempList = new ArrayList<>();
        Iterator<PicDiagram> it = list.iterator();
        while(it.hasNext()){
            PicDiagram tempDiagram = (PicDiagram)it.next();
            if(StringUtils.isNotNull(tempDiagram.getParentId()) && tempDiagram.getParentId().longValue() == d.getNodeId().longValue()){
                // 说明这个tempDiagram是传过来的d的子节点（非孙子节点或其他）
                tempList.add(tempDiagram);
            }
        }
        return tempList;
    }

    /**
     * 删除附件 annex: 附件
     * @param fpath
     */
    private void deleteAnnex(String fpath){
        if(StringUtils.isEmpty(fpath)){
            throw new CustomException("附件路径为空！", 401);
        }
        //将相对路径转换为绝对路径
        String newPath = fpath.replaceAll(Constants.RESOURCE_PREFIX, RuoYiConfig.getProfile());
        File file = new File(newPath);
        if(file.exists()){
            if(!file.delete()){
                throw new CustomException("删除失败", 401);
            }
        }else{
            throw new CustomException("附件不存在！", 401);
        }
    }
    /**
     * 删除系统图信息
     *
     * @param nodeId 系统图ID
     * @return 结果
     */
    @Override
    public int deletePicDiagramById(Long nodeId)
    {
        // 删除前先删除对应的系统图文件
        PicDiagram diagram = diagramMapper.selectPicDiagramById(nodeId);
        if(diagram != null && "0".equals(diagram.getNodeType())){
            deleteAnnex(diagram.getFpath());
        }
        // 修改KKS里面关联此系统图的信息
        kksMapper.updateKksByDiagramName(diagram.getNodeName());
        return diagramMapper.deletePicDiagramById(nodeId);
    }

    /**
     * 获取所有目录
     *
     * @param nodeType
     * @return
     */
    @Override
    public List<PicDiagram> selectPicDiagramListByNodeType(String nodeType) {
        return diagramMapper.selectPicDiagramListByNodeType(nodeType);
    }

    /**
     * 获取所有的系统图信息 非目录
     *
     * @return
     */
    @Override
    public List<PicDiagram> selectDiagrams() {
        PicDiagram diagram = new PicDiagram();
        diagram.setNodeType("0");
        List<PicDiagram> diagrams = diagramMapper.selectPicDiagramList(diagram);
        return diagrams;
    }

    /**
     * 判断节点名称是否唯一 除自己
     *
     * @param diagram
     * @return
     */
    @Override
    public boolean checkNodeNameUniqueExcludeSelf(PicDiagram diagram) {
        int count = diagramMapper.checkNodeNameUniqueExcludeSelf(diagram.getNodeId(), diagram.getNodeName());
        return count > 0 ? true : false;
    }

    /**
     * 判断节点是否唯一
     *
     * @param nodeName
     * @return
     */
    @Override
    public boolean checkNodeNameUnique(String nodeName) {
        int count = diagramMapper.checkNodeNameUnique(nodeName);
        return false;
    }

    /**
     * 根据nodeName获取nodeId
     *
     * @param nodeName
     * @return
     */
    @Override
    public Long getNodeIdByNodeName(String nodeName) {
        return diagramMapper.getNodeIdByNodeName(nodeName);
    }


}
