package com.ruoyi.project.devsys.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.devsys.mapper.DevAlterationMapper;
import com.ruoyi.project.devsys.domain.DevAlteration;
import com.ruoyi.project.devsys.service.IDevAlterationService;

/**
 * 异动变更Service业务层处理
 * 
 * @author wulei
 * @date 2020-06-16
 */
@Service
public class DevAlterationServiceImpl implements IDevAlterationService 
{
    @Autowired
    private DevAlterationMapper devAlterationMapper;

    /**
     * 查询异动变更
     * 
     * @param alterationId 异动变更ID
     * @return 异动变更
     */
    @Override
    public DevAlteration selectDevAlterationById(Long alterationId)
    {
        return devAlterationMapper.selectDevAlterationById(alterationId);
    }

    /**
     * 查询异动变更列表
     * 
     * @param devAlteration 异动变更
     * @return 异动变更
     */
    @Override
    public List<DevAlteration> selectDevAlterationList(DevAlteration devAlteration)
    {
        return devAlterationMapper.selectDevAlterationList(devAlteration);
    }

    /**
     * 批量删除异动变更
     * 
     * @param alterationIds 需要删除的异动变更ID
     * @return 结果
     */
    @Override
    public int deleteDevAlterationByIds(Long[] alterationIds)
    {
        return devAlterationMapper.deleteDevAlterationByIds(alterationIds);
    }

    /**
     * 删除异动变更信息
     * 
     * @param alterationId 异动变更ID
     * @return 结果
     */
    @Override
    public int deleteDevAlterationById(Long alterationId)
    {
        return devAlterationMapper.deleteDevAlterationById(alterationId);
    }

    //##################################################

    /**
     * 新增异动变更
     *
     * @param alteration 异动变更
     * @return 结果
     */
    @Override
    public int insertDevAlteration(DevAlteration alteration)
    {
        alteration.setCreateTime(DateUtils.getNowDate());
        alteration.setCreateBy(SecurityUtils.getUsername());
        return devAlterationMapper.insertDevAlteration(alteration);
    }

    /**
     * 修改异动变更
     *
     * @param alteration 异动变更
     * @return 结果
     */
    @Override
    public int updateDevAlteration(DevAlteration alteration)
    {
        alteration.setUpdateTime(DateUtils.getNowDate());
        alteration.setUpdateBy(SecurityUtils.getUsername());
        return devAlterationMapper.updateDevAlteration(alteration);
    }

}
