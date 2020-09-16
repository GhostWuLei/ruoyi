package com.ruoyi.project.devsys.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.devsys.mapper.DevNormMapper;
import com.ruoyi.project.devsys.domain.DevNorm;
import com.ruoyi.project.devsys.service.IDevNormService;

/**
 * 设备规范Service业务层处理
 * 
 * @author wulei
 * @date 2020-06-16
 */
@Service
public class DevNormServiceImpl implements IDevNormService 
{
    @Autowired
    private DevNormMapper devNormMapper;

    /**
     * 查询设备规范
     * 
     * @param normId 设备规范ID
     * @return 设备规范
     */
    @Override
    public DevNorm selectDevNormById(Long normId)
    {
        return devNormMapper.selectDevNormById(normId);
    }

    /**
     * 查询设备规范列表
     * 
     * @param devNorm 设备规范
     * @return 设备规范
     */
    @Override
    public List<DevNorm> selectDevNormList(DevNorm devNorm)
    {
        return devNormMapper.selectDevNormList(devNorm);
    }


    /**
     * 批量删除设备规范
     * 
     * @param normIds 需要删除的设备规范ID
     * @return 结果
     */
    @Override
    public int deleteDevNormByIds(Long[] normIds)
    {
        return devNormMapper.deleteDevNormByIds(normIds);
    }

    /**
     * 删除设备规范信息
     * 
     * @param normId 设备规范ID
     * @return 结果
     */
    @Override
    public int deleteDevNormById(Long normId)
    {
        return devNormMapper.deleteDevNormById(normId);
    }

    //######################################################################

    /**
     * 新增设备规范
     *
     * @param norm 设备规范
     * @return 结果
     */
    @Override
    public int insertDevNorm(DevNorm norm)
    {
        norm.setCreateTime(DateUtils.getNowDate());
        norm.setCreateBy(SecurityUtils.getUsername());
        return devNormMapper.insertDevNorm(norm);
    }

    /**
     * 修改设备规范
     *
     * @param norm 设备规范
     * @return 结果
     */
    @Override
    public int updateDevNorm(DevNorm norm)
    {
        norm.setUpdateTime(DateUtils.getNowDate());
        norm.setUpdateBy(SecurityUtils.getUsername());
        return devNormMapper.updateDevNorm(norm);
    }
}
