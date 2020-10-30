package com.ruoyi.project.devsys.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.devsys.mapper.DevInformationMapper;
import com.ruoyi.project.devsys.domain.DevInformation;
import com.ruoyi.project.devsys.service.IDevInformationService;

/**
 * 设备信息Service业务层处理
 * 
 * @author wulei
 * @date 2020-10-30
 */
@Service
public class DevInformationServiceImpl implements IDevInformationService 
{
    @Autowired
    private DevInformationMapper devInformationMapper;

    /**
     * 查询设备信息
     * 
     * @param informationId 设备信息ID
     * @return 设备信息
     */
    @Override
    public DevInformation selectDevInformationById(Long informationId)
    {
        return devInformationMapper.selectDevInformationById(informationId);
    }

    /**
     * 查询设备信息列表
     * 
     * @param devInformation 设备信息
     * @return 设备信息
     */
    @Override
    public List<DevInformation> selectDevInformationList(DevInformation devInformation)
    {
        return devInformationMapper.selectDevInformationList(devInformation);
    }

    /**
     * 新增设备信息
     * 
     * @param devInformation 设备信息
     * @return 结果
     */
    @Override
    public int insertDevInformation(DevInformation devInformation)
    {
        devInformation.setCreateTime(DateUtils.getNowDate());
        return devInformationMapper.insertDevInformation(devInformation);
    }

    /**
     * 修改设备信息
     * 
     * @param devInformation 设备信息
     * @return 结果
     */
    @Override
    public int updateDevInformation(DevInformation devInformation)
    {
        devInformation.setUpdateTime(DateUtils.getNowDate());
        return devInformationMapper.updateDevInformation(devInformation);
    }

    /**
     * 批量删除设备信息
     * 
     * @param informationIds 需要删除的设备信息ID
     * @return 结果
     */
    @Override
    public int deleteDevInformationByIds(Long[] informationIds)
    {
        return devInformationMapper.deleteDevInformationByIds(informationIds);
    }

    /**
     * 删除设备信息信息
     * 
     * @param informationId 设备信息ID
     * @return 结果
     */
    @Override
    public int deleteDevInformationById(Long informationId)
    {
        return devInformationMapper.deleteDevInformationById(informationId);
    }
}
