package com.ruoyi.project.devsys.controller;

import java.io.*;
import java.util.List;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.framework.config.RuoYiConfig;
import com.ruoyi.project.devsys.domain.DevEquip;
import com.ruoyi.project.devsys.domain.DevTrack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.project.devsys.domain.DevReform;
import com.ruoyi.project.devsys.service.IDevReformService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 重大技改Controller
 *
 * @author wulei
 * @date 2020-06-16
 */
@RestController
@RequestMapping("/devsys/reform")
public class DevReformController extends BaseController
{
    @Autowired
    private IDevReformService devReformService;

    /**
     * 查询重大技改列表
     */
    @PreAuthorize("@ss.hasPermi('devsys:reform:list')")
    @GetMapping("/list")
    public TableDataInfo list(DevReform devReform)
    {
        startPage();
        List<DevReform> list = devReformService.selectDevReformList(devReform);
        return getDataTable(list);
    }

    /**
     * 导出重大技改列表
     */
    @PreAuthorize("@ss.hasPermi('devsys:reform:export')")
    @Log(title = "重大技改", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(DevReform devReform)
    {
        List<DevReform> list = devReformService.selectDevReformList(devReform);
        ExcelUtil<DevReform> util = new ExcelUtil<DevReform>(DevReform.class);
        return util.exportExcel(list, "reform");
    }

    /**
     * 获取重大技改详细信息
     */
    @PreAuthorize("@ss.hasPermi('devsys:reform:query')")
    @GetMapping(value = "/{reformId}")
    public AjaxResult getInfo(@PathVariable("reformId") Long reformId)
    {
        return AjaxResult.success(devReformService.selectDevReformById(reformId));
    }

    /**
     * 新增重大技改
     */
    @PreAuthorize("@ss.hasPermi('devsys:reform:add')")
    @Log(title = "重大技改", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DevReform devReform)
    {
        return toAjax(devReformService.insertDevReform(devReform));
    }

    /**
     * 修改重大技改
     */
    @PreAuthorize("@ss.hasPermi('devsys:reform:edit')")
    @Log(title = "重大技改", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DevReform devReform)
    {
        return toAjax(devReformService.updateDevReform(devReform));
    }

    /**
     * 删除重大技改
     */
    @PreAuthorize("@ss.hasPermi('devsys:reform:remove')")
    @Log(title = "重大技改", businessType = BusinessType.DELETE)
	@DeleteMapping("/{reformIds}")
    public AjaxResult remove(@PathVariable Long[] reformIds)
    {
        for (Long reformId : reformIds) {
            DevReform devReform = devReformService.selectDevReformById(reformId);
            if(StringUtils.isNotEmpty(devReform.getFpath())){
                devReformService.deleteAnnex(devReform.getFpath());
                devReformService.deleteDevReformById(reformId);
            }else{
                devReformService.deleteDevReformById(reformId);
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
    @PreAuthorize("@ss.hasPermi('devsys:reform:uploadFile')")
    @PostMapping("/uploadFile")
    public AjaxResult uploadFile(@RequestParam Long reformId, @RequestParam MultipartFile[] files) throws IOException {
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
                String fpath = FileUploadUtils.upload(RuoYiConfig.getreformPath(), file);
                //判断原来的有没有附件 如果有则删除
                DevReform reform = devReformService.selectDevReformById(reformId);
                if(StringUtils.isNotEmpty(reform.getFpath())){
                    devReformService.deleteAnnex(reform.getFpath());
                }
                // 修改附件
                DevReform devReform = new DevReform();
                devReform.setReformId(reformId);
                devReform.setFname(fname);
                devReform.setFpath(fpath);
                devReformService.updateDevReform(devReform);
                return AjaxResult.success("上传成功！");
            }
        }
        return AjaxResult.error("上传附件异常，请联系管理员");
    }
    /**
     * 下载附件
     *
     * @param reformId
     */
    @PostMapping("/download/{materialId}")
    public String downloadFile(@PathVariable Long reformId, HttpServletResponse response) {
        Logger logger= LoggerFactory.getLogger(Logger.class);
        DevReform reform = devReformService.selectDevReformById(reformId);
        String fname = reform.getFname();
        String fpath = reform.getFpath();
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
    @GetMapping("/getEquip")
    public AjaxResult getEquip(){
        List<DevEquip> equip = devReformService.getEquip();
        return AjaxResult.success("list",equip);
    }
}
