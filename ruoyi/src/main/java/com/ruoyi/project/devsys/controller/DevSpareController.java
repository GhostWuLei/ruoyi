package com.ruoyi.project.devsys.controller;

import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.framework.config.RuoYiConfig;
import com.ruoyi.project.devsys.domain.DevFile;
import com.ruoyi.project.devsys.service.IDevFileService;
import com.ruoyi.project.picsys.domain.PicDiagram;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.project.devsys.domain.DevSpare;
import com.ruoyi.project.devsys.service.IDevSpareService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 备品备件Controller
 *
 * @author wulei
 * @date 2020-06-08
 */
@Api(value = "备品备件controller类",tags = "备品备件操作接口")
@RestController
@RequestMapping("/devsys/spare")
public class DevSpareController extends BaseController
{
    @Autowired
    private IDevSpareService devSpareService;
    @Autowired
    private IDevFileService devFileService;

    /**
     * 查询备品备件列表
     */
    @ApiOperation(value = "获取备品备件列表信息",notes = "json的数据格式")
    @PreAuthorize("@ss.hasPermi('devsys:spare:list')")
    @GetMapping("/list")
    public TableDataInfo list(DevSpare devSpare)
    {
        startPage();
        List<DevSpare> list = devSpareService.selectDevSpareList(devSpare);
        return getDataTable(list);
    }

    /**
     * 导出备品备件列表
     */
    @PreAuthorize("@ss.hasPermi('devsys:spare:export')")
    @Log(title = "备品备件", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(DevSpare devSpare)
    {
        List<DevSpare> list = devSpareService.selectDevSpareList(devSpare);
        ExcelUtil<DevSpare> util = new ExcelUtil<DevSpare>(DevSpare.class);
        return util.exportExcel(list, "spare");
    }

    /**
     * 获取备品备件详细信息
     */
    @PreAuthorize("@ss.hasPermi('devsys:spare:query')")
    @GetMapping(value = "/{spareId}")
    public AjaxResult getInfo(@PathVariable("spareId") Long spareId)
    {
        return AjaxResult.success(devSpareService.selectDevSpareById(spareId));
    }

    /**
     * 新增备品备件
     */
    @PreAuthorize("@ss.hasPermi('devsys:spare:add')")
    @Log(title = "备品备件", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DevSpare devSpare)
    {
        return toAjax(devSpareService.insertDevSpare(devSpare));
    }

    /**
     * 修改备品备件
     */
    @PreAuthorize("@ss.hasPermi('devsys:spare:edit')")
    @Log(title = "备品备件", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DevSpare devSpare)
    {
        return toAjax(devSpareService.updateDevSpare(devSpare));
    }

    /**
     * 删除备品备件 判断有没有附件，如果有附件也需要删除
     */
    @PreAuthorize("@ss.hasPermi('devsys:spare:remove')")
    @Log(title = "备品备件", businessType = BusinessType.DELETE)
	@DeleteMapping("/{spareIds}")
    public AjaxResult remove(@PathVariable Long[] spareIds)
    {
        for (Long spareId : spareIds) {
            DevSpare tmpSpare = devSpareService.selectDevSpareById(spareId);
            if(StringUtils.isNotEmpty(tmpSpare.getFpath())){
                devSpareService.deleteAnnex(tmpSpare.getFpath());
                devSpareService.deleteDevSpareById(tmpSpare.getSpareId());
            }else{
                devSpareService.deleteDevSpareById(spareId);
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
    public AjaxResult uploadFile(@RequestParam Long spareId,  @RequestParam MultipartFile[] files) throws IOException {
        System.out.println("上传id");
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                // 兼容IE
                String fname = file.getOriginalFilename(); // IE浏览器返回的是路径 chrome浏览器返回的是文件名加后缀
                int unixSep = fname.lastIndexOf("/");
                int winSep = fname.lastIndexOf("\\");
                int pos = (winSep > unixSep ? winSep : unixSep);
                if (pos != -1) {
                    fname = fname.substring(pos + 1);
                }
                List<DevFile> devFiles = devFileService.selectDevFileList(null);
                for (DevFile devFile : devFiles) {
                    if (devFile.getFname().equals(file.getOriginalFilename())) {
                        devFileService.deleteDevFileById(devFile.getSpareId());
                        devSpareService.deleteAnnex(devFile.getFpath());
                    }
                }
                String fpath = FileUploadUtils.upload(RuoYiConfig.getAccoutPath(), file);
                DevFile devFile = new DevFile();
                devFile.setSpareId(spareId);
                devFile.setFname(file.getOriginalFilename());
                devFile.setFpath(fpath);
                devFileService.insertDevFile(devFile);
            }
                //判断原来的有没有附件 如果有则删除
//                DevSpare spare = devSpareService.selectDevSpareById(spareId);
//                if (StringUtils.isNotEmpty(spare.getFpath())) {
//                    devSpareService.deleteAnnex(spare.getFpath());
//                }
                // 修改附件
//                DevSpare devSpare = new DevSpare();
//                devSpare.setSpareId(spareId);
//                devSpare.setFname(fname);
//                devSpare.setFpath(fpath);
//                devSpareService.updateDevSpare(devSpare);
                return AjaxResult.success("上传成功！");
            }
        return AjaxResult.error("上传附件异常，请联系管理员");
    }


    /**
     * 下载附件
     *
     * @param spareId
     */
    @PostMapping("/download/{spareId}")
    public String downloadFile(@PathVariable Long spareId, HttpServletResponse response) {
        DevSpare spare = devSpareService.selectDevSpareById(spareId);
        String fname = spare.getFname();
        String fpath = spare.getFpath();
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
    //获取上传文件
    @GetMapping("/obtainFile/{spareId}")
    public AjaxResult obtainFile(@PathVariable Long spareId){
        List<DevFile> devFile = devFileService.selectDevFileById(spareId);
        return AjaxResult.success(devFile);
    }
    //删除文件
    @DeleteMapping("delFile/{FileId}")
    public AjaxResult delFile(@PathVariable Long FileId){
        DevFile devFile=devFileService.selectDevFileid(FileId);
        System.out.println(devFile);
        devSpareService.deleteAnnex(devFile.getFpath());
        devFileService.deleteDevFileByFileId(FileId);
        return AjaxResult.success("删除成功");
    }



}
