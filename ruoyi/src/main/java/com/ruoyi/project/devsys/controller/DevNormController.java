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
import com.ruoyi.project.devsys.domain.DevNorm;
import com.ruoyi.project.devsys.service.IDevNormService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 设备规范Controller
 * 
 * @author wulei
 * @date 2020-06-16
 */
@RestController
@RequestMapping("/devsys/norm")
public class DevNormController extends BaseController
{
    @Autowired
    private IDevNormService devNormService;

    /**
     * 查询设备规范列表
     */
    @PreAuthorize("@ss.hasPermi('devsys:norm:list')")
    @GetMapping("/list")
    public TableDataInfo list(DevNorm devNorm)
    {
        startPage();
        List<DevNorm> list = devNormService.selectDevNormList(devNorm);
        return getDataTable(list);
    }

    /**
     * 导出设备规范列表
     */
    @PreAuthorize("@ss.hasPermi('devsys:norm:export')")
    @Log(title = "设备规范", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(DevNorm devNorm)
    {
        List<DevNorm> list = devNormService.selectDevNormList(devNorm);
        ExcelUtil<DevNorm> util = new ExcelUtil<DevNorm>(DevNorm.class);
        return util.exportExcel(list, "norm");
    }

    /**
     * 获取设备规范详细信息
     */
    @PreAuthorize("@ss.hasPermi('devsys:norm:query')")
    @GetMapping(value = "/{normId}")
    public AjaxResult getInfo(@PathVariable("normId") Long normId)
    {
        return AjaxResult.success(devNormService.selectDevNormById(normId));
    }

    /**
     * 新增设备规范
     */
    @PreAuthorize("@ss.hasPermi('devsys:norm:add')")
    @Log(title = "设备规范", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DevNorm devNorm)
    {
        return toAjax(devNormService.insertDevNorm(devNorm));
    }

    /**
     * 修改设备规范
     */
    @PreAuthorize("@ss.hasPermi('devsys:norm:edit')")
    @Log(title = "设备规范", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DevNorm devNorm)
    {
        return toAjax(devNormService.updateDevNorm(devNorm));
    }

    /**
     * 删除设备规范
     */
    @PreAuthorize("@ss.hasPermi('devsys:norm:remove')")
    @Log(title = "设备规范", businessType = BusinessType.DELETE)
	@DeleteMapping("/{normIds}")
    public AjaxResult remove(@PathVariable Long[] normIds)
    {
        return toAjax(devNormService.deleteDevNormByIds(normIds));
    }
}
