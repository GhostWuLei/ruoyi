package com.ruoyi.project.devsys.mapper;

import java.util.List;
import com.ruoyi.project.devsys.domain.DevAlteration;

/**
 * 异动变更Mapper接口
 * 
 * @author wulei
 * @date 2020-06-16
 */
public interface DevAlterationMapper 
{
    /**
     * 查询异动变更
     * 
     * @param alterationId 异动变更ID
     * @return 异动变更
     */
    public DevAlteration selectDevAlterationById(Long alterationId);

    /**
     * 查询异动变更列表
     * 
     * @param devAlteration 异动变更
     * @return 异动变更集合
     */
    public List<DevAlteration> selectDevAlterationList(DevAlteration devAlteration);

    /**
     * 新增异动变更
     * 
     * @param devAlteration 异动变更
     * @return 结果
     */
    public int insertDevAlteration(DevAlteration devAlteration);

    /**
     * 修改异动变更
     * 
     * @param devAlteration 异动变更
     * @return 结果
     */
    public int updateDevAlteration(DevAlteration devAlteration);

    /**
     * 删除异动变更
     * 
     * @param alterationId 异动变更ID
     * @return 结果
     */
    public int deleteDevAlterationById(Long alterationId);

    /**
     * 批量删除异动变更
     * 
     * @param alterationIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteDevAlterationByIds(Long[] alterationIds);
}
