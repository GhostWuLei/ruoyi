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
import com.ruoyi.project.devsys.domain.DevFault;
import com.ruoyi.project.devsys.service.IDevFaultService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 故障记录Controller
 * 
 * @author wulei
 * @date 2020-10-30
 */
@RestController
@RequestMapping("/devsys/fault")
public class DevFaultController extends BaseController
{
    @Autowired
    private IDevFaultService devFaultService;

    /**
     * 查询故障记录列表
     */
    @PreAuthorize("@ss.hasPermi('devsys:fault:list')")
    @GetMapping("/list")
    public TableDataInfo list(DevFault devFault)
    {
        startPage();
        List<DevFault> list = devFaultService.selectDevFaultList(devFault);
        return getDataTable(list);
    }

    /**
     * 导出故障记录列表
     */
    @PreAuthorize("@ss.hasPermi('devsys:fault:export')")
    @Log(title = "故障记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(DevFault devFault)
    {
        List<DevFault> list = devFaultService.selectDevFaultList(devFault);
        ExcelUtil<DevFault> util = new ExcelUtil<DevFault>(DevFault.class);
        return util.exportExcel(list, "fault");
    }

    /**
     * 获取故障记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('devsys:fault:query')")
    @GetMapping(value = "/{faultId}")
    public AjaxResult getInfo(@PathVariable("faultId") Long faultId)
    {
        return AjaxResult.success(devFaultService.selectDevFaultById(faultId));
    }

    /**
     * 新增故障记录
     */
    @PreAuthorize("@ss.hasPermi('devsys:fault:add')")
    @Log(title = "故障记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DevFault devFault)
    {
        return toAjax(devFaultService.insertDevFault(devFault));
    }

    /**
     * 修改故障记录
     */
    @PreAuthorize("@ss.hasPermi('devsys:fault:edit')")
    @Log(title = "故障记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DevFault devFault)
    {
        return toAjax(devFaultService.updateDevFault(devFault));
    }

    /**
     * 删除故障记录
     */
    @PreAuthorize("@ss.hasPermi('devsys:fault:remove')")
    @Log(title = "故障记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{faultIds}")
    public AjaxResult remove(@PathVariable Long[] faultIds)
    {
        return toAjax(devFaultService.deleteDevFaultByIds(faultIds));
    }
}
