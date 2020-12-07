package com.ruoyi.project.devsys.mapper;

import com.ruoyi.project.devsys.domain.DevAlteration;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 设备变更Mapper接口
 *
 * @author wulei
 * @date 2020-10-30
 */
public interface DevAlterationMapper
{
    /**
     * 查询设备变更
     *
     * @param alterationId 设备变更ID
     * @return 设备变更
     */
    public DevAlteration selectDevAlterationById(Long alterationId);

    /**
     * 查询设备变更列表
     *
     * @param devAlteration 设备变更
     * @return 设备变更集合
     */
    public List<DevAlteration> selectDevAlterationList(DevAlteration devAlteration);

    /**
     * 新增设备变更
     *
     * @param devAlteration 设备变更
     * @return 结果
     */
    public int insertDevAlteration(DevAlteration devAlteration);

    /**
     * 修改设备变更
     *
     * @param devAlteration 设备变更
     * @return 结果
     */
    public int updateDevAlteration(DevAlteration devAlteration);

    /**
     * 删除设备变更
     *
     * @param alterationId 设备变更ID
     * @return 结果
     */
    public int deleteDevAlterationById(Long alterationId);

    /**
     * 批量删除设备变更
     *
     * @param alterationIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteDevAlterationByIds(Long[] alterationIds);

    DevAlteration selectByName(@Param("equipName") String equipName);

    List<DevAlteration> selectDevAlterationListIn(List<Long> list);

    int deleteequipId(@Param("equipId") Long equipId);
}
