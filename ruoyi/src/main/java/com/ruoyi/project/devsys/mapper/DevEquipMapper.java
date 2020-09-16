package com.ruoyi.project.devsys.mapper;

import java.util.List;
import com.ruoyi.project.devsys.domain.DevEquip;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * 设备Mapper接口
 * 
 * @author wulei
 * @date 2020-05-27
 */
public interface DevEquipMapper 
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
     * 删除设备
     * 
     * @param equipId 设备ID
     * @return 结果
     */
    public int deleteDevEquipById(Long equipId);

    /**
     * 批量删除设备
     * 
     * @param equipIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteDevEquipByIds(Long[] equipIds);

    //#########################################################

    /**
     * 校验同级设备目录下设备名称是否唯一
     * @param equipName
     * @param parentId
     * @return
     */
    DevEquip checkEquipNameUnique(@Param("equipName") String equipName, @Param("parentId") Long parentId);

    /**
     * 根据id查询设备下面是否还有子设备
     * @param equipId
     * @return
     */
    int hasChildByEquipId(Long equipId);

    /**
     * 根据设备id查询子设备
     * @param equipId
     * @return
     */
    List<DevEquip> selectChildrenEquipById(Long equipId);

    /**
     * 修改子元素关系
     * @param equips
     */
    void updateEquipChildren(@Param("equips") List<DevEquip> equips);

    /**
     * 修改该设备所有上级设备的状态
     * @param equip
     */
    void updateEquipStatus(DevEquip equip);
}
