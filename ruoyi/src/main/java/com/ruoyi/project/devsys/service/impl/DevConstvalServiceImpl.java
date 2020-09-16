package com.ruoyi.project.devsys.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.devsys.mapper.DevConstvalMapper;
import com.ruoyi.project.devsys.domain.DevConstval;
import com.ruoyi.project.devsys.service.IDevConstvalService;

/**
 * 设备定值Service业务层处理
 * 
 * @author wulei
 * @date 2020-06-15
 */
@Service
public class DevConstvalServiceImpl implements IDevConstvalService 
{
    @Autowired
    private DevConstvalMapper devConstvalMapper;

    /**
     * 查询设备定值
     * 
     * @param constvalId 设备定值ID
     * @return 设备定值
     */
    @Override
    public DevConstval selectDevConstvalById(Long constvalId)
    {
        return devConstvalMapper.selectDevConstvalById(constvalId);
    }

    /**
     * 查询设备定值列表
     * 
     * @param devConstval 设备定值
     * @return 设备定值
     */
    @Override
    public List<DevConstval> selectDevConstvalList(DevConstval devConstval)
    {
        return devConstvalMapper.selectDevConstvalList(devConstval);
    }

    /**
     * 新增设备定值
     * 
     * @param devConstval 设备定值
     * @return 结果
     */
    @Override
    public int insertDevConstval(DevConstval devConstval)
    {
        devConstval.setCreateTime(DateUtils.getNowDate());
        return devConstvalMapper.insertDevConstval(devConstval);
    }

    /**
     * 修改设备定值
     * 
     * @param devConstval 设备定值
     * @return 结果
     */
    @Override
    public int updateDevConstval(DevConstval devConstval)
    {
        devConstval.setUpdateTime(DateUtils.getNowDate());
        return devConstvalMapper.updateDevConstval(devConstval);
    }

    /**
     * 批量删除设备定值
     * 
     * @param constvalIds 需要删除的设备定值ID
     * @return 结果
     */
    @Override
    public int deleteDevConstvalByIds(Long[] constvalIds)
    {
        return devConstvalMapper.deleteDevConstvalByIds(constvalIds);
    }

    /**
     * 删除设备定值信息
     * 
     * @param constvalId 设备定值ID
     * @return 结果
     */
    @Override
    public int deleteDevConstvalById(Long constvalId)
    {
        return devConstvalMapper.deleteDevConstvalById(constvalId);
    }
}
