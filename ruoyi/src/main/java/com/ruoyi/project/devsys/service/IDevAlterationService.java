package com.ruoyi.project.devsys.service;

import java.util.List;
import com.ruoyi.project.devsys.domain.DevAlteration;
import org.springframework.web.multipart.MultipartFile;

/**
 * 设备变更Service接口
 *
 * @author wulei
 * @date 2020-10-30
 */
public interface IDevAlterationService
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
     * 批量删除设备变更
     *
     * @param alterationIds 需要删除的设备变更ID
     * @return 结果
     */
    public int deleteDevAlterationByIds(Long[] alterationIds);

    /**
     * 删除设备变更信息
     *
     * @param alterationId 设备变更ID
     * @return 结果
     */
    public int deleteDevAlterationById(Long alterationId);

    boolean uploadFile(Long alterationId, MultipartFile[] files);

    String importUser(List<DevAlteration> devAlterationList, boolean updateSupport, String username, Long equipId);
}
