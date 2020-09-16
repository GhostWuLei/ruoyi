package com.ruoyi.project.picsys.controller;

import java.io.*;
import java.net.URLEncoder;
import java.util.List;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.constant.DiagramConstants;
import com.ruoyi.common.constant.EquipConstants;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.framework.config.RuoYiConfig;
import com.ruoyi.project.devsys.domain.DevEquip;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.project.picsys.domain.PicDiagram;
import com.ruoyi.project.picsys.service.IPicDiagramService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统图Controller
 * 
 * @author wulei
 * @date 2020-06-28
 */
@RestController
@RequestMapping("/picsys/diagram")
public class PicDiagramController extends BaseController {
    @Autowired
    private IPicDiagramService diagramService;

    /**
     * 查询系统图列表
     */
//    @PreAuthorize("@ss.hasPermi('picsys:diagram:list')")
//    @GetMapping("/list")
//    public TableDataInfo list(PicDiagram picDiagram) {
//        startPage();
//        List<PicDiagram> list = diagramService.selectPicDiagramList(picDiagram);
//        return getDataTable(list);
//    }

    /**
     * 导出系统图列表
     */
//    @PreAuthorize("@ss.hasPermi('picsys:diagram:export')")
//    @Log(title = "系统图", businessType = BusinessType.EXPORT)
//    @GetMapping("/export")
//    public AjaxResult export(PicDiagram picDiagram) {
//        List<PicDiagram> list = diagramService.selectPicDiagramList(picDiagram);
//        ExcelUtil<PicDiagram> util = new ExcelUtil<PicDiagram>(PicDiagram.class);
//        return util.exportExcel(list, "diagram");
//    }

    /**
     * 获取系统图详细信息
     */
    @PreAuthorize("@ss.hasPermi('picsys:diagram:query')")
    @GetMapping(value = "/{nodeId}")
    public AjaxResult getInfo(@PathVariable("nodeId") Long nodeId) {
        return AjaxResult.success(diagramService.selectPicDiagramById(nodeId));
    }


    // ################################################################

    /**
     * 获取系统图的左侧树
     *
     * @param diagram
     * @return
     */
    @GetMapping("/treeselect")
    public AjaxResult treeSelect(PicDiagram diagram) {
        List<PicDiagram> diagramList = diagramService.selectPicDiagramList(diagram);
        return AjaxResult.success(diagramService.buildDiagramTreeSelect(diagramList));
    }

    /**
     * 获取下拉目录树 除去所有的系统图
     */
    @GetMapping("/dropdowntree")
    public AjaxResult dropdowntree(){
        String nodeType = "1";
        List<PicDiagram> diagrams = diagramService.selectPicDiagramListByNodeType(nodeType);
        return AjaxResult.success(diagramService.buildDiagramTreeSelect(diagrams));
    }

    /**
     * 上传文件的接口函数
     *
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/uploadFile")
    public AjaxResult uploadFile(@RequestParam MultipartFile file) throws IOException {
        if(!file.isEmpty()){
            // 兼容IE
            String fname = file.getOriginalFilename(); // IE浏览器返回的是路径 chrome返回的是文件名加后缀
            int unixSep = fname.lastIndexOf("/");
            int winSep = fname.lastIndexOf("\\");
            int pos = (winSep > unixSep ? winSep : unixSep);
            if( pos != -1){
                fname = fname.substring(pos + 1);
            }
            String fpath = FileUploadUtils.upload(RuoYiConfig.getPicturePath(), file);
            AjaxResult ajax = AjaxResult.success();
            ajax.put("fpath", fpath);
            ajax.put("fname", fname);
            return ajax;
        }
        return AjaxResult.error("上传系统图异常，请联系管理员");
    }



    /**
     * 新增系统图
     */
    @PreAuthorize("@ss.hasPermi('picsys:diagram:add')")
    @Log(title = "系统图", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PicDiagram diagram) {
        if (diagramService.checkNodeNameUnique(diagram.getNodeName())) {
            return AjaxResult.error("新增系统图/目录'" + diagram.getNodeName() + "'失败，系统图/目录名称已存在");
        }
        int i = diagramService.insertPicDiagram(diagram);
        return toAjax(i);
    }

    /**
     * 修改系统图
     */
    @PreAuthorize("@ss.hasPermi('picsys:diagram:edit')")
    @Log(title = "系统图", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    public AjaxResult edit(@RequestBody PicDiagram diagram) {
        if (DiagramConstants.DIAGRAM_NOT_UNIQUE.equals(diagramService.checkDiagramNameUnique(diagram))) {
            // 名称冲突
            return AjaxResult.error("修改系统图/目录'" + diagram.getNodeName() + "'失败，系统图/目录名称已存在");
        } else if (diagram.getParentId().equals(diagram.getNodeId())) {
            // 上级目录是自己
            return AjaxResult.error("修改系统图/目录'" + diagram.getNodeName() + "'失败，上级目录不能是自己");
        }
        PicDiagram oldNode = diagramService.selectPicDiagramById(diagram.getNodeId());
        // 如果原来的节点存在子节点 则不允许修改为系统图节点
        if(diagramService.hasChildByDiagramId(oldNode.getNodeId()) && "0".equals(diagram.getNodeType())){
            return AjaxResult.error("目录节点下存在其他节点，不允许修改为系统图节点");
        }
        // 原来的nodeType为0
        if("0".equals(oldNode.getNodeType())){
            if("0".equals(diagram.getNodeType())){
                //说明修改后还是为0
                if(diagram.getFpath().equals(oldNode.getFpath())){
                    //没有修改系统图
                    return toAjax(diagramService.updatePicDiagram(diagram));
                }else{
                    //修改了系统图 这时候要删除原来的系统图
                    deleteAnnex(oldNode.getFpath());
                    return toAjax(diagramService.updatePicDiagram(diagram));
                }
            }else if("1".equals(diagram.getNodeType())){
                //说明修改后为1 这时候要删除原来的系统图
                deleteAnnex(oldNode.getFpath());
                diagram.setFname("");
                diagram.setFpath("");
                return toAjax(diagramService.updatePicDiagram(diagram));
            }else {
               return AjaxResult.error("节点类型错误！");
            }
        }else if("1".equals(oldNode.getNodeType())){ // 原来的节点就是目录
            return toAjax(diagramService.updatePicDiagram(diagram));
        }else {
            return AjaxResult.error("原有节点类型错误！");
        }
    }

     /*// 判断修改后是系统图还是目录
        if ("1".equals(diagram.getNodeType())) {
            if ("0".equals(oldDiagram.getNodeType())) {
                // 删除原来的系统图
                deleteAnnex(oldDiagram.getFpath());
            }
            // 是目录
            diagram.setFname("");
            diagram.setFpath("");
            diagram.setUpdateBy(SecurityUtils.getUsername());
            return toAjax(diagramService.updatePicDiagram(diagram));
        }else if("0".equals(diagram.getNodeType())){
            if ("0".equals(oldDiagram.getNodeType())) {
                // 删除原来的系统图
                deleteAnnex(oldDiagram.getFpath());
            }
            // 是目录
            diagram.setFname(diagram.getFname());
            diagram.setFpath(diagram.getFpath());
            diagram.setUpdateBy(SecurityUtils.getUsername());
            return toAjax(diagramService.updatePicDiagram(diagram));
        }else {
            return AjaxResult.error("节点类型错误！");
        }*/


    /**
     * 删除附件 annex: 附件
     *
     * @param fpath
     */
    private void deleteAnnex(String fpath) {
        if (StringUtils.isEmpty(fpath)) {
            throw new CustomException("附件路径为空！", 401);
        }
        int dirLastIndex = Constants.RESOURCE_PREFIX.length();
        String realPath = RuoYiConfig.getProfile() + StringUtils.substring(fpath, dirLastIndex);
        File file = new File(realPath);
        if (file.exists()) {
            if (!file.delete()) {
                throw new CustomException("删除失败", 401);
            }
        } else {
            throw new CustomException("附件不存在！", 401);
        }
    }


    /**
     * 删除系统图
     */
    @PreAuthorize("@ss.hasPermi('picsys:diagram:remove')")
    @Log(title = "系统图", businessType = BusinessType.DELETE)
    @DeleteMapping("/{nodeId}")
    public AjaxResult remove(@PathVariable Long nodeId) {
        if (diagramService.hasChildByDiagramId(nodeId)) {
            return AjaxResult.error("存在下级目录/系统图,不允许删除");
        }
        return toAjax(diagramService.deletePicDiagramById(nodeId));
    }

    /**
     * 下载系统图
     *
     * @param nodeId
     */
    @PostMapping(value = "/download")// application/octet-stream
    public AjaxResult downLoad(Long nodeId, HttpServletResponse response) {
        nodeId = 293L;
        PicDiagram diagram = diagramService.selectPicDiagramById(nodeId);
//        if (StringUtils.isNull(diagram) || "1".equals(diagram.getNodeType())) {
//            return AjaxResult.error("系统图不存在");
//        }

        String fname = diagram.getFname();
        String fpath = diagram.getFpath();
        int dirLastIndex = Constants.RESOURCE_PREFIX.length();
        String realPath = RuoYiConfig.getProfile() + StringUtils.substring(fpath, dirLastIndex);
        File file = new File(realPath);
        if (file.exists()) {
            BufferedInputStream bis = null;
            OutputStream os = null;
            try {
                response.reset();
                //处理get请求的中文乱码问题
                fname = new String(fname.getBytes("iso-8859-1"), "utf-8");
                response.setContentType("application/octet-stream");
                response.setCharacterEncoding("utf-8");
                response.setContentLength((int) file.length());
                response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fname, "utf-8"));
                byte[] buff = new byte[1024];
                os = response.getOutputStream();
                bis = new BufferedInputStream(new FileInputStream(file));
                return AjaxResult.success("你好你好你好！！！");
                /*int i = 0;
                while ((i = bis.read(buff)) != -1) {
                    os.write(buff, 0, i);
                    os.flush();
                }
                int i = bis.read(buff);
                while (i != -1) {
                    os.write(buff, 0, i);
                    i = bis.read(buff);
                }
                return AjaxResult.success("操作成功");*/
            } catch (Exception e) {
                e.printStackTrace();
                return AjaxResult.error("系统错误！");
            } finally {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return AjaxResult.error("系统错误！");
    }

    /**
     * 获取所有的系统图列表
     *
     * @return
     */
    @GetMapping("/getAllDiagrams")
    public AjaxResult getAllDiagrams(){
        List<PicDiagram> diagrams = diagramService.selectDiagrams();
        return AjaxResult.success(diagrams);
    }

    /**
     * 根据diagramName获取nodeId
     *
     * @param diagramName
     * @return
     */
    @GetMapping("/getNodeIdByDiagramName/{diagramName}")
    public AjaxResult getNodeIdByDiagramName(@PathVariable String diagramName){
        Long nodeId = diagramService.getNodeIdByNodeName(diagramName);
        System.out.println(diagramName);
        if(StringUtils.isNull(nodeId)){
            return AjaxResult.error("不存在该系统图！");
        }
        return AjaxResult.success(nodeId);
    }



    @PostMapping(value = "/download/{nodeId}")// application/octet-stream
    public void downLoad2(@PathVariable Long nodeId, HttpServletRequest request, HttpServletResponse response) throws Exception {

        PicDiagram diagram = diagramService.selectPicDiagramById(nodeId);
        String fname = diagram.getFname();
        String fpath = diagram.getFpath();
        int dirLastIndex = Constants.RESOURCE_PREFIX.length();
        String realPath = RuoYiConfig.getProfile() + StringUtils.substring(fpath, dirLastIndex);
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition",
                "attachment;fileName=" + FileUtils.setFileDownloadHeader(request, fname));
        FileUtils.writeBytes(realPath, response.getOutputStream());
    }

}

