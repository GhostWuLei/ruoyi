package com.ruoyi.project.devsys.controller;

import java.io.*;
import java.util.List;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.framework.config.RuoYiConfig;
import com.ruoyi.project.devsys.domain.DevConstval;
import com.ruoyi.project.devsys.domain.DevMaterial;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.project.devsys.domain.DevTrack;
import com.ruoyi.project.devsys.service.IDevTrackService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 设备跟踪Controller
 *
 * @author wulei
 * @date 2020-06-16
 */
@RestController
@RequestMapping("/devsys/track")
public class DevTrackController extends BaseController
{
    @Autowired
    private IDevTrackService devTrackService;

    /**
     * 查询设备跟踪列表
     */
    @PreAuthorize("@ss.hasPermi('devsys:track:list')")
    @GetMapping("/list")
    public TableDataInfo list(DevTrack devTrack)
    {
        startPage();
        List<DevTrack> list = devTrackService.selectDevTrackList(devTrack);
        return getDataTable(list);
    }

    /**
     * 导出设备跟踪列表
     */
    @PreAuthorize("@ss.hasPermi('devsys:track:export')")
    @Log(title = "设备跟踪", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(DevTrack devTrack)
    {
        List<DevTrack> list = devTrackService.selectDevTrackList(devTrack);
        ExcelUtil<DevTrack> util = new ExcelUtil<DevTrack>(DevTrack.class);
        return util.exportExcel(list, "track");
    }

    /**
     * 获取设备跟踪详细信息
     */
    @PreAuthorize("@ss.hasPermi('devsys:track:query')")
    @GetMapping(value = "/{trackId}")
    public AjaxResult getInfo(@PathVariable("trackId") Long trackId)
    {
        return AjaxResult.success(devTrackService.selectDevTrackById(trackId));
    }

    /**
     * 新增设备跟踪
     */
    @PreAuthorize("@ss.hasPermi('devsys:track:add')")
    @Log(title = "设备跟踪", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DevTrack devTrack)
    {
        return toAjax(devTrackService.insertDevTrack(devTrack));
    }

    /**
     * 修改设备跟踪
     */
    @PreAuthorize("@ss.hasPermi('devsys:track:edit')")
    @Log(title = "设备跟踪", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DevTrack devTrack)
    {
        return toAjax(devTrackService.updateDevTrack(devTrack));
    }

    /**
     * 删除设备跟踪
     */
    @PreAuthorize("@ss.hasPermi('devsys:track:remove')")
    @Log(title = "设备跟踪", businessType = BusinessType.DELETE)
	@DeleteMapping("/{trackIds}")
    public AjaxResult remove(@PathVariable Long[] trackIds)
    {
        for (Long trackId : trackIds) {
            DevTrack devTrack = devTrackService.selectDevTrackById(trackId);
            if(StringUtils.isNotEmpty(devTrack.getFpath())){
                devTrackService.deleteAnnex(devTrack.getFpath());
                devTrackService.deleteDevTrackById(trackId);
            }else{
                devTrackService.deleteDevTrackById(trackId);
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
    @PreAuthorize("@ss.hasPermi('devsys:track:uploadFile')")
    @PostMapping("/uploadFile")
    public AjaxResult uploadFile(@RequestParam Long trackId, @RequestParam MultipartFile[] files) throws IOException {
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
                String fpath = FileUploadUtils.upload(RuoYiConfig.gettrackPath(), file);
                //判断原来的有没有附件 如果有则删除
//                DevSpare spare = devSpareService.selectDevSpareById(spareId);
                DevTrack track = devTrackService.selectDevTrackById(trackId);
                if(StringUtils.isNotEmpty(track.getFpath())){
                    devTrackService.deleteAnnex(track.getFpath());
                }
                // 修改附件
                DevTrack devMaterial = new DevTrack();
                devMaterial.setTrackId(trackId);
                devMaterial.setFname(fname);
                devMaterial.setFpath(fpath);
                devTrackService.updateDevTrack(devMaterial);
                return AjaxResult.success("上传成功！");
            }
        }
        return AjaxResult.error("上传附件异常，请联系管理员");
    }
    /**
     * 下载附件
     *
     * @param trackId
     */
    @PostMapping("/download/{trackId}")
    public void downloadFile(@PathVariable Long trackId, HttpServletResponse response) {
        DevTrack devTrack = devTrackService.selectDevTrackById(trackId);
        String fname = devTrack.getFname();
        String fpath = devTrack.getFpath();
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
