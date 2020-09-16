package com.ruoyi.project.devsys.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.devsys.mapper.DevRepairMapper;
import com.ruoyi.project.devsys.domain.DevRepair;
import com.ruoyi.project.devsys.service.IDevRepairService;

/**
 * 检修记录Service业务层处理
 * 
 * @author wulei
 * @date 2020-06-15
 */
@Service
public class DevRepairServiceImpl implements IDevRepairService 
{
    @Autowired
    private DevRepairMapper devRepairMapper;

    /**
     * 查询检修记录
     * @param repairId 检修记录ID
     * @return 检修记录
     */
    @Override
    public DevRepair selectDevRepairById(Long repairId)
    {
        return devRepairMapper.selectDevRepairById(repairId);
    }

    /**
     * 查询检修记录列表
     * @param devRepair 检修记录
     * @return 检修记录
     */
    @Override
    public List<DevRepair> selectDevRepairList(DevRepair devRepair)
    {
        return devRepairMapper.selectDevRepairList(devRepair);
    }

    /**
     * 批量删除检修记录
     * @param repairIds 需要删除的检修记录ID
     * @return 结果
     */
    @Override
    public int deleteDevRepairByIds(Long[] repairIds)
    {
        return devRepairMapper.deleteDevRepairByIds(repairIds);
    }

    /**
     * 删除检修记录信息
     * @param repairId 检修记录ID
     * @return 结果
     */
    @Override
    public int deleteDevRepairById(Long repairId)
    {
        return devRepairMapper.deleteDevRepairById(repairId);
    }

    //######################################################################################################
    /**
     * 新增检修记录
     * @param repair 检修记录
     * @return 结果
     */
    @Override
    public int insertDevRepair(DevRepair repair)
    {
        repair.setCreateTime(DateUtils.getNowDate());
        repair.setCreateBy(SecurityUtils.getUsername());
        return devRepairMapper.insertDevRepair(repair);
    }

    /**
     * 修改检修记录
     * @param repair 检修记录
     * @return 结果
     */
    @Override
    public int updateDevRepair(DevRepair repair)
    {
        repair.setUpdateTime(DateUtils.getNowDate());
        repair.setUpdateBy(SecurityUtils.getUsername());
        return devRepairMapper.updateDevRepair(repair);
    }
}
