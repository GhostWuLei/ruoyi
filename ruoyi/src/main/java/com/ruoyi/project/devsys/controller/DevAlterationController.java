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
import com.ruoyi.project.devsys.domain.DevAlteration;
import com.ruoyi.project.devsys.domain.DevEquip;
import com.ruoyi.project.devsys.domain.DevFile;
import com.ruoyi.project.devsys.service.IDevAlterationService;
import com.ruoyi.project.devsys.service.IDevEquipService;
import com.ruoyi.project.devsys.service.IDevFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * 设备变更Controller
 *
 * @author wulei
 * @date 2020-10-30
 */
@RestController
@RequestMapping("/devsys/alteration")
public class DevAlterationController extends BaseController
{
    @Autowired
    private IDevAlterationService devAlterationService;
    @Autowired
    private IDevFileService fileService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private IDevEquipService equipService;

    /**
     * 查询设备变更列表
     */
    @PreAuthorize("@ss.hasPermi('devsys:alteration:list')")
    @GetMapping("/list")
    public TableDataInfo list(DevAlteration devAlteration)
    {
        DevEquip devEquip=new DevEquip();
        devEquip.setParentId(devAlteration.getEquipId());
        List<DevEquip> devEquips = equipService.selectDevEquipList(devEquip);
        if(devEquips.size()>0){
            startPage();
            List<DevAlteration> devEquipList=devAlterationService.selectDevAlterationListIn(devEquips);
            return getDataTable(devEquipList);
        }
        startPage();
        List<DevAlteration> list = devAlterationService.selectDevAlterationList(devAlteration);
        return getDataTable(list);
    }

    /**
     * 导出设备变更列表
     */
    @PreAuthorize("@ss.hasPermi('devsys:alteration:export')")
    @Log(title = "设备变更", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(DevAlteration devAlteration)
    {
        List<DevAlteration> list = devAlterationService.selectDevAlterationList(devAlteration);
        ExcelUtil<DevAlteration> util = new ExcelUtil<DevAlteration>(DevAlteration.class);
        return util.exportExcel(list, "alteration");
    }

    /**
     * 获取设备变更详细信息
     */
    @PreAuthorize("@ss.hasPermi('devsys:alteration:query')")
    @GetMapping(value = "/{alterationId}")
    public AjaxResult getInfo(@PathVariable("alterationId") Long alterationId)
    {
        return AjaxResult.success(devAlterationService.selectDevAlterationById(alterationId));
    }

    /**
     * 新增设备变更
     */
    @PreAuthorize("@ss.hasPermi('devsys:alteration:add')")
    @Log(title = "设备变更", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DevAlteration devAlteration)
    {
        return toAjax(devAlterationService.insertDevAlteration(devAlteration));
    }

    /**
     * 修改设备变更
     */
    @PreAuthorize("@ss.hasPermi('devsys:alteration:edit')")
    @Log(title = "设备变更", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DevAlteration devAlteration)
    {
        return toAjax(devAlterationService.updateDevAlteration(devAlteration));
    }

    /**
     * 删除设备变更
     */
    @PreAuthorize("@ss.hasPermi('devsys:alteration:remove')")
    @Log(title = "设备变更", businessType = BusinessType.DELETE)
	@DeleteMapping("/{alterationIds}")
    public AjaxResult remove(@PathVariable Long[] alterationIds) {
        return toAjax(devAlterationService.deleteDevAlterationByIds(alterationIds));
    }
    /**
     * 上传文件的接口
     */
    @PostMapping("/uploadFile")
    public AjaxResult uploadFile(@RequestParam Long alterationId, @RequestParam MultipartFile[] files) throws IOException {
        boolean flag=devAlterationService.uploadFile(alterationId,files);
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
    @GetMapping("/obtainFile/{alterationId}")
    public AjaxResult obtainFile(@PathVariable Long alterationId){
        List<DevFile> devFile = fileService.selectalterationIdById(alterationId);
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
        ExcelUtil<DevAlteration> util = new ExcelUtil<>(DevAlteration.class);
        List<DevAlteration> devAlterationList = util.importExcel(file.getInputStream());
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        String username = loginUser.getUsername();
        String message = devAlterationService.importUser(devAlterationList,updateSupport,username,equipId);
        return AjaxResult.success(message);
    }


}
