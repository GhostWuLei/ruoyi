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
import com.ruoyi.project.devsys.domain.DevRepair;
import com.ruoyi.project.devsys.service.IDevRepairService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 检修记录Controller
 * @author wulei
 * @date 2020-06-15
 */
@RestController
@RequestMapping("/devsys/repair")
public class DevRepairController extends BaseController
{
    @Autowired
    private IDevRepairService devRepairService;

    /**
     * 查询检修记录列表
     */
    @PreAuthorize("@ss.hasPermi('devsys:repair:list')")
    @GetMapping("/list")
    public TableDataInfo list(DevRepair devRepair)
    {
        startPage();
        List<DevRepair> list = devRepairService.selectDevRepairList(devRepair);
        return getDataTable(list);
    }

    /**
     * 导出检修记录列表
     */
    @PreAuthorize("@ss.hasPermi('devsys:repair:export')")
    @Log(title = "检修记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(DevRepair devRepair)
    {
        List<DevRepair> list = devRepairService.selectDevRepairList(devRepair);
        ExcelUtil<DevRepair> util = new ExcelUtil<DevRepair>(DevRepair.class);
        return util.exportExcel(list, "repair");
    }

    /**
     * 获取检修记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('devsys:repair:query')")
    @GetMapping(value = "/{repairId}")
    public AjaxResult getInfo(@PathVariable("repairId") Long repairId)
    {
        return AjaxResult.success(devRepairService.selectDevRepairById(repairId));
    }

    /**
     * 新增检修记录
     */
    @PreAuthorize("@ss.hasPermi('devsys:repair:add')")
    @Log(title = "检修记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DevRepair devRepair)
    {
        return toAjax(devRepairService.insertDevRepair(devRepair));
    }

    /**
     * 修改检修记录
     */
    @PreAuthorize("@ss.hasPermi('devsys:repair:edit')")
    @Log(title = "检修记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DevRepair devRepair)
    {
        return toAjax(devRepairService.updateDevRepair(devRepair));
    }

    /**
     * 删除检修记录
     */
    @PreAuthorize("@ss.hasPermi('devsys:repair:remove')")
    @Log(title = "检修记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{repairIds}")
    public AjaxResult remove(@PathVariable Long[] repairIds)
    {
        return toAjax(devRepairService.deleteDevRepairByIds(repairIds));
    }
}
