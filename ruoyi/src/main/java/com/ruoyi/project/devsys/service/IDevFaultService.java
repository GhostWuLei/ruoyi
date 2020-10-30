package com.ruoyi.project.devsys.service;

import java.util.List;
import com.ruoyi.project.devsys.domain.DevFault;

/**
 * 故障记录Service接口
 * 
 * @author wulei
 * @date 2020-10-30
 */
public interface IDevFaultService 
{
    /**
     * 查询故障记录
     * 
     * @param faultId 故障记录ID
     * @return 故障记录
     */
    public DevFault selectDevFaultById(Long faultId);

    /**
     * 查询故障记录列表
     * 
     * @param devFault 故障记录
     * @return 故障记录集合
     */
    public List<DevFault> selectDevFaultList(DevFault devFault);

    /**
     * 新增故障记录
     * 
     * @param devFault 故障记录
     * @return 结果
     */
    public int insertDevFault(DevFault devFault);

    /**
     * 修改故障记录
     * 
     * @param devFault 故障记录
     * @return 结果
     */
    public int updateDevFault(DevFault devFault);

    /**
     * 批量删除故障记录
     * 
     * @param faultIds 需要删除的故障记录ID
     * @return 结果
     */
    public int deleteDevFaultByIds(Long[] faultIds);

    /**
     * 删除故障记录信息
     * 
     * @param faultId 故障记录ID
     * @return 结果
     */
    public int deleteDevFaultById(Long faultId);
}
