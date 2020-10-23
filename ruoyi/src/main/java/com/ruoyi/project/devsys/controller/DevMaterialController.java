package com.ruoyi.project.devsys.controller;

import java.io.*;
import java.util.List;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.framework.config.RuoYiConfig;
import com.ruoyi.project.devsys.domain.DevSpare;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.project.devsys.domain.DevMaterial;
import com.ruoyi.project.devsys.service.IDevMaterialService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

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
        for (Long materialId : materialIds) {
            DevMaterial devMaterial = devMaterialService.selectDevMaterialById(materialId);
            System.out.println();
            if(StringUtils.isNotEmpty(devMaterial.getFpath())){
                devMaterialService.deleteAnnex(devMaterial.getFpath());
                devMaterialService.deleteDevMaterialById(materialId);
            }else{
                devMaterialService.deleteDevMaterialById(materialId);
            }
        }
        return AjaxResult.success("删除成功");
    }


    /**
     * 上传文件的接口函数
     *
     * @param
     * @return
     * @throws IOException
     */
     @PreAuthorize("@ss.hasPermi('devsys:spare:uploadFile')")
    @PostMapping("/uploadFile")
    public AjaxResult uploadFile(@RequestParam Long materialId, @RequestParam MultipartFile[] files) throws IOException {
        for (MultipartFile file : files) {
            if(!file.isEmpty()){
                // 兼容IE
                String fname = file.getOriginalFilename(); // IE浏览器返回的是路径 chrome浏览器返回的是文件名加后缀
                int unixSep = fname.lastIndexOf("/");
                int winSep = fname.lastIndexOf("\\");
                int pos = (winSep > unixSep ? winSep : unixSep);
                if( pos != -1){
                    fname = fname.substring(pos + 1);
                }
                String fpath = FileUploadUtils.upload(RuoYiConfig.getmaterialPath(), file);
                //判断原来的有没有附件 如果有则删除
//                DevSpare spare = devSpareService.selectDevSpareById(spareId);
                DevMaterial Material = devMaterialService.selectDevMaterialById(materialId);
                if(StringUtils.isNotEmpty(Material.getFpath())){
                    devMaterialService.deleteAnnex(Material.getFpath());
                }
                // 修改附件
                DevMaterial devMaterial = new DevMaterial();
                devMaterial.setMaterialId(materialId);
                devMaterial.setFname(fname);
                devMaterial.setFpath(fpath);
                devMaterialService.updateDevMaterial(devMaterial);
                return AjaxResult.success("上传成功！");
            }
        }
        return AjaxResult.error("上传附件异常，请联系管理员");
    }
    /**
     * 下载附件
     *
     * @param materialId
     */
    @PostMapping("/download/{materialId}")
    public void downloadFile(@PathVariable Long materialId, HttpServletResponse response) {
        DevMaterial material = devMaterialService.selectDevMaterialById(materialId);
        String fname = material.getFname();
        String fpath = material.getFpath();
        int dirLastIndex = Constants.RESOURCE_PREFIX.length();
        String realPath = RuoYiConfig.getProfile() + StringUtils.substring(fpath, dirLastIndex);
        if (StringUtils.isNotEmpty(fname)) {
            //设置文件路径
            File file = new File(realPath);
            if (file.exists()) {
                response.setContentType("application/octet-stream");//
                response.setHeader("content-type", "application/octet-stream");
                response.setHeader("Content-Disposition", "attachment;fileName=" + fname);// 设置文件名
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    System.out.println("success");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
