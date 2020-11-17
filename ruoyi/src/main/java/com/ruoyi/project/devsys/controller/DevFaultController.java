package com.ruoyi.project.devsys.controller;

import java.io.*;
import java.util.List;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.config.RuoYiConfig;
import com.ruoyi.framework.security.LoginUser;
import com.ruoyi.framework.security.service.TokenService;
import com.ruoyi.project.devsys.domain.DevFile;
import com.ruoyi.project.devsys.domain.DevSpare;
import com.ruoyi.project.devsys.service.IDevFileService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.project.devsys.domain.DevFault;
import com.ruoyi.project.devsys.service.IDevFaultService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 故障记录Controller
 *
 * @author wulei
 * @date 2020-10-30
 */
@RestController
@RequestMapping("/devsys/fault")
public class DevFaultController extends BaseController
{
    @Autowired
    private IDevFaultService devFaultService;
    @Autowired
    private IDevFileService fileService;
    @Autowired
    private TokenService tokenService;

    /**
     * 查询故障记录列表
     */
    @PreAuthorize("@ss.hasPermi('devsys:fault:list')")
    @GetMapping("/list")
    public TableDataInfo list(DevFault devFault)
    {
        startPage();
        List<DevFault> list = devFaultService.selectDevFaultList(devFault);
        return getDataTable(list);
    }

    /**
     * 导出故障记录列表
     */
    @PreAuthorize("@ss.hasPermi('devsys:fault:export')")
    @Log(title = "故障记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(DevFault devFault)
    {
        List<DevFault> list = devFaultService.selectDevFaultList(devFault);
        ExcelUtil<DevFault> util = new ExcelUtil<DevFault>(DevFault.class);
        return util.exportExcel(list, "fault");
    }

    /**
     * 获取故障记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('devsys:fault:query')")
    @GetMapping(value = "/{faultId}")
    public AjaxResult getInfo(@PathVariable("faultId") Long faultId)
    {
        return AjaxResult.success(devFaultService.selectDevFaultById(faultId));
    }

    /**
     * 新增故障记录
     */
    @PreAuthorize("@ss.hasPermi('devsys:fault:add')")
    @Log(title = "故障记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DevFault devFault)
    {
        return toAjax(devFaultService.insertDevFault(devFault));
    }

    /**
     * 修改故障记录
     */
    @PreAuthorize("@ss.hasPermi('devsys:fault:edit')")
    @Log(title = "故障记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DevFault devFault)
    {
        return toAjax(devFaultService.updateDevFault(devFault));
    }

    /**
     * 删除故障记录
     */
    @PreAuthorize("@ss.hasPermi('devsys:fault:remove')")
    @Log(title = "故障记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{faultIds}")
    public AjaxResult remove(@PathVariable Long[] faultIds)
    {
        return toAjax(devFaultService.deleteDevFaultByIds(faultIds));
    }
    /**
     * 上传文件的接口
     */
    @PostMapping("/uploadFile")
    public AjaxResult uploadFile(@RequestParam Long faultId, @RequestParam MultipartFile[] files) throws IOException {
        boolean flag=devFaultService.uploadFile(faultId,files);
        if(flag){
            return AjaxResult.success("上传成功");
        }else{
            return AjaxResult.error("上传失败");
        }
    }
    @GetMapping("/download/{FileId}")
    @ResponseBody
    public String downloadFile(@PathVariable Long FileId, HttpServletResponse response) {
        DevFile devFile = fileService.selectDevFileid(FileId);
        String fname = devFile.getFname();
        System.out.println(fname);
        String fpath = devFile.getFpath();
        int dirLastIndex = Constants.RESOURCE_PREFIX.length();
        String realPath = RuoYiConfig.getProfile() + StringUtils.substring(fpath, dirLastIndex);
        System.out.println(realPath);
        if (StringUtils.isNotEmpty(fname)) {
            //设置文件路径
            File file = new File(realPath);
            if (file.exists()) {
                response.setContentType("application/octet-stream");
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
        return "";
    }
    //获取上传文件
    @GetMapping("/obtainFile/{faultId}")
    public AjaxResult obtainFile(@PathVariable Long faultId){
        List<DevFile> devFile = fileService.selectfaultById(faultId);
        return AjaxResult.success(devFile);
    }
    //删除文件
    @DeleteMapping("delFile/{FileId}")
    public AjaxResult delFile(@PathVariable Long FileId){
        DevFile devFile=fileService.selectDevFileid(FileId);
        fileService.deleteAnne(devFile.getFpath());
        fileService.deleteDevFileByFileId(FileId);
        return AjaxResult.success("删除成功");
    }
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport,Long equipId) throws Exception{
        ExcelUtil<DevFault> util = new ExcelUtil<>(DevFault.class);
        List<DevFault> devFaultList = util.importExcel(file.getInputStream());
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        String username = loginUser.getUsername();
        String message = devFaultService.importUser(devFaultList,updateSupport,username,equipId);
        return AjaxResult.success(message);
    }
}
