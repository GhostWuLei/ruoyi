package com.ruoyi.project.devsys.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import com.ruoyi.common.constant.EquipConstants;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.domain.TreeSelect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.devsys.mapper.DevEquipMapper;
import com.ruoyi.project.devsys.domain.DevEquip;
import com.ruoyi.project.devsys.service.IDevEquipService;
import oshi.util.StringUtil;

/**
 * 设备Service业务层处理
 * 
 * @author wulei
 * @date 2020-05-27
 */
@Service
public class DevEquipServiceImpl implements IDevEquipService
{
    @Autowired
    private DevEquipMapper equipMapper;

    /**
     * 查询设备
     * 
     * @param equipId 设备ID
     * @return 设备
     */
    @Override
    public DevEquip selectDevEquipById(Long equipId)
    {
        return equipMapper.selectDevEquipById(equipId);
    }

    /**
     * 查询设备列表
     * 
     * @param devEquip 设备
     * @return 设备
     */
    @Override
    public List<DevEquip> selectDevEquipList(DevEquip devEquip)
    {
        return equipMapper.selectDevEquipList(devEquip);
    }


    /**
     * 批量删除设备
     * 
     * @param equipIds 需要删除的设备ID
     * @return 结果
     */
    @Override
    public int deleteDevEquipByIds(Long[] equipIds)
    {
        return equipMapper.deleteDevEquipByIds(equipIds);
    }

    /**
     * 删除设备信息
     * 
     * @param equipId 设备ID
     * @return 结果
     */
    @Override
    public int deleteDevEquipById(Long equipId)
    {
        return equipMapper.deleteDevEquipById(equipId);
    }

    //#####################################################################

    /**
     * 构建前端所需要设备树结构
     *
     * @param equipList
     * @return
     */
    @Override
    public List<TreeSelect> buildEquipTreeSelect(List<DevEquip> equipList) {
        List<DevEquip> equipTrees = buildEquipTree(equipList);
        return equipTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    /**
     * 构建前端所需要树结构
     *
     * @param equipList 设备列表
     * @return 树结构列表
     */
    @Override
    public List<DevEquip> buildEquipTree(List<DevEquip> equipList) {
        List<DevEquip> returnList = new ArrayList<>();
        List<Long> tempList = new ArrayList<>();
        // 获取到所有的设备id 后面获取到所有设备id的父id 如果说设备id里面没有这个设备的父id说明这个设备是顶级节点 父id为0
        for (DevEquip equip : equipList) {
            tempList.add(equip.getEquipId());
        }
        for(Iterator<DevEquip> iterator = equipList.iterator(); iterator.hasNext();){
            DevEquip equip = (DevEquip) iterator.next();
            // 如果是顶级节点 则遍历该父节点所有的子节点
            if(!tempList.contains(equip.getParentId())){
                // equip为顶级节点
                recursionFn(equipList, equip);
                returnList.add(equip);
            }
        }
        if(returnList.isEmpty()){
            returnList = equipList;
        }
        return returnList;
    }

    /**
     * 校验设备名称在同级下是否唯一
     * @param equip
     * @return
     */
    @Override
    public String checkEquipNameUnique(DevEquip equip) {
        Long equipId = StringUtils.isNull(equip.getEquipId()) ? -1L : equip.getEquipId();
        DevEquip info = equipMapper.checkEquipNameUnique(equip.getEquipName(), equip.getParentId());
        if(StringUtils.isNotNull(info) && info.getEquipId().longValue() != equipId.longValue()){
            return EquipConstants.EQUIP_NOT_UNIQUE;
        }
        return EquipConstants.EQUIP_UNIQUE;
    }

    /**
     * 查询所选设备下面是否还有子设备/目录
     * @param equipId
     * @return
     */
    @Override
    public boolean hasChildByEquipId(Long equipId) {
        int result = equipMapper.hasChildByEquipId(equipId);
        return result > 0 ? true : false;
    }


    /**
     * 新增设备
     * @param equip 设备
     * @return 结果
     */
    @Override
    public int insertDevEquip(DevEquip equip)
    {
        DevEquip parentEquip = equipMapper.selectDevEquipById(equip.getParentId());
        equip.setCreateTime(DateUtils.getNowDate());
        equip.setAncestors(parentEquip.getAncestors() + "," + equip.getParentId());
        return equipMapper.insertDevEquip(equip);
    }

    /**
     * 修改设备
     * @param equip 设备
     * @return 结果
     */
    @Override
    public int updateDevEquip(DevEquip equip)
    {
        DevEquip newParentEquip = equipMapper.selectDevEquipById(equip.getParentId());
        DevEquip oldEquip = equipMapper.selectDevEquipById(equip.getEquipId());
        if(StringUtils.isNotNull(newParentEquip) && StringUtils.isNotNull(oldEquip)){
            String newAncestors = newParentEquip.getAncestors() + "," + newParentEquip.getEquipId();
            String oldAncestors = oldEquip.getAncestors();
            equip.setAncestors(newAncestors);
            //修改子设备的父节点列表
            updateEquipChildren(equip.getEquipId(), newAncestors, oldAncestors);
        }
        int result = equipMapper.updateDevEquip(equip);
        if (EquipConstants.EQUIP_NORMAL.equals(equip.getStatus()))
        {
            // 如果设备是启用状态，则启用该设备的所有上级设备
            updateParentEquipStatus(equip);
        }

        return result;
    }

    /**
     * 根据部门修改父级部门的状态
     * @param equip 当前部门
     */
    private void updateParentEquipStatus(DevEquip equip) {
        String updateBy = equip.getUpdateBy();
        equip = equipMapper.selectDevEquipById(equip.getEquipId());
        equip.setUpdateBy(updateBy);
        equipMapper.updateEquipStatus(equip);
    }

    /**
     * 递归修改子元素关系
     * @param equipId 被修改的设备id
     * @param newAncestors 新的父id集合
     * @param oldAncestors 旧的父id集合
     */
    private void updateEquipChildren(Long equipId, String newAncestors, String oldAncestors) {
        List<DevEquip> childrens = equipMapper.selectChildrenEquipById(equipId);
        for (DevEquip child : childrens) {
            child.setAncestors(child.getAncestors().replace(oldAncestors, newAncestors));
        }
        if(childrens.size() > 0){
            equipMapper.updateEquipChildren(childrens);
        }
    }

    /**
     * 递归列表
     * @param list
     * @param e
     */
    private void recursionFn(List<DevEquip> list, DevEquip e) {
        List<DevEquip> childList = getChildList(list, e);
        e.setChildren(childList);
        for (DevEquip tempChild : childList) {
            if(hasChild(list, tempChild)){
                //有子节点
                Iterator<DevEquip> iterator = childList.iterator();
                while (iterator.hasNext()){
                    DevEquip n = iterator.next();
                    recursionFn(list, n);
                }
            }

        }
    }

    /**
     * 得到子节点列表
     * @param list
     * @param e
     * @return
     */
    private List<DevEquip> getChildList(List<DevEquip> list, DevEquip e) {
        List<DevEquip> tempList = new ArrayList<>();
        // 对list进行全部遍历
        Iterator<DevEquip> it = list.iterator();
        while(it.hasNext()){
            DevEquip tempEquip = (DevEquip) it.next();
            // 判断是否是e的子节点
            if(StringUtils.isNotNull(tempEquip.getParentId()) && tempEquip.getParentId().longValue() == e.getEquipId().longValue()){
                // 如果是 则添加到tempEquip中
                tempList.add(tempEquip);
            }
        }
        return tempList;
    }

    /**
     * 判断是否有子节点
     * @param list
     * @param equip
     * @return
     */
    private boolean hasChild(List<DevEquip> list, DevEquip equip){
        return getChildList(list, equip).size()>0 ? true : false;
    }



}
