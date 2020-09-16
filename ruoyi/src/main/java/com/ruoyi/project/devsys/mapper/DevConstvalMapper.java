package com.ruoyi.project.devsys.mapper;

import java.util.List;
import com.ruoyi.project.devsys.domain.DevConstval;

/**
 * 设备定值Mapper接口
 * @author wulei
 * @date 2020-06-15
 */
public interface DevConstvalMapper 
{
    /**
     * 查询设备定值
     * @param constvalId 设备定值ID
     * @return 设备定值
     */
    public DevConstval selectDevConstvalById(Long constvalId);

    /**
     * 查询设备定值列表
     * @param devConstval 设备定值
     * @return 设备定值集合
     */
    public List<DevConstval> selectDevConstvalList(DevConstval devConstval);

    /**
     * 新增设备定值
     * @param devConstval 设备定值
     * @return 结果
     */
    public int insertDevConstval(DevConstval devConstval);

    /**
     * 修改设备定值
     * @param devConstval 设备定值
     * @return 结果
     */
    public int updateDevConstval(DevConstval devConstval);

    /**
     * 删除设备定值
     * @param constvalId 设备定值ID
     * @return 结果
     */
    public int deleteDevConstvalById(Long constvalId);

    /**
     * 批量删除设备定值
     * @param constvalIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteDevConstvalByIds(Long[] constvalIds);
}
