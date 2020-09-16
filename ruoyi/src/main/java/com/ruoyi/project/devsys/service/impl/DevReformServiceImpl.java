package com.ruoyi.project.devsys.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.devsys.mapper.DevReformMapper;
import com.ruoyi.project.devsys.domain.DevReform;
import com.ruoyi.project.devsys.service.IDevReformService;

/**
 * 重大技改Service业务层处理
 * 
 * @author wulei
 * @date 2020-06-16
 */
@Service
public class DevReformServiceImpl implements IDevReformService 
{
    @Autowired
    private DevReformMapper devReformMapper;

    /**
     * 查询重大技改
     * 
     * @param reformId 重大技改ID
     * @return 重大技改
     */
    @Override
    public DevReform selectDevReformById(Long reformId)
    {
        return devReformMapper.selectDevReformById(reformId);
    }

    /**
     * 查询重大技改列表
     * 
     * @param devReform 重大技改
     * @return 重大技改
     */
    @Override
    public List<DevReform> selectDevReformList(DevReform devReform)
    {
        return devReformMapper.selectDevReformList(devReform);
    }

    /**
     * 新增重大技改
     * 
     * @param devReform 重大技改
     * @return 结果
     */
    @Override
    public int insertDevReform(DevReform devReform)
    {
        devReform.setCreateTime(DateUtils.getNowDate());
        return devReformMapper.insertDevReform(devReform);
    }

    /**
     * 修改重大技改
     * 
     * @param devReform 重大技改
     * @return 结果
     */
    @Override
    public int updateDevReform(DevReform devReform)
    {
        devReform.setUpdateTime(DateUtils.getNowDate());
        return devReformMapper.updateDevReform(devReform);
    }

    /**
     * 批量删除重大技改
     * 
     * @param reformIds 需要删除的重大技改ID
     * @return 结果
     */
    @Override
    public int deleteDevReformByIds(Long[] reformIds)
    {
        return devReformMapper.deleteDevReformByIds(reformIds);
    }

    /**
     * 删除重大技改信息
     * 
     * @param reformId 重大技改ID
     * @return 结果
     */
    @Override
    public int deleteDevReformById(Long reformId)
    {
        return devReformMapper.deleteDevReformById(reformId);
    }
}
