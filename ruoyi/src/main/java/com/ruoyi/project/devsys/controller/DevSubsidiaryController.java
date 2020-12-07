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
import com.ruoyi.project.devsys.domain.DevEquip;
import com.ruoyi.project.devsys.domain.DevFile;
import com.ruoyi.project.devsys.domain.DevSubsidiary;
import com.ruoyi.project.devsys.service.IDevEquipService;
import com.ruoyi.project.devsys.service.IDevFileService;
import com.ruoyi.project.devsys.service.IDevSubsidiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * 附属设备明细Controller
 *
 * @author wulei
 * @date 2020-10-30
 */
@RestController
@RequestMapping("/devsys/subsidiary")
public class DevSubsidiaryController extends BaseController
{
    @Autowired
    private IDevSubsidiaryService devSubsidiaryService;
    @Autowired
    private IDevFileService fileService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private IDevEquipService equipService;

    /**
     * 查询附属设备明细列表
     */
    @PreAuthorize("@ss.hasPermi('devsys:subsidiary:list')")
    @GetMapping("/list")
    public TableDataInfo list(DevSubsidiary devSubsidiary)
    {
        DevEquip devEquip=new DevEquip();
        devEquip.setParentId(devSubsidiary.getEquipId());
        List<DevEquip> devEquipList = equipService.selectDevEquipList(devEquip);
        if(devEquipList.size()>0){
            startPage();
            List<DevSubsidiary>   devSubsidiaries=devSubsidiaryService.selectDevSubsidiaryListIn(devEquipList);
            return getDataTable(devSubsidiaries);
        }
        startPage();
        List<DevSubsidiary> list = devSubsidiaryService.selectDevSubsidiaryList(devSubsidiary);
        return getDataTable(list);
    }

    /**
     * 导出附属设备明细列表
     */
    @PreAuthorize("@ss.hasPermi('devsys:subsidiary:export')")
    @Log(title = "附属设备明细", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(DevSubsidiary devSubsidiary)
    {
        List<DevSubsidiary> list = devSubsidiaryService.selectDevSubsidiaryList(devSubsidiary);
        ExcelUtil<DevSubsidiary> util = new ExcelUtil<DevSubsidiary>(DevSubsidiary.class);
        return util.exportExcel(list, "subsidiary");
    }

    /**
     * 获取附属设备明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('devsys:subsidiary:query')")
    @GetMapping(value = "/{subsidiaryId}")
    public AjaxResult getInfo(@PathVariable("subsidiaryId") Long subsidiaryId)
    {
        return AjaxResult.success(devSubsidiaryService.selectDevSubsidiaryById(subsidiaryId));
    }

    /**
     * 新增附属设备明细
     */
    @PreAuthorize("@ss.hasPermi('devsys:subsidiary:add')")
    @Log(title = "附属设备明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DevSubsidiary devSubsidiary)
    {
        return toAjax(devSubsidiaryService.insertDevSubsidiary(devSubsidiary));
    }

    /**
     * 修改附属设备明细
     */
    @PreAuthorize("@ss.hasPermi('devsys:subsidiary:edit')")
    @Log(title = "附属设备明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DevSubsidiary devSubsidiary)
    {
        return toAjax(devSubsidiaryService.updateDevSubsidiary(devSubsidiary));
    }

    /**
     * 删除附属设备明细
     */
    @PreAuthorize("@ss.hasPermi('devsys:subsidiary:remove')")
    @Log(title = "附属设备明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{subsidiaryIds}")
    public AjaxResult remove(@PathVariable Long[] subsidiaryIds)
    {
        return toAjax(devSubsidiaryService.deleteDevSubsidiaryByIds(subsidiaryIds));
    }
    /**
     * 上传文件的接口
     */
    @PostMapping("/uploadFile")
    public AjaxResult uploadFile(@RequestParam Long subsidiaryId, @RequestParam MultipartFile[] files) throws IOException {
        boolean flag=devSubsidiaryService.uploadFile(subsidiaryId,files);
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
    @GetMapping("/obtainFile/{subsidiaryId}")
    public AjaxResult obtainFile(@PathVariable Long subsidiaryId){
        List<DevFile> devFile = fileService.selectsubsidiaryIdById(subsidiaryId);
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
        ExcelUtil<DevSubsidiary> util = new ExcelUtil<>(DevSubsidiary.class);
        List<DevSubsidiary> devSubsidiaries = util.importExcel(file.getInputStream());
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        String username = loginUser.getUsername();
        String message = devSubsidiaryService.importUser(devSubsidiaries,updateSupport,username,equipId);
        return AjaxResult.success(message);
    }
}
