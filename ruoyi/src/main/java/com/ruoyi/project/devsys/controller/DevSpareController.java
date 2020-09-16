package com.ruoyi.project.devsys.controller;

import java.io.IOException;
import java.util.List;

import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.framework.config.RuoYiConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.project.devsys.domain.DevSpare;
import com.ruoyi.project.devsys.service.IDevSpareService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 备品备件Controller
 * 
 * @author wulei
 * @date 2020-06-08
 */
@Api(value = "备品备件controller类",tags = "备品备件操作接口")
@RestController
@RequestMapping("/devsys/spare")
public class DevSpareController extends BaseController
{
    @Autowired
    private IDevSpareService devSpareService;

    /**
     * 查询备品备件列表
     */
    @ApiOperation(value = "获取备品备件列表信息",notes = "json的数据格式")
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

    /**
     * 上传文件的接口函数
     *
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/uploadFile")
    public AjaxResult uploadFile(@RequestParam MultipartFile file) throws IOException {
        if(!file.isEmpty()){
            // 兼容IE
            String fname = file.getOriginalFilename(); // IE浏览器返回的是路径 chrome浏览器返回的是文件名加后缀
            int unixSep = fname.lastIndexOf("/");
            int winSep = fname.lastIndexOf("\\");
            int pos = (winSep > unixSep ? winSep : unixSep);
            if( pos != -1){
                fname = fname.substring(pos + 1);
            }
            String fpath = FileUploadUtils.upload(RuoYiConfig.getPicturePath(), file);
            AjaxResult ajax = AjaxResult.success();
            ajax.put("fpath", fpath);
            ajax.put("fname", fname);
            return ajax;
        }
        return AjaxResult.error("上传附件异常，请联系管理员");
    }

}
