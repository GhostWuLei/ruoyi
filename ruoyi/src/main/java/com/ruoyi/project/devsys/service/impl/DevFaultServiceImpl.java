package com.ruoyi.project.devsys.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.devsys.mapper.DevFaultMapper;
import com.ruoyi.project.devsys.domain.DevFault;
import com.ruoyi.project.devsys.service.IDevFaultService;

/**
 * 故障记录Service业务层处理
 * 
 * @author wulei
 * @date 2020-10-30
 */
@Service
public class DevFaultServiceImpl implements IDevFaultService 
{
    @Autowired
    private DevFaultMapper devFaultMapper;

    /**
     * 查询故障记录
     * 
     * @param faultId 故障记录ID
     * @return 故障记录
     */
    @Override
    public DevFault selectDevFaultById(Long faultId)
    {
        return devFaultMapper.selectDevFaultById(faultId);
    }

    /**
     * 查询故障记录列表
     * 
     * @param devFault 故障记录
     * @return 故障记录
     */
    @Override
    public List<DevFault> selectDevFaultList(DevFault devFault)
    {
        return devFaultMapper.selectDevFaultList(devFault);
    }

    /**
     * 新增故障记录
     * 
     * @param devFault 故障记录
     * @return 结果
     */
    @Override
    public int insertDevFault(DevFault devFault)
    {
        devFault.setCreateTime(DateUtils.getNowDate());
        return devFaultMapper.insertDevFault(devFault);
    }

    /**
     * 修改故障记录
     * 
     * @param devFault 故障记录
     * @return 结果
     */
    @Override
    public int updateDevFault(DevFault devFault)
    {
        devFault.setUpdateTime(DateUtils.getNowDate());
        return devFaultMapper.updateDevFault(devFault);
    }

    /**
     * 批量删除故障记录
     * 
     * @param faultIds 需要删除的故障记录ID
     * @return 结果
     */
    @Override
    public int deleteDevFaultByIds(Long[] faultIds)
    {
        return devFaultMapper.deleteDevFaultByIds(faultIds);
    }

    /**
     * 删除故障记录信息
     * 
     * @param faultId 故障记录ID
     * @return 结果
     */
    @Override
    public int deleteDevFaultById(Long faultId)
    {
        return devFaultMapper.deleteDevFaultById(faultId);
    }
}
