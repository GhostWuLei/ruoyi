package com.ruoyi.project.devsys.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.devsys.mapper.DevTrackMapper;
import com.ruoyi.project.devsys.domain.DevTrack;
import com.ruoyi.project.devsys.service.IDevTrackService;

/**
 * 设备跟踪Service业务层处理
 * 
 * @author wulei
 * @date 2020-06-16
 */
@Service
public class DevTrackServiceImpl implements IDevTrackService 
{
    @Autowired
    private DevTrackMapper devTrackMapper;

    /**
     * 查询设备跟踪
     * 
     * @param trackId 设备跟踪ID
     * @return 设备跟踪
     */
    @Override
    public DevTrack selectDevTrackById(Long trackId)
    {
        return devTrackMapper.selectDevTrackById(trackId);
    }

    /**
     * 查询设备跟踪列表
     * 
     * @param devTrack 设备跟踪
     * @return 设备跟踪
     */
    @Override
    public List<DevTrack> selectDevTrackList(DevTrack devTrack)
    {
        return devTrackMapper.selectDevTrackList(devTrack);
    }

    /**
     * 批量删除设备跟踪
     * 
     * @param trackIds 需要删除的设备跟踪ID
     * @return 结果
     */
    @Override
    public int deleteDevTrackByIds(Long[] trackIds)
    {
        return devTrackMapper.deleteDevTrackByIds(trackIds);
    }

    /**
     * 删除设备跟踪信息
     * 
     * @param trackId 设备跟踪ID
     * @return 结果
     */
    @Override
    public int deleteDevTrackById(Long trackId)
    {
        return devTrackMapper.deleteDevTrackById(trackId);
    }

    //################################################################
    /**
     * 新增设备跟踪
     *
     * @param track 设备跟踪
     * @return 结果
     */
    @Override
    public int insertDevTrack(DevTrack track)
    {
        track.setCreateTime(DateUtils.getNowDate());
        track.setCreateBy(SecurityUtils.getUsername());
        return devTrackMapper.insertDevTrack(track);
    }

    /**
     * 修改设备跟踪
     *
     * @param track 设备跟踪
     * @return 结果
     */
    @Override
    public int updateDevTrack(DevTrack track)
    {
        track.setUpdateTime(DateUtils.getNowDate());
        track.setUpdateBy(SecurityUtils.getUsername());
        return devTrackMapper.updateDevTrack(track);
    }
}
