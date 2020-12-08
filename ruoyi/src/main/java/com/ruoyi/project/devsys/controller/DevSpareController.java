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
import com.ruoyi.project.devsys.domain.DevSpare;
import com.ruoyi.project.devsys.service.IDevEquipService;
import com.ruoyi.project.devsys.service.IDevFileService;
import com.ruoyi.project.devsys.service.IDevSpareService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * 备品备件Controller
 *
 * @author wulei
 * @date 2020-10-30
 */
@RestController
@RequestMapping("/devsys/spare")
@Api(value="备品备件controller",tags={"备品备件"})
public class DevSpareController extends BaseController
{
    @Autowired
    private IDevSpareService devSpareService;
    @Autowired
    private IDevFileService fileService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private IDevEquipService equipService;
    /**
     * 查询备品备件列表
     */
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
     * 删除备品备件
     */
    @PreAuthorize("@ss.hasPermi('devsys:spare:remove')")
    @Log(title = "备品备件", businessType = BusinessType.DELETE)
	@DeleteMapping("/{spareIds}")
    public AjaxResult remove(@PathVariable Long[] spareIds)
    {
        return toAjax(devSpareService.deleteDevSpareByIds(spareIds));
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
     * 上传文件的接口函数
     */
//    @PreAuthorize("@ss.hasPermi('devsys:spare:uploadFile')")
    @PostMapping("/uploadFile")
    public AjaxResult uploadFile(@RequestParam Long spareId, @RequestParam MultipartFile[] files){
        boolean flag=devSpareService.uploadFile(spareId,files);
        if(flag){
            return  AjaxResult.success("上传成功");
        }
        return  AjaxResult.success("上传失败");
    }
    /**
     * 查询文件接口
     */
    //获取上传文件
    @GetMapping("/obtainFile/{spareId}")

    public AjaxResult obtainFile(@PathVariable Long spareId){
        List<DevFile> devFile = fileService.selectDevFileById(spareId);
        return AjaxResult.success(devFile);
    }
    //删除文件
    @DeleteMapping("delFile/{FileId}")
    public AjaxResult delFile(@PathVariable Long FileId){
        DevFile devFile=fileService.selectDevFileid(FileId);
        devSpareService.deleteAnnexFile(devFile.getFpath());
        fileService.deleteDevFileByFileId(FileId);
        return AjaxResult.success("删除成功");
    }
    @PostMapping("/importData")
    public AjaxResult importData(@RequestParam MultipartFile file, boolean updateSupport,@RequestParam Long equipId) throws Exception{
        ExcelUtil<DevSpare> util = new ExcelUtil<>(DevSpare.class);
        List<DevSpare> spareList = util.importExcel(file.getInputStream());
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        String username = loginUser.getUsername();
        String message = devSpareService.importUser(spareList,updateSupport,username,equipId);
        return AjaxResult.success(message);
    }
}
