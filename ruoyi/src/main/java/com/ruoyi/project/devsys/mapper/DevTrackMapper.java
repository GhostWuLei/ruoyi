package com.ruoyi.project.devsys.mapper;

import java.util.List;
import com.ruoyi.project.devsys.domain.DevTrack;

/**
 * 设备跟踪Mapper接口
 * 
 * @author wulei
 * @date 2020-06-16
 */
public interface DevTrackMapper 
{
    /**
     * 查询设备跟踪
     * 
     * @param trackId 设备跟踪ID
     * @return 设备跟踪
     */
    public DevTrack selectDevTrackById(Long trackId);

    /**
     * 查询设备跟踪列表
     * 
     * @param devTrack 设备跟踪
     * @return 设备跟踪集合
     */
    public List<DevTrack> selectDevTrackList(DevTrack devTrack);

    /**
     * 新增设备跟踪
     * 
     * @param devTrack 设备跟踪
     * @return 结果
     */
    public int insertDevTrack(DevTrack devTrack);

    /**
     * 修改设备跟踪
     * 
     * @param devTrack 设备跟踪
     * @return 结果
     */
    public int updateDevTrack(DevTrack devTrack);

    /**
     * 删除设备跟踪
     * 
     * @param trackId 设备跟踪ID
     * @return 结果
     */
    public int deleteDevTrackById(Long trackId);

    /**
     * 批量删除设备跟踪
     * 
     * @param trackIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteDevTrackByIds(Long[] trackIds);
}
