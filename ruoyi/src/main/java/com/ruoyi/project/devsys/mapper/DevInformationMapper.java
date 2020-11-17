package com.ruoyi.project.devsys.mapper;

import java.util.List;
import com.ruoyi.project.devsys.domain.DevInformation;
import com.ruoyi.project.devsys.domain.DevSpare;
import org.apache.ibatis.annotations.Param;

/**
 * 设备信息Mapper接口
 *
 * @author wulei
 * @date 2020-10-30
 */
public interface DevInformationMapper
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
     * 删除设备信息
     *
     * @param informationId 设备信息ID
     * @return 结果
     */
    public int deleteDevInformationById(Long informationId);

    /**
     * 批量删除设备信息
     *
     * @param informationIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteDevInformationByIds(Long[] informationIds);

    DevInformation selectName(@Param("equipName") String equipName);
}
