package com.ruoyi.project.devsys.mapper;

import com.ruoyi.project.devsys.domain.DevRepair;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 检修记录Mapper接口
 *
 * @author wulei
 * @date 2020-10-26
 */
public interface DevRepairMapper
{
    /**
     * 查询检修记录
     *
     * @param repairId 检修记录ID
     * @return 检修记录
     */
    public DevRepair selectDevRepairById(Long repairId);

    /**
     * 查询检修记录列表
     *
     * @param devRepair 检修记录
     * @return 检修记录集合
     */
    public List<DevRepair> selectDevRepairList(DevRepair devRepair);

    /**
     * 新增检修记录
     *
     * @param devRepair 检修记录
     * @return 结果
     */
    public int insertDevRepair(DevRepair devRepair);

    /**
     * 修改检修记录
     *
     * @param devRepair 检修记录
     * @return 结果
     */
    public int updateDevRepair(DevRepair devRepair);

    /**
     * 删除检修记录
     *
     * @param repairId 检修记录ID
     * @return 结果
     */
    public int deleteDevRepairById(Long repairId);

    /**
     * 批量删除检修记录
     *
     * @param repairIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteDevRepairByIds(Long[] repairIds);

    DevRepair selectByName(@Param("repairContent") String repairContent);

    List<DevRepair> selectDevRepairListIn(List<Long> list);

    int deleteequipId(@Param("equipId") Long equipId);
}
