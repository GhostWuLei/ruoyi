package com.ruoyi.project.devsys.controller;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.config.RuoYiConfig;
import com.ruoyi.framework.security.LoginUser;
import com.ruoyi.framework.security.service.TokenService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.devsys.domain.DevFile;
import com.ruoyi.project.devsys.domain.DevInformation;
import com.ruoyi.project.devsys.service.IDevEquipService;
import com.ruoyi.project.devsys.service.IDevFileService;
import com.ruoyi.project.devsys.service.IDevInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

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
    @Autowired
    private IDevFileService fileService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private IDevEquipService equipService;
    /**
     * 查询设备信息列表
     */
    @PreAuthorize("@ss.hasPermi('devsys:information:list')")
    @GetMapping("/list")
    public TableDataInfo list(DevInformation devInformation)
    {
        startPage();
        List<DevInformation> devInformations = devInformationService.selectDevInformationList(devInformation);
        return getDataTable(devInformations);
    }

    /**
     * 导出设备信息列表
     */
    @PreAuthorize("@ss.hasPermi('devsys:information:export')")
    @Log(title = "设备信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(DevInformation devInformation)
    {
        List<DevInformation> list = devInformationService.selectDevInformationListAll(devInformation);
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
    /**
     * 上传文件的接口
     */
    @PostMapping("/uploadFile")
    public AjaxResult uploadFile(@RequestParam Long informationId, @RequestParam MultipartFile[] files) throws IOException {
       boolean flag=devInformationService.uploadFile(informationId,files);
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
    /**
     * 查询全部文件接口
     */
    //获取上传文件
    @GetMapping("/obtainFile/{informationId}")
    public AjaxResult obtainFile(@PathVariable Long informationId){
        List<DevFile> devFile = fileService.selectinformationIdById(informationId);
        return AjaxResult.success(devFile);
    }
    //删除文件
    @DeleteMapping("delFile/{FileId}")
    public AjaxResult delFile(@PathVariable Long FileId){
        DevFile devFile=fileService.selectDevFileid(FileId);
        devInformationService.deleteAnnex(devFile.getFpath());
        fileService.deleteDevFileByFileId(FileId);
        return AjaxResult.success("删除成功");
    }
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport,Long equipId) throws Exception{
        ExcelUtil<DevInformation> util = new ExcelUtil<>(DevInformation.class);
        List<DevInformation> devInformationList = util.importExcel(file.getInputStream());
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        String username = loginUser.getUsername();
        String message = devInformationService.importUser(devInformationList,updateSupport,username,equipId);
        return AjaxResult.success(message);
    }
}
