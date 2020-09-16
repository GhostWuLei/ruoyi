package com.ruoyi.project.devsys.service;

import java.util.List;

import com.ruoyi.framework.web.domain.TreeSelect;
import com.ruoyi.project.devsys.domain.DevEquip;

/**
 * 设备Service接口
 * 
 * @author wulei
 * @date 2020-05-27
 */
public interface IDevEquipService 
{
    /**
     * 查询设备
     * 
     * @param equipId 设备ID
     * @return 设备
     */
    public DevEquip selectDevEquipById(Long equipId);

    /**
     * 查询设备列表
     * 
     * @param devEquip 设备
     * @return 设备集合
     */
    public List<DevEquip> selectDevEquipList(DevEquip devEquip);

    /**
     * 新增设备
     * 
     * @param devEquip 设备
     * @return 结果
     */
    public int insertDevEquip(DevEquip devEquip);

    /**
     * 修改设备
     * 
     * @param devEquip 设备
     * @return 结果
     */
    public int updateDevEquip(DevEquip devEquip);

    /**
     * 批量删除设备
     * 
     * @param equipIds 需要删除的设备ID
     * @return 结果
     */
    public int deleteDevEquipByIds(Long[] equipIds);

    /**
     * 删除设备信息
     * 
     * @param equipId 设备ID
     * @return 结果
     */
    public int deleteDevEquipById(Long equipId);

    // ####################################################################
    /**
     * 构建前端所需要设备树结构
     * @param equipList
     * @return
     */
    List<TreeSelect> buildEquipTreeSelect(List<DevEquip> equipList);

    /**
     * 构建前端所需要树结构
     * @param equipList 设备列表
     * @return 树结构列表
     */
    List<DevEquip> buildEquipTree(List<DevEquip> equipList);

    /**
     * 校验设备名称在同级下是否唯一
     * @param equip
     * @return
     */
    String checkEquipNameUnique(DevEquip equip);

    /**
     * 查询所选设备下面是否还有子设备/目录
     * @param equipId
     * @return
     */
    boolean hasChildByEquipId(Long equipId);
}
