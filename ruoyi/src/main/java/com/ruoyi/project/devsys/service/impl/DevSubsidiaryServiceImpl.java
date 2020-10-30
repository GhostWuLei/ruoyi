package com.ruoyi.project.devsys.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.devsys.mapper.DevSubsidiaryMapper;
import com.ruoyi.project.devsys.domain.DevSubsidiary;
import com.ruoyi.project.devsys.service.IDevSubsidiaryService;

/**
 * 附属设备明细Service业务层处理
 * 
 * @author wulei
 * @date 2020-10-30
 */
@Service
public class DevSubsidiaryServiceImpl implements IDevSubsidiaryService 
{
    @Autowired
    private DevSubsidiaryMapper devSubsidiaryMapper;

    /**
     * 查询附属设备明细
     * 
     * @param subsidiaryId 附属设备明细ID
     * @return 附属设备明细
     */
    @Override
    public DevSubsidiary selectDevSubsidiaryById(Long subsidiaryId)
    {
        return devSubsidiaryMapper.selectDevSubsidiaryById(subsidiaryId);
    }

    /**
     * 查询附属设备明细列表
     * 
     * @param devSubsidiary 附属设备明细
     * @return 附属设备明细
     */
    @Override
    public List<DevSubsidiary> selectDevSubsidiaryList(DevSubsidiary devSubsidiary)
    {
        return devSubsidiaryMapper.selectDevSubsidiaryList(devSubsidiary);
    }

    /**
     * 新增附属设备明细
     * 
     * @param devSubsidiary 附属设备明细
     * @return 结果
     */
    @Override
    public int insertDevSubsidiary(DevSubsidiary devSubsidiary)
    {
        devSubsidiary.setCreateTime(DateUtils.getNowDate());
        return devSubsidiaryMapper.insertDevSubsidiary(devSubsidiary);
    }

    /**
     * 修改附属设备明细
     * 
     * @param devSubsidiary 附属设备明细
     * @return 结果
     */
    @Override
    public int updateDevSubsidiary(DevSubsidiary devSubsidiary)
    {
        devSubsidiary.setUpdateTime(DateUtils.getNowDate());
        return devSubsidiaryMapper.updateDevSubsidiary(devSubsidiary);
    }

    /**
     * 批量删除附属设备明细
     * 
     * @param subsidiaryIds 需要删除的附属设备明细ID
     * @return 结果
     */
    @Override
    public int deleteDevSubsidiaryByIds(Long[] subsidiaryIds)
    {
        return devSubsidiaryMapper.deleteDevSubsidiaryByIds(subsidiaryIds);
    }

    /**
     * 删除附属设备明细信息
     * 
     * @param subsidiaryId 附属设备明细ID
     * @return 结果
     */
    @Override
    public int deleteDevSubsidiaryById(Long subsidiaryId)
    {
        return devSubsidiaryMapper.deleteDevSubsidiaryById(subsidiaryId);
    }
}
