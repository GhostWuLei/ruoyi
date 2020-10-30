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
import com.ruoyi.project.devsys.domain.DevSpare;
import com.ruoyi.project.devsys.service.IDevSpareService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 备品备件Controller
 * 
 * @author wulei
 * @date 2020-10-30
 */
@RestController
@RequestMapping("/devsys/spare")
public class DevSpareController extends BaseController
{
    @Autowired
    private IDevSpareService devSpareService;

    /**
     * 查询备品备件列表
     */
    @PreAuthorize("@ss.hasPermi('devsys:spare:list')")
    @GetMapping("/list")
    public TableDataInfo list(DevSpare devSpare)
    {
        startPage();
        List<DevSpare> list = devSpareService.selectDevSpareList(devSpare);
        return getDataTable(list);
    }

    /**
     * 导出备品备件列表
     */
    @PreAuthorize("@ss.hasPermi('devsys:spare:export')")
    @Log(title = "备品备件", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(DevSpare devSpare)
    {
        List<DevSpare> list = devSpareService.selectDevSpareList(devSpare);
        ExcelUtil<DevSpare> util = new ExcelUtil<DevSpare>(DevSpare.class);
        return util.exportExcel(list, "spare");
    }

    /**
     * 获取备品备件详细信息
     */
    @PreAuthorize("@ss.hasPermi('devsys:spare:query')")
    @GetMapping(value = "/{spareId}")
    public AjaxResult getInfo(@PathVariable("spareId") Long spareId)
    {
        return AjaxResult.success(devSpareService.selectDevSpareById(spareId));
    }

    /**
     * 新增备品备件
     */
    @PreAuthorize("@ss.hasPermi('devsys:spare:add')")
    @Log(title = "备品备件", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DevSpare devSpare)
    {
        return toAjax(devSpareService.insertDevSpare(devSpare));
    }

    /**
     * 修改备品备件
     */
    @PreAuthorize("@ss.hasPermi('devsys:spare:edit')")
    @Log(title = "备品备件", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DevSpare devSpare)
    {
        return toAjax(devSpareService.updateDevSpare(devSpare));
    }

    /**
     * 删除备品备件
     */
    @PreAuthorize("@ss.hasPermi('devsys:spare:remove')")
    @Log(title = "备品备件", businessType = BusinessType.DELETE)
	@DeleteMapping("/{spareIds}")
    public AjaxResult remove(@PathVariable Long[] spareIds)
    {
        return toAjax(devSpareService.deleteDevSpareByIds(spareIds));
    }
}
