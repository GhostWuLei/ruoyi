package com.ruoyi.project.devsys.controller;

import java.io.*;
import java.util.List;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.framework.config.RuoYiConfig;
import com.ruoyi.project.devsys.domain.DevRepair;
import com.ruoyi.project.devsys.domain.DevSpare;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.project.devsys.domain.DevNorm;
import com.ruoyi.project.devsys.service.IDevNormService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 设备规范Controller
 *
 * @author wulei
 * @date 2020-06-16
 */
@RestController
@RequestMapping("/devsys/norm")
public class DevNormController extends BaseController
{
    @Autowired
    private IDevNormService devNormService;

    /**
     * 查询设备规范列表
     */
    @PreAuthorize("@ss.hasPermi('devsys:norm:list')")
    @GetMapping("/list")
    public TableDataInfo list(DevNorm devNorm)
    {
        startPage();
        List<DevNorm> list = devNormService.selectDevNormList(devNorm);
        return getDataTable(list);
    }

    /**
     * 导出设备规范列表
     */
    @PreAuthorize("@ss.hasPermi('devsys:norm:export')")
    @Log(title = "设备规范", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(DevNorm devNorm)
    {
        List<DevNorm> list = devNormService.selectDevNormList(devNorm);
        ExcelUtil<DevNorm> util = new ExcelUtil<DevNorm>(DevNorm.class);
        return util.exportExcel(list, "norm");
    }

    /**
     * 获取设备规范详细信息
     */
    @PreAuthorize("@ss.hasPermi('devsys:norm:query')")
    @GetMapping(value = "/{normId}")
    public AjaxResult getInfo(@PathVariable("normId") Long normId)
    {
        return AjaxResult.success(devNormService.selectDevNormById(normId));
    }

    /**
     * 新增设备规范
     */
    @PreAuthorize("@ss.hasPermi('devsys:norm:add')")
    @Log(title = "设备规范", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DevNorm devNorm)
    {
        return toAjax(devNormService.insertDevNorm(devNorm));
    }

    /**
     * 修改设备规范
     */
    @PreAuthorize("@ss.hasPermi('devsys:norm:edit')")
    @Log(title = "设备规范", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DevNorm devNorm)
    {
        return toAjax(devNormService.updateDevNorm(devNorm));
    }

    /**
     * 删除设备规范
     */
    @PreAuthorize("@ss.hasPermi('devsys:norm:remove')")
    @Log(title = "设备规范", businessType = BusinessType.DELETE)
	@DeleteMapping("/{normIds}")
    public AjaxResult remove(@PathVariable Long[] normIds)
    {
        for (Long normId : normIds) {
            DevNorm tmpNorm = devNormService.selectDevNormById(normId);
            if(StringUtils.isNotEmpty(tmpNorm.getFpath())){
                devNormService.deleteAnnex(tmpNorm.getFpath());
                devNormService.deleteDevNormById(tmpNorm.getNormId());
            }else{
                devNormService.deleteDevNormById(normId);
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
    // @PreAuthorize("@ss.hasPermi('devsys:spare:uploadFile')")
    @PostMapping("/uploadFile")
    public AjaxResult uploadFile(@RequestParam Long normId, @RequestParam MultipartFile[] files) throws IOException {
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
                String fpath = FileUploadUtils.upload(RuoYiConfig.getNormPath(), file);
                //判断原来的有没有附件 如果有则删除
                DevNorm norm = devNormService.selectDevNormById(normId);
                if(StringUtils.isNotEmpty(norm.getFpath())){
                    devNormService.deleteAnnex(norm.getFpath());
                }
                // 修改附件
                DevNorm devNorm = new DevNorm();
                devNorm.setNormId(normId);
                devNorm.setFname(fname);
                devNorm.setFpath(fpath);
                devNormService.updateDevNorm(devNorm);
                return AjaxResult.success("上传成功！");
            }
        }
        return AjaxResult.error("上传附件异常，请联系管理员");
    }

    /**
     * 下载附件
     *
     * @param normId
     */
    @PostMapping("/download/{normId}")
    public void downloadFile(@PathVariable Long normId, HttpServletResponse response) {
        DevNorm norm = devNormService.selectDevNormById(normId);
        String fname = norm.getFname();
        String fpath = norm.getFpath();
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
