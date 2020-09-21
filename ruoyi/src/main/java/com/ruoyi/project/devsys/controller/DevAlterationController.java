package com.ruoyi.project.devsys.controller;

import java.io.*;
import java.util.List;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.framework.config.RuoYiConfig;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.project.devsys.domain.DevAlteration;
import com.ruoyi.project.devsys.service.IDevAlterationService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 异动变更Controller
 * 
 * @author wulei
 * @date 2020-06-16
 */
@RestController
@RequestMapping("/devsys/alteration")
public class DevAlterationController extends BaseController
{
    @Autowired
    private IDevAlterationService devAlterationService;

    /**
     * 查询异动变更列表
     */
    @PreAuthorize("@ss.hasPermi('devsys:alteration:list')")
    @GetMapping("/list")
    public TableDataInfo list(DevAlteration devAlteration)
    {
        startPage();
        List<DevAlteration> list = devAlterationService.selectDevAlterationList(devAlteration);
        return getDataTable(list);
    }

    /**
     * 导出异动变更列表
     */
    @PreAuthorize("@ss.hasPermi('devsys:alteration:export')")
    @Log(title = "异动变更", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(DevAlteration devAlteration)
    {
        List<DevAlteration> list = devAlterationService.selectDevAlterationList(devAlteration);
        ExcelUtil<DevAlteration> util = new ExcelUtil<DevAlteration>(DevAlteration.class);
        return util.exportExcel(list, "alteration");
    }

    /**
     * 获取异动变更详细信息
     */
    @PreAuthorize("@ss.hasPermi('devsys:alteration:query')")
    @GetMapping(value = "/{alterationId}")
    public AjaxResult getInfo(@PathVariable("alterationId") Long alterationId)
    {
        return AjaxResult.success(devAlterationService.selectDevAlterationById(alterationId));
    }

    /**
     * 新增异动变更
     */
    @PreAuthorize("@ss.hasPermi('devsys:alteration:add')")
    @Log(title = "异动变更", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DevAlteration devAlteration)
    {
        return toAjax(devAlterationService.insertDevAlteration(devAlteration));
    }

    /**
     * 修改异动变更
     */
    @PreAuthorize("@ss.hasPermi('devsys:alteration:edit')")
    @Log(title = "异动变更", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DevAlteration devAlteration)
    {
        return toAjax(devAlterationService.updateDevAlteration(devAlteration));
    }

    /**
     * 删除异动变更
     */
    @PreAuthorize("@ss.hasPermi('devsys:alteration:remove')")
    @Log(title = "异动变更", businessType = BusinessType.DELETE)
	@DeleteMapping("/{alterationIds}")
    public AjaxResult remove(@PathVariable Long[] alterationIds)
    {
        for (Long alterationId : alterationIds) {
            DevAlteration tmpAlteration = devAlterationService.selectDevAlterationById(alterationId);
            if(StringUtils.isNotEmpty(tmpAlteration.getFpath())){
                devAlterationService.deleteAnnex(tmpAlteration.getFpath());
                devAlterationService.deleteDevAlterationById(tmpAlteration.getAlterationId());
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
    // @PreAuthorize("@ss.hasPermi('devsys:alteration:uploadFile')")
    @PostMapping("/uploadFile")
    public AjaxResult uploadFile(@RequestParam Long alterationId, @RequestParam MultipartFile[] files) throws IOException {
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
                String fpath = FileUploadUtils.upload(RuoYiConfig.getAccoutPath(), file);
                //判断原来的有没有附件 如果有则删除
                DevAlteration alteration = devAlterationService.selectDevAlterationById(alterationId);
                if(StringUtils.isNotEmpty(alteration.getFpath())){
                    devAlterationService.deleteAnnex(alteration.getFpath());
                }
                // 修改附件
                DevAlteration devAlteration = new DevAlteration();
                devAlteration.setAlterationId(alterationId);
                devAlteration.setFname(fname);
                devAlteration.setFpath(fpath);
                devAlterationService.updateDevAlteration(devAlteration);
                return AjaxResult.success("上传成功！");
            }
        }
        return AjaxResult.error("上传附件异常，请联系管理员");
    }


    /**
     * 下载附件
     *
     * @param alterationId
     */
    @PostMapping("/download/{alterationId}")
    public String downloadFile(@PathVariable Long alterationId, HttpServletResponse response) {
        DevAlteration alteration = devAlterationService.selectDevAlterationById(alterationId);
        String fname = alteration.getFname();
        String fpath = alteration.getFpath();
        int dirLastIndex = Constants.RESOURCE_PREFIX.length();
        String realPath = RuoYiConfig.getProfile() + StringUtils.substring(fpath, dirLastIndex);
        if (fname != null) {
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
        return null;
    }
}
