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
import com.ruoyi.project.devsys.domain.DevConstval;
import com.ruoyi.project.devsys.service.IDevConstvalService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 设备定值Controller
 * 
 * @author wulei
 * @date 2020-06-15
 */
@RestController
@RequestMapping("/devsys/constval")
public class DevConstvalController extends BaseController
{
    @Autowired
    private IDevConstvalService devConstvalService;

    /**
     * 查询设备定值列表
     */
    @PreAuthorize("@ss.hasPermi('devsys:constval:list')")
    @GetMapping("/list")
    public TableDataInfo list(DevConstval devConstval)
    {
        startPage();
        List<DevConstval> list = devConstvalService.selectDevConstvalList(devConstval);
        return getDataTable(list);
    }

    /**
     * 导出设备定值列表
     */
    @PreAuthorize("@ss.hasPermi('devsys:constval:export')")
    @Log(title = "设备定值", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(DevConstval devConstval)
    {
        List<DevConstval> list = devConstvalService.selectDevConstvalList(devConstval);
        ExcelUtil<DevConstval> util = new ExcelUtil<DevConstval>(DevConstval.class);
        return util.exportExcel(list, "constval");
    }

    /**
     * 获取设备定值详细信息
     */
    @PreAuthorize("@ss.hasPermi('devsys:constval:query')")
    @GetMapping(value = "/{constvalId}")
    public AjaxResult getInfo(@PathVariable("constvalId") Long constvalId)
    {
        return AjaxResult.success(devConstvalService.selectDevConstvalById(constvalId));
    }

    /**
     * 新增设备定值
     */
    @PreAuthorize("@ss.hasPermi('devsys:constval:add')")
    @Log(title = "设备定值", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DevConstval devConstval)
    {
        return toAjax(devConstvalService.insertDevConstval(devConstval));
    }

    /**
     * 修改设备定值
     */
    @PreAuthorize("@ss.hasPermi('devsys:constval:edit')")
    @Log(title = "设备定值", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DevConstval devConstval)
    {
        return toAjax(devConstvalService.updateDevConstval(devConstval));
    }

    /**
     * 删除设备定值
     */
    @PreAuthorize("@ss.hasPermi('devsys:constval:remove')")
    @Log(title = "设备定值", businessType = BusinessType.DELETE)
	@DeleteMapping("/{constvalIds}")
    public AjaxResult remove(@PathVariable Long[] constvalIds)
    {
        return toAjax(devConstvalService.deleteDevConstvalByIds(constvalIds));
    }
}
