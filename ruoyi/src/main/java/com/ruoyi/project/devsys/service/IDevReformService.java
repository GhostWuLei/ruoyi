package com.ruoyi.project.devsys.service;

import java.util.List;
import com.ruoyi.project.devsys.domain.DevReform;

/**
 * 重大技改Service接口
 * 
 * @author wulei
 * @date 2020-06-16
 */
public interface IDevReformService 
{
    /**
     * 查询重大技改
     * 
     * @param reformId 重大技改ID
     * @return 重大技改
     */
    public DevReform selectDevReformById(Long reformId);

    /**
     * 查询重大技改列表
     * 
     * @param devReform 重大技改
     * @return 重大技改集合
     */
    public List<DevReform> selectDevReformList(DevReform devReform);

    /**
     * 新增重大技改
     * 
     * @param devReform 重大技改
     * @return 结果
     */
    public int insertDevReform(DevReform devReform);

    /**
     * 修改重大技改
     * 
     * @param devReform 重大技改
     * @return 结果
     */
    public int updateDevReform(DevReform devReform);

    /**
     * 批量删除重大技改
     * 
     * @param reformIds 需要删除的重大技改ID
     * @return 结果
     */
    public int deleteDevReformByIds(Long[] reformIds);

    /**
     * 删除重大技改信息
     * 
     * @param reformId 重大技改ID
     * @return 结果
     */
    public int deleteDevReformById(Long reformId);
}
