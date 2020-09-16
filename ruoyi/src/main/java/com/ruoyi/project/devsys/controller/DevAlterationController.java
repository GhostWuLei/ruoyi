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
import com.ruoyi.project.devsys.domain.DevAlteration;
import com.ruoyi.project.devsys.service.IDevAlterationService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 异动变更Controller
 * 
 * @author wulei
 * @date 2020-06-16
 */
@RestController
@RequestMapping("/devsys/alteration")
public class DevAlterationController extends BaseController
{
    @Autowired
    private IDevAlterationService devAlterationService;

    /**
     * 查询异动变更列表
     */
    @PreAuthorize("@ss.hasPermi('devsys:alteration:list')")
    @GetMapping("/list")
    public TableDataInfo list(DevAlteration devAlteration)
    {
        startPage();
        List<DevAlteration> list = devAlterationService.selectDevAlterationList(devAlteration);
        return getDataTable(list);
    }

    /**
     * 导出异动变更列表
     */
    @PreAuthorize("@ss.hasPermi('devsys:alteration:export')")
    @Log(title = "异动变更", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(DevAlteration devAlteration)
    {
        List<DevAlteration> list = devAlterationService.selectDevAlterationList(devAlteration);
        ExcelUtil<DevAlteration> util = new ExcelUtil<DevAlteration>(DevAlteration.class);
        return util.exportExcel(list, "alteration");
    }

    /**
     * 获取异动变更详细信息
     */
    @PreAuthorize("@ss.hasPermi('devsys:alteration:query')")
    @GetMapping(value = "/{alterationId}")
    public AjaxResult getInfo(@PathVariable("alterationId") Long alterationId)
    {
        return AjaxResult.success(devAlterationService.selectDevAlterationById(alterationId));
    }

    /**
     * 新增异动变更
     */
    @PreAuthorize("@ss.hasPermi('devsys:alteration:add')")
    @Log(title = "异动变更", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DevAlteration devAlteration)
    {
        return toAjax(devAlterationService.insertDevAlteration(devAlteration));
    }

    /**
     * 修改异动变更
     */
    @PreAuthorize("@ss.hasPermi('devsys:alteration:edit')")
    @Log(title = "异动变更", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DevAlteration devAlteration)
    {
        return toAjax(devAlterationService.updateDevAlteration(devAlteration));
    }

    /**
     * 删除异动变更
     */
    @PreAuthorize("@ss.hasPermi('devsys:alteration:remove')")
    @Log(title = "异动变更", businessType = BusinessType.DELETE)
	@DeleteMapping("/{alterationIds}")
    public AjaxResult remove(@PathVariable Long[] alterationIds)
    {
        return toAjax(devAlterationService.deleteDevAlterationByIds(alterationIds));
    }
}
