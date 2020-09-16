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
import com.ruoyi.project.devsys.domain.DevMaterial;
import com.ruoyi.project.devsys.service.IDevMaterialService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 技术资料Controller
 * @author wulei
 * @date 2020-06-12
 */
@RestController
@RequestMapping("/devsys/material")
public class DevMaterialController extends BaseController
{
    @Autowired
    private IDevMaterialService devMaterialService;

    /**
     * 查询技术资料列表
     */
    @PreAuthorize("@ss.hasPermi('devsys:material:list')")
    @GetMapping("/list")
    public TableDataInfo list(DevMaterial devMaterial)
    {
        startPage();
        List<DevMaterial> list = devMaterialService.selectDevMaterialList(devMaterial);
        return getDataTable(list);
    }

    /**
     * 导出技术资料列表
     */
    @PreAuthorize("@ss.hasPermi('devsys:material:export')")
    @Log(title = "技术资料", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(DevMaterial devMaterial)
    {
        List<DevMaterial> list = devMaterialService.selectDevMaterialList(devMaterial);
        ExcelUtil<DevMaterial> util = new ExcelUtil<DevMaterial>(DevMaterial.class);
        return util.exportExcel(list, "material");
    }

    /**
     * 获取技术资料详细信息
     */
    @PreAuthorize("@ss.hasPermi('devsys:material:query')")
    @GetMapping(value = "/{materialId}")
    public AjaxResult getInfo(@PathVariable("materialId") Long materialId)
    {
        return AjaxResult.success(devMaterialService.selectDevMaterialById(materialId));
    }

    /**
     * 新增技术资料
     */
    @PreAuthorize("@ss.hasPermi('devsys:material:add')")
    @Log(title = "技术资料", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DevMaterial devMaterial)
    {
        return toAjax(devMaterialService.insertDevMaterial(devMaterial));
    }

    /**
     * 修改技术资料
     */
    @PreAuthorize("@ss.hasPermi('devsys:material:edit')")
    @Log(title = "技术资料", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DevMaterial devMaterial)
    {
        return toAjax(devMaterialService.updateDevMaterial(devMaterial));
    }

    /**
     * 删除技术资料
     */
    @PreAuthorize("@ss.hasPermi('devsys:material:remove')")
    @Log(title = "技术资料", businessType = BusinessType.DELETE)
	@DeleteMapping("/{materialIds}")
    public AjaxResult remove(@PathVariable Long[] materialIds)
    {
        return toAjax(devMaterialService.deleteDevMaterialByIds(materialIds));
    }
}
