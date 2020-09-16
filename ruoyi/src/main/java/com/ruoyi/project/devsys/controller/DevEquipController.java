package com.ruoyi.project.devsys.controller;

import java.util.List;

import com.ruoyi.common.constant.EquipConstants;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.framework.web.domain.TreeSelect;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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
import com.ruoyi.project.devsys.domain.DevEquip;
import com.ruoyi.project.devsys.service.IDevEquipService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 设备Controller
 * 
 * @author wulei
 * @date 2020-05-27
 */
@RestController
@RequestMapping("/devsys/equip")
public class DevEquipController extends BaseController
{
    @Autowired
    private IDevEquipService equipService;

    /**
     * 查询设备列表
     */
    @PreAuthorize("@ss.hasPermi('devsys:equip:list')")
    @GetMapping("/list")
    public TableDataInfo list(DevEquip devEquip)
    {
        startPage();
        List<DevEquip> list = equipService.selectDevEquipList(devEquip);
        return getDataTable(list);
    }

    /**
     * 导出设备列表
     */
    @PreAuthorize("@ss.hasPermi('devsys:equip:export')")
    @Log(title = "设备", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(DevEquip devEquip)
    {
        List<DevEquip> list = equipService.selectDevEquipList(devEquip);
        ExcelUtil<DevEquip> util = new ExcelUtil<DevEquip>(DevEquip.class);
        return util.exportExcel(list, "equip");
    }

    /**
     * 获取设备详细信息
     */
    @PreAuthorize("@ss.hasPermi('devsys:equip:query')")
    @GetMapping(value = "/{equipId}")
    public AjaxResult getInfo(@PathVariable("equipId") Long equipId)
    {
        return AjaxResult.success(equipService.selectDevEquipById(equipId));
    }




    //###################################################################################

    /**
     * 获取设备的左侧树
     * @param equip
     * @return
     */
    @GetMapping("/treeselect")
    public AjaxResult treeSelect(DevEquip equip){
        List<DevEquip> equipList = equipService.selectDevEquipList(equip);
        return AjaxResult.success(equipService.buildEquipTreeSelect(equipList));
    }

    /**
     * 新增设备
     */
    @PreAuthorize("@ss.hasPermi('devsys:equip:add')")
    @Log(title = "设备管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody DevEquip equip)
    {
        if(EquipConstants.EQUIP_NOT_UNIQUE.equals(equipService.checkEquipNameUnique(equip))){
            return AjaxResult.error("新增设备/目录'" + equip.getEquipName() + "'失败，设备/目录名称已存在");
        }
        equip.setCreateBy(SecurityUtils.getUsername());
        return toAjax(equipService.insertDevEquip(equip));
    }

    /**
     * 删除设备
     */
    @PreAuthorize("@ss.hasPermi('devsys:equip:remove')")
    @Log(title = "设备管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{equipId}")
    public AjaxResult remove(@PathVariable Long equipId)
    {
        if(equipService.hasChildByEquipId(equipId)){
            return AjaxResult.error("存在下级设备/目录,不允许删除");
        }
        return toAjax(equipService.deleteDevEquipById(equipId));
    }

    /**
     * 修改设备
     */
    @PreAuthorize("@ss.hasPermi('devsys:equip:edit')")
    @Log(title = "设备管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DevEquip equip)
    {
        System.out.println("XXXXXXXXXXXXXXXXXXXXXXXX" + equip.getStatus());
        if(EquipConstants.EQUIP_NOT_UNIQUE.equals(equipService.checkEquipNameUnique(equip))){
            //名称冲突
            return AjaxResult.error("修改设备'" + equip.getEquipName() + "'失败，设备名称已存在");
        }else if(equip.getParentId().equals(equip.getEquipId())){
            //上级设备是本身
            return AjaxResult.error("修改设备'" + equip.getEquipName() + "'失败，上级设备不能是自己");
        }
        equip.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(equipService.updateDevEquip(equip));
    }


}
