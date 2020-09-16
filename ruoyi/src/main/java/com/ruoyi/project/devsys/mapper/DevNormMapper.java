package com.ruoyi.project.devsys.mapper;

import java.util.List;
import com.ruoyi.project.devsys.domain.DevNorm;

/**
 * 设备规范Mapper接口
 * 
 * @author wulei
 * @date 2020-06-16
 */
public interface DevNormMapper 
{
    /**
     * 查询设备规范
     * 
     * @param normId 设备规范ID
     * @return 设备规范
     */
    public DevNorm selectDevNormById(Long normId);

    /**
     * 查询设备规范列表
     * 
     * @param devNorm 设备规范
     * @return 设备规范集合
     */
    public List<DevNorm> selectDevNormList(DevNorm devNorm);

    /**
     * 新增设备规范
     * 
     * @param devNorm 设备规范
     * @return 结果
     */
    public int insertDevNorm(DevNorm devNorm);

    /**
     * 修改设备规范
     * 
     * @param devNorm 设备规范
     * @return 结果
     */
    public int updateDevNorm(DevNorm devNorm);

    /**
     * 删除设备规范
     * 
     * @param normId 设备规范ID
     * @return 结果
     */
    public int deleteDevNormById(Long normId);

    /**
     * 批量删除设备规范
     * 
     * @param normIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteDevNormByIds(Long[] normIds);
}
