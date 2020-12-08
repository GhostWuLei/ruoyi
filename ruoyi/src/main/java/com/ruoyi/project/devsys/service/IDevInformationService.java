package com.ruoyi.project.devsys.service;

import com.ruoyi.project.devsys.domain.DevEquip;
import com.ruoyi.project.devsys.domain.DevInformation;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 设备信息Service接口
 *
 * @author wulei
 * @date 2020-10-30
 */
public interface IDevInformationService
{
    /**
     * 查询设备信息
     *
     * @param informationId 设备信息ID
     * @return 设备信息
     */
    public DevInformation selectDevInformationById(Long informationId);

    /**
     * 查询设备信息列表
     *
     * @param devInformation 设备信息
     * @return 设备信息集合
     */
    public List<DevInformation> selectDevInformationList(DevInformation devInformation);

    /**
     * 新增设备信息
     *
     * @param devInformation 设备信息
     * @return 结果
     */
    public int insertDevInformation(DevInformation devInformation);

    /**
     * 修改设备信息
     *
     * @param devInformation 设备信息
     * @return 结果
     */
    public int updateDevInformation(DevInformation devInformation);

    /**
     * 批量删除设备信息
     *
     * @param informationIds 需要删除的设备信息ID
     * @return 结果
     */
    public int deleteDevInformationByIds(Long[] informationIds);

    /**
     * 删除设备信息信息
     *
     * @param informationId 设备信息ID
     * @return 结果
     */
    public int deleteDevInformationById(Long informationId);

    boolean uploadFile(Long informationId, MultipartFile[] files);

    void deleteAnnex(String fpath);

    String importUser(List<DevInformation> devInformationList, boolean updateSupport, String username, Long equipId);
    

    int deleteequipId(Long equipId);

    List<DevInformation> selectDevInformationListAll(DevInformation devInformation);

    List<DevInformation> selectDevInformationListIn(List<DevEquip> devEquips);
}
