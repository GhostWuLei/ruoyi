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
import com.ruoyi.project.devsys.domain.DevReform;
import com.ruoyi.project.devsys.service.IDevReformService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 重大技改Controller
 * 
 * @author wulei
 * @date 2020-06-16
 */
@RestController
@RequestMapping("/devsys/reform")
public class DevReformController extends BaseController
{
    @Autowired
    private IDevReformService devReformService;

    /**
     * 查询重大技改列表
     */
    @PreAuthorize("@ss.hasPermi('devsys:reform:list')")
    @GetMapping("/list")
    public TableDataInfo list(DevReform devReform)
    {
        startPage();
        List<DevReform> list = devReformService.selectDevReformList(devReform);
        return getDataTable(list);
    }

    /**
     * 导出重大技改列表
     */
    @PreAuthorize("@ss.hasPermi('devsys:reform:export')")
    @Log(title = "重大技改", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(DevReform devReform)
    {
        List<DevReform> list = devReformService.selectDevReformList(devReform);
        ExcelUtil<DevReform> util = new ExcelUtil<DevReform>(DevReform.class);
        return util.exportExcel(list, "reform");
    }

    /**
     * 获取重大技改详细信息
     */
    @PreAuthorize("@ss.hasPermi('devsys:reform:query')")
    @GetMapping(value = "/{reformId}")
    public AjaxResult getInfo(@PathVariable("reformId") Long reformId)
    {
        return AjaxResult.success(devReformService.selectDevReformById(reformId));
    }

    /**
     * 新增重大技改
     */
    @PreAuthorize("@ss.hasPermi('devsys:reform:add')")
    @Log(title = "重大技改", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DevReform devReform)
    {
        return toAjax(devReformService.insertDevReform(devReform));
    }

    /**
     * 修改重大技改
     */
    @PreAuthorize("@ss.hasPermi('devsys:reform:edit')")
    @Log(title = "重大技改", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DevReform devReform)
    {
        return toAjax(devReformService.updateDevReform(devReform));
    }

    /**
     * 删除重大技改
     */
    @PreAuthorize("@ss.hasPermi('devsys:reform:remove')")
    @Log(title = "重大技改", businessType = BusinessType.DELETE)
	@DeleteMapping("/{reformIds}")
    public AjaxResult remove(@PathVariable Long[] reformIds)
    {
        return toAjax(devReformService.deleteDevReformByIds(reformIds));
    }
}
