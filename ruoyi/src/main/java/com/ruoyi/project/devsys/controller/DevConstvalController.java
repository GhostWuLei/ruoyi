package com.ruoyi.project.devsys.controller;

import java.io.*;
import java.util.List;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.framework.config.RuoYiConfig;
import com.ruoyi.project.devsys.domain.DevMaterial;
import com.ruoyi.project.devsys.domain.DevSpare;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.project.devsys.domain.DevConstval;
import com.ruoyi.project.devsys.service.IDevConstvalService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 设备定值Controller
 *
 * @author wulei
 * @date 2020-06-15
 */
@RestController
@RequestMapping("/devsys/constval")
public class DevConstvalController extends BaseController
{
    @Autowired
    private IDevConstvalService devConstvalService;

    /**
     * 查询设备定值列表
     */
    @PreAuthorize("@ss.hasPermi('devsys:constval:list')")
    @GetMapping("/list")
    public TableDataInfo list(DevConstval devConstval)
    {
        startPage();
        List<DevConstval> list = devConstvalService.selectDevConstvalList(devConstval);
        return getDataTable(list);
    }

    /**
     * 导出设备定值列表
     */
    @PreAuthorize("@ss.hasPermi('devsys:constval:export')")
    @Log(title = "设备定值", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(DevConstval devConstval)
    {
        List<DevConstval> list = devConstvalService.selectDevConstvalList(devConstval);
        ExcelUtil<DevConstval> util = new ExcelUtil<DevConstval>(DevConstval.class);
        return util.exportExcel(list, "constval");
    }

    /**
     * 获取设备定值详细信息
     */
    @PreAuthorize("@ss.hasPermi('devsys:constval:query')")
    @GetMapping(value = "/{constvalId}")
    public AjaxResult getInfo(@PathVariable("constvalId") Long constvalId)
    {
        return AjaxResult.success(devConstvalService.selectDevConstvalById(constvalId));
    }

    /**
     * 新增设备定值
     */
    @PreAuthorize("@ss.hasPermi('devsys:constval:add')")
    @Log(title = "设备定值", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DevConstval devConstval)
    {
        return toAjax(devConstvalService.insertDevConstval(devConstval));
    }

    /**
     * 修改设备定值
     */
    @PreAuthorize("@ss.hasPermi('devsys:constval:edit')")
    @Log(title = "设备定值", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DevConstval devConstval)
    {
        return toAjax(devConstvalService.updateDevConstval(devConstval));
    }

    /**
     * 删除设备定值
     */
    @PreAuthorize("@ss.hasPermi('devsys:constval:remove')")
    @Log(title = "设备定值", businessType = BusinessType.DELETE)
	@DeleteMapping("/{constvalIds}")
    public AjaxResult remove(@PathVariable Long[] constvalIds)
    {
        return toAjax(devConstvalService.deleteDevConstvalByIds(constvalIds));
    }
    /**
     * 上传文件的接口函数
     *
     * @param
     * @return
     * @throws IOException
     */
    @PreAuthorize("@ss.hasPermi('devsys:constval:uploadFile')")
    @PostMapping("/uploadFile")
    public AjaxResult uploadFile(@RequestParam Long constvalId, @RequestParam MultipartFile[] files) throws IOException {
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
                String fpath = FileUploadUtils.upload(RuoYiConfig.getconstvalPath(), file);
                //判断原来的有没有附件 如果有则删除
                DevConstval devConstval = devConstvalService.selectDevConstvalById(constvalId);
                if(StringUtils.isNotEmpty(devConstval.getFpath())){
                    devConstvalService.deleteAnnex(devConstval.getFpath());
                }
                // 修改附件
                DevConstval constval = new DevConstval();
                constval.setConstvalId(constvalId);
                constval.setFname(fname);
                constval.setFpath(fpath);
                devConstvalService.updateDevConstval(constval);
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
    public String downloadFile(@PathVariable Long materialId, HttpServletResponse response) {
        Logger logger= LoggerFactory.getLogger(Logger.class);
        DevConstval constval = devConstvalService.selectDevConstvalById(materialId);
        String fname = constval.getFname();
        String fpath = constval.getFpath();
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
