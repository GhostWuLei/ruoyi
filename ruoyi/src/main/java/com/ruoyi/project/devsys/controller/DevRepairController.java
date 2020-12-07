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
import com.ruoyi.project.devsys.domain.DevRepair;
import com.ruoyi.project.devsys.service.IDevEquipService;
import com.ruoyi.project.devsys.service.IDevFileService;
import com.ruoyi.project.devsys.service.IDevRepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * 检修记录Controller
 *
 * @author wulei
 * @date 2020-10-26
 */
@RestController
@RequestMapping("/devsys/repair")
public class DevRepairController extends BaseController
{
    @Autowired
    private IDevRepairService devRepairService;

    @Autowired
    private IDevFileService fileService;

    @Autowired
    private TokenService tokenService;
    @Autowired
    private IDevEquipService equipService;

    /**
     * 查询检修记录列表
     */
    @PreAuthorize("@ss.hasPermi('devsys:repair:list')")
    @GetMapping("/list")
    public TableDataInfo list(DevRepair devRepair)
    {
        DevEquip devEquip=new DevEquip();
        devEquip.setParentId(devRepair.getEquipId());
        List<DevEquip> devEquips = equipService.selectDevEquipList(devEquip);
            if(devEquips.size()>0){
                startPage();
                List<DevRepair> devRepairs=devRepairService.selectDevRepairListIn(devEquips);
                return getDataTable(devRepairs);
            }
        startPage();
        List<DevRepair> list = devRepairService.selectDevRepairList(devRepair);
        return getDataTable(list);
    }

    /**
     * 导出检修记录列表
     */
    @PreAuthorize("@ss.hasPermi('devsys:repair:export')")
    @Log(title = "检修记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(DevRepair devRepair)
    {
        List<DevRepair> list = devRepairService.selectDevRepairList(devRepair);
        ExcelUtil<DevRepair> util = new ExcelUtil<DevRepair>(DevRepair.class);
        return util.exportExcel(list, "repair");
    }

    /**
     * 获取检修记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('devsys:repair:query')")
    @GetMapping(value = "/{repairId}")
    public AjaxResult getInfo(@PathVariable("repairId") Long repairId)
    {
        return AjaxResult.success(devRepairService.selectDevRepairById(repairId));
    }

    /**
     * 新增检修记录
     */
    @PreAuthorize("@ss.hasPermi('devsys:repair:add')")
    @Log(title = "检修记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DevRepair repair)
    {
        return toAjax(devRepairService.insertDevRepair(repair));
    }

    /**
     * 修改检修记录
     */
    @PreAuthorize("@ss.hasPermi('devsys:repair:edit')")
    @Log(title = "检修记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DevRepair devRepair)
    {
        return toAjax(devRepairService.updateDevRepair(devRepair));
    }

    /**
     * 删除检修记录
     */
    @PreAuthorize("@ss.hasPermi('devsys:repair:remove')")
    @Log(title = "检修记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{repairIds}")
    public AjaxResult remove(@PathVariable Long[] repairIds)
    {
        return toAjax(devRepairService.deleteDevRepairByIds(repairIds));
    }
    @PostMapping("/uploadFile")
    public AjaxResult uploadFile(@RequestParam Long repairId, @RequestParam MultipartFile[] files) throws IOException {
        boolean flag=devRepairService.uploadFile(repairId,files);
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
     * 查询文件接口
     */
    //获取上传文件
    @GetMapping("/obtainFile/{repairId}")
    public AjaxResult obtainFile(@PathVariable Long repairId){
        List<DevFile> devFile = fileService.selectrepairFileById(repairId);
        return AjaxResult.success(devFile);
    }
    //删除文件
    @DeleteMapping("delFile/{FileId}")
    public AjaxResult delFile(@PathVariable Long FileId){
        DevFile devFile=fileService.selectDevFileid(FileId);
        devRepairService.deleteAnne(devFile.getFpath());
        fileService.deleteDevFileByFileId(FileId);
        return AjaxResult.success("删除成功");
    }
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport,Long equipId) throws Exception{
        ExcelUtil<DevRepair> util = new ExcelUtil<>(DevRepair.class);
        List<DevRepair> devRepairList = util.importExcel(file.getInputStream());
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        String username = loginUser.getUsername();
        String message = devRepairService.importUser(devRepairList,updateSupport,username,equipId);
        return AjaxResult.success(message);
    }
}
