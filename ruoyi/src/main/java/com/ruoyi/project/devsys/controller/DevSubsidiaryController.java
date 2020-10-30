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
import com.ruoyi.project.devsys.domain.DevSubsidiary;
import com.ruoyi.project.devsys.service.IDevSubsidiaryService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 附属设备明细Controller
 * 
 * @author wulei
 * @date 2020-10-30
 */
@RestController
@RequestMapping("/devsys/subsidiary")
public class DevSubsidiaryController extends BaseController
{
    @Autowired
    private IDevSubsidiaryService devSubsidiaryService;

    /**
     * 查询附属设备明细列表
     */
    @PreAuthorize("@ss.hasPermi('devsys:subsidiary:list')")
    @GetMapping("/list")
    public TableDataInfo list(DevSubsidiary devSubsidiary)
    {
        startPage();
        List<DevSubsidiary> list = devSubsidiaryService.selectDevSubsidiaryList(devSubsidiary);
        return getDataTable(list);
    }

    /**
     * 导出附属设备明细列表
     */
    @PreAuthorize("@ss.hasPermi('devsys:subsidiary:export')")
    @Log(title = "附属设备明细", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(DevSubsidiary devSubsidiary)
    {
        List<DevSubsidiary> list = devSubsidiaryService.selectDevSubsidiaryList(devSubsidiary);
        ExcelUtil<DevSubsidiary> util = new ExcelUtil<DevSubsidiary>(DevSubsidiary.class);
        return util.exportExcel(list, "subsidiary");
    }

    /**
     * 获取附属设备明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('devsys:subsidiary:query')")
    @GetMapping(value = "/{subsidiaryId}")
    public AjaxResult getInfo(@PathVariable("subsidiaryId") Long subsidiaryId)
    {
        return AjaxResult.success(devSubsidiaryService.selectDevSubsidiaryById(subsidiaryId));
    }

    /**
     * 新增附属设备明细
     */
    @PreAuthorize("@ss.hasPermi('devsys:subsidiary:add')")
    @Log(title = "附属设备明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DevSubsidiary devSubsidiary)
    {
        return toAjax(devSubsidiaryService.insertDevSubsidiary(devSubsidiary));
    }

    /**
     * 修改附属设备明细
     */
    @PreAuthorize("@ss.hasPermi('devsys:subsidiary:edit')")
    @Log(title = "附属设备明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DevSubsidiary devSubsidiary)
    {
        return toAjax(devSubsidiaryService.updateDevSubsidiary(devSubsidiary));
    }

    /**
     * 删除附属设备明细
     */
    @PreAuthorize("@ss.hasPermi('devsys:subsidiary:remove')")
    @Log(title = "附属设备明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{subsidiaryIds}")
    public AjaxResult remove(@PathVariable Long[] subsidiaryIds)
    {
        return toAjax(devSubsidiaryService.deleteDevSubsidiaryByIds(subsidiaryIds));
    }
}
