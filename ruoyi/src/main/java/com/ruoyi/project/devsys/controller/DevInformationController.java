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
import com.ruoyi.project.devsys.domain.DevInformation;
import com.ruoyi.project.devsys.service.IDevInformationService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 设备信息Controller
 * 
 * @author wulei
 * @date 2020-10-30
 */
@RestController
@RequestMapping("/devsys/information")
public class DevInformationController extends BaseController
{
    @Autowired
    private IDevInformationService devInformationService;

    /**
     * 查询设备信息列表
     */
    @PreAuthorize("@ss.hasPermi('devsys:information:list')")
    @GetMapping("/list")
    public TableDataInfo list(DevInformation devInformation)
    {
        startPage();
        List<DevInformation> list = devInformationService.selectDevInformationList(devInformation);
        return getDataTable(list);
    }

    /**
     * 导出设备信息列表
     */
    @PreAuthorize("@ss.hasPermi('devsys:information:export')")
    @Log(title = "设备信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(DevInformation devInformation)
    {
        List<DevInformation> list = devInformationService.selectDevInformationList(devInformation);
        ExcelUtil<DevInformation> util = new ExcelUtil<DevInformation>(DevInformation.class);
        return util.exportExcel(list, "information");
    }

    /**
     * 获取设备信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('devsys:information:query')")
    @GetMapping(value = "/{informationId}")
    public AjaxResult getInfo(@PathVariable("informationId") Long informationId)
    {
        return AjaxResult.success(devInformationService.selectDevInformationById(informationId));
    }

    /**
     * 新增设备信息
     */
    @PreAuthorize("@ss.hasPermi('devsys:information:add')")
    @Log(title = "设备信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DevInformation devInformation)
    {
        return toAjax(devInformationService.insertDevInformation(devInformation));
    }

    /**
     * 修改设备信息
     */
    @PreAuthorize("@ss.hasPermi('devsys:information:edit')")
    @Log(title = "设备信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DevInformation devInformation)
    {
        return toAjax(devInformationService.updateDevInformation(devInformation));
    }

    /**
     * 删除设备信息
     */
    @PreAuthorize("@ss.hasPermi('devsys:information:remove')")
    @Log(title = "设备信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{informationIds}")
    public AjaxResult remove(@PathVariable Long[] informationIds)
    {
        return toAjax(devInformationService.deleteDevInformationByIds(informationIds));
    }
}
