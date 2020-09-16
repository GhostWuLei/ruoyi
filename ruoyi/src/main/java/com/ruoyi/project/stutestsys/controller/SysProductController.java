package com.ruoyi.project.stutestsys.controller;

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
import com.ruoyi.project.stutestsys.domain.SysProduct;
import com.ruoyi.project.stutestsys.service.ISysProductService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 产品Controller
 * 
 * @author wulei
 * @date 2020-05-27
 */
@RestController
@RequestMapping("/stutestsys/product")
public class SysProductController extends BaseController
{
    @Autowired
    private ISysProductService sysProductService;

    /**
     * 查询产品列表
     */
    @PreAuthorize("@ss.hasPermi('stutestsys:product:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysProduct sysProduct)
    {
        startPage();
        List<SysProduct> list = sysProductService.selectSysProductList(sysProduct);
        return getDataTable(list);
    }

    /**
     * 导出产品列表
     */
    @PreAuthorize("@ss.hasPermi('stutestsys:product:export')")
    @Log(title = "产品", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SysProduct sysProduct)
    {
        List<SysProduct> list = sysProductService.selectSysProductList(sysProduct);
        ExcelUtil<SysProduct> util = new ExcelUtil<SysProduct>(SysProduct.class);
        return util.exportExcel(list, "product");
    }

    /**
     * 获取产品详细信息
     */
    @PreAuthorize("@ss.hasPermi('stutestsys:product:query')")
    @GetMapping(value = "/{productId}")
    public AjaxResult getInfo(@PathVariable("productId") Long productId)
    {
        return AjaxResult.success(sysProductService.selectSysProductById(productId));
    }

    /**
     * 新增产品
     */
    @PreAuthorize("@ss.hasPermi('stutestsys:product:add')")
    @Log(title = "产品", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysProduct sysProduct)
    {
        return toAjax(sysProductService.insertSysProduct(sysProduct));
    }

    /**
     * 修改产品
     */
    @PreAuthorize("@ss.hasPermi('stutestsys:product:edit')")
    @Log(title = "产品", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysProduct sysProduct)
    {
        return toAjax(sysProductService.updateSysProduct(sysProduct));
    }

    /**
     * 删除产品
     */
    @PreAuthorize("@ss.hasPermi('stutestsys:product:remove')")
    @Log(title = "产品", businessType = BusinessType.DELETE)
	@DeleteMapping("/{productIds}")
    public AjaxResult remove(@PathVariable Long[] productIds)
    {
        return toAjax(sysProductService.deleteSysProductByIds(productIds));
    }
}
