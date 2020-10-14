package com.ruoyi.project.devsys.controller;

import java.io.*;
import java.util.List;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.framework.config.RuoYiConfig;
import com.ruoyi.project.devsys.domain.DevSpare;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.project.devsys.domain.DevRepair;
import com.ruoyi.project.devsys.service.IDevRepairService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 检修记录Controller
 * @author wulei
 * @date 2020-06-15
 */
@RestController
@RequestMapping("/devsys/repair")
public class DevRepairController extends BaseController
{
    @Autowired
    private IDevRepairService devRepairService;

    /**
     * 查询检修记录列表
     */
    @PreAuthorize("@ss.hasPermi('devsys:repair:list')")
    @GetMapping("/list")
    public TableDataInfo list(DevRepair devRepair)
    {
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
    public AjaxResult add(@RequestBody DevRepair devRepair)
    {
        return toAjax(devRepairService.insertDevRepair(devRepair));
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
        for (Long repairId : repairIds) {
            DevRepair tmpRepair = devRepairService.selectDevRepairById(repairId);
            if(StringUtils.isNotEmpty(tmpRepair.getFpath())){
                devRepairService.deleteAnnex(tmpRepair.getFpath());
                devRepairService.deleteDevRepairById(tmpRepair.getRepairId());
            }else{
                devRepairService.deleteDevRepairById(repairId);
            }
        }
        return AjaxResult.success("删除成功");
    }

    @PostMapping("/uploadFile")
    public AjaxResult uploadFile(@RequestParam Long repairId, @RequestParam MultipartFile[] files) throws IOException {
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
                String fpath = FileUploadUtils.upload(RuoYiConfig.getRepairPath(), file);
                //判断原来的有没有附件 如果有则删除
                DevRepair repair = devRepairService.selectDevRepairById(repairId);
                if(StringUtils.isNotEmpty(repair.getFpath())){
                    devRepairService.deleteAnnex(repair.getFpath());
                }
                // 修改附件
                DevRepair devRepair = new DevRepair();
                devRepair.setRepairId(repairId);
                devRepair.setFname(fname);
                devRepair.setFpath(fpath);
                devRepairService.updateDevRepair(devRepair);
                return AjaxResult.success("上传成功！");
            }
        }
        return AjaxResult.error("上传附件异常，请联系管理员");
    }

    /**
     * 下载附件
     *
     * @param repairId
     */
    @PostMapping("/download/{repairId}")
    public void downloadFile(@PathVariable Long repairId, HttpServletResponse response) {
        DevRepair repair = devRepairService.selectDevRepairById(repairId);
        String fname = repair.getFname();
        String fpath = repair.getFpath();
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
