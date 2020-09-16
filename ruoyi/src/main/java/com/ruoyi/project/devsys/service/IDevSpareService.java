package com.ruoyi.project.devsys.service;

import java.util.List;
import com.ruoyi.project.devsys.domain.DevSpare;

/**
 * 备品备件Service接口
 * 
 * @author wulei
 * @date 2020-06-08
 */
public interface IDevSpareService 
{
    /**
     * 查询备品备件
     * 
     * @param spareId 备品备件ID
     * @return 备品备件
     */
    public DevSpare selectDevSpareById(Long spareId);

    /**
     * 查询备品备件列表
     * 
     * @param devSpare 备品备件
     * @return 备品备件集合
     */
    public List<DevSpare> selectDevSpareList(DevSpare devSpare);

    /**
     * 新增备品备件
     * 
     * @param devSpare 备品备件
     * @return 结果
     */
    public int insertDevSpare(DevSpare devSpare);

    /**
     * 修改备品备件
     * 
     * @param devSpare 备品备件
     * @return 结果
     */
    public int updateDevSpare(DevSpare devSpare);

    /**
     * 批量删除备品备件
     * 
     * @param spareIds 需要删除的备品备件ID
     * @return 结果
     */
    public int deleteDevSpareByIds(Long[] spareIds);

    /**
     * 删除备品备件信息
     * 
     * @param spareId 备品备件ID
     * @return 结果
     */
    public int deleteDevSpareById(Long spareId);
}
