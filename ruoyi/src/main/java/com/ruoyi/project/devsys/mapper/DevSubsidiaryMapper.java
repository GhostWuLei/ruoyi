package com.ruoyi.project.devsys.mapper;

import java.util.List;
import com.ruoyi.project.devsys.domain.DevSubsidiary;

/**
 * 附属设备明细Mapper接口
 * 
 * @author wulei
 * @date 2020-10-30
 */
public interface DevSubsidiaryMapper 
{
    /**
     * 查询附属设备明细
     * 
     * @param subsidiaryId 附属设备明细ID
     * @return 附属设备明细
     */
    public DevSubsidiary selectDevSubsidiaryById(Long subsidiaryId);

    /**
     * 查询附属设备明细列表
     * 
     * @param devSubsidiary 附属设备明细
     * @return 附属设备明细集合
     */
    public List<DevSubsidiary> selectDevSubsidiaryList(DevSubsidiary devSubsidiary);

    /**
     * 新增附属设备明细
     * 
     * @param devSubsidiary 附属设备明细
     * @return 结果
     */
    public int insertDevSubsidiary(DevSubsidiary devSubsidiary);

    /**
     * 修改附属设备明细
     * 
     * @param devSubsidiary 附属设备明细
     * @return 结果
     */
    public int updateDevSubsidiary(DevSubsidiary devSubsidiary);

    /**
     * 删除附属设备明细
     * 
     * @param subsidiaryId 附属设备明细ID
     * @return 结果
     */
    public int deleteDevSubsidiaryById(Long subsidiaryId);

    /**
     * 批量删除附属设备明细
     * 
     * @param subsidiaryIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteDevSubsidiaryByIds(Long[] subsidiaryIds);
}
