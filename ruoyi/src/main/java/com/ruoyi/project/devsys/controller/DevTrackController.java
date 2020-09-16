package com.ruoyi.project.devsys.controller;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.project.devsys.domain.DevTrack;
import com.ruoyi.project.devsys.service.IDevTrackService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 设备跟踪Controller
 * 
 * @author wulei
 * @date 2020-06-16
 */
@RestController
@RequestMapping("/devsys/track")
public class DevTrackController extends BaseController
{
    @Autowired
    private IDevTrackService devTrackService;

    /**
     * 查询设备跟踪列表
     */
    @PreAuthorize("@ss.hasPermi('devsys:track:list')")
    @GetMapping("/list")
    public TableDataInfo list(DevTrack devTrack)
    {
        startPage();
        List<DevTrack> list = devTrackService.selectDevTrackList(devTrack);
        return getDataTable(list);
    }

    /**
     * 导出设备跟踪列表
     */
    @PreAuthorize("@ss.hasPermi('devsys:track:export')")
    @Log(title = "设备跟踪", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(DevTrack devTrack)
    {
        List<DevTrack> list = devTrackService.selectDevTrackList(devTrack);
        ExcelUtil<DevTrack> util = new ExcelUtil<DevTrack>(DevTrack.class);
        return util.exportExcel(list, "track");
    }

    /**
     * 获取设备跟踪详细信息
     */
    @PreAuthorize("@ss.hasPermi('devsys:track:query')")
    @GetMapping(value = "/{trackId}")
    public AjaxResult getInfo(@PathVariable("trackId") Long trackId)
    {
        return AjaxResult.success(devTrackService.selectDevTrackById(trackId));
    }

    /**
     * 新增设备跟踪
     */
    @PreAuthorize("@ss.hasPermi('devsys:track:add')")
    @Log(title = "设备跟踪", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DevTrack devTrack)
    {
        return toAjax(devTrackService.insertDevTrack(devTrack));
    }

    /**
     * 修改设备跟踪
     */
    @PreAuthorize("@ss.hasPermi('devsys:track:edit')")
    @Log(title = "设备跟踪", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DevTrack devTrack)
    {
        return toAjax(devTrackService.updateDevTrack(devTrack));
    }

    /**
     * 删除设备跟踪
     */
    @PreAuthorize("@ss.hasPermi('devsys:track:remove')")
    @Log(title = "设备跟踪", businessType = BusinessType.DELETE)
	@DeleteMapping("/{trackIds}")
    public AjaxResult remove(@PathVariable Long[] trackIds)
    {
        return toAjax(devTrackService.deleteDevTrackByIds(trackIds));
    }
}
