package com.ruoyi.project.devsys.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.devsys.mapper.DevAlterationMapper;
import com.ruoyi.project.devsys.domain.DevAlteration;
import com.ruoyi.project.devsys.service.IDevAlterationService;

/**
 * 设备变更Service业务层处理
 * 
 * @author wulei
 * @date 2020-10-30
 */
@Service
public class DevAlterationServiceImpl implements IDevAlterationService 
{
    @Autowired
    private DevAlterationMapper devAlterationMapper;

    /**
     * 查询设备变更
     * 
     * @param alterationId 设备变更ID
     * @return 设备变更
     */
    @Override
    public DevAlteration selectDevAlterationById(Long alterationId)
    {
        return devAlterationMapper.selectDevAlterationById(alterationId);
    }

    /**
     * 查询设备变更列表
     * 
     * @param devAlteration 设备变更
     * @return 设备变更
     */
    @Override
    public List<DevAlteration> selectDevAlterationList(DevAlteration devAlteration)
    {
        return devAlterationMapper.selectDevAlterationList(devAlteration);
    }

    /**
     * 新增设备变更
     * 
     * @param devAlteration 设备变更
     * @return 结果
     */
    @Override
    public int insertDevAlteration(DevAlteration devAlteration)
    {
        devAlteration.setCreateTime(DateUtils.getNowDate());
        return devAlterationMapper.insertDevAlteration(devAlteration);
    }

    /**
     * 修改设备变更
     * 
     * @param devAlteration 设备变更
     * @return 结果
     */
    @Override
    public int updateDevAlteration(DevAlteration devAlteration)
    {
        devAlteration.setUpdateTime(DateUtils.getNowDate());
        return devAlterationMapper.updateDevAlteration(devAlteration);
    }

    /**
     * 批量删除设备变更
     * 
     * @param alterationIds 需要删除的设备变更ID
     * @return 结果
     */
    @Override
    public int deleteDevAlterationByIds(Long[] alterationIds)
    {
        return devAlterationMapper.deleteDevAlterationByIds(alterationIds);
    }

    /**
     * 删除设备变更信息
     * 
     * @param alterationId 设备变更ID
     * @return 结果
     */
    @Override
    public int deleteDevAlterationById(Long alterationId)
    {
        return devAlterationMapper.deleteDevAlterationById(alterationId);
    }
}
