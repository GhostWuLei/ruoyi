package com.ruoyi.project.devsys.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.framework.security.LoginUser;
import com.ruoyi.framework.security.service.TokenService;
import com.ruoyi.framework.web.domain.TreeSelect;
import com.ruoyi.project.devsys.domain.vo.KKSSelectTreeVo;
import com.ruoyi.project.system.domain.SysDictData;
import com.ruoyi.project.system.domain.SysUser;
import com.ruoyi.project.system.service.ISysDictDataService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.project.devsys.domain.DevKks;
import com.ruoyi.project.devsys.service.IDevKksService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * kks编码Controller
 * 
 * @author wulei
 * @date 2020-05-26
 */
@RestController
@RequestMapping("/devsys/kks")
public class DevKksController extends BaseController
{
    @Autowired
    private IDevKksService kksService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ISysDictDataService dictDataService;

    /**
     * 查询kks编码列表
     */
    @PreAuthorize("@ss.hasPermi('devsys:kks:list')")
    @GetMapping("/list")
    public AjaxResult list(DevKks devKks)
    {
        List<DevKks> kksList = kksService.selectDevKksList(devKks);
        return AjaxResult.success(kksList);
    }

    /**
     * 获取KKS树
     * @param kks
     * @return
     */
    @GetMapping("/kksTree")
    public AjaxResult kksTree(DevKks kks)
    {
        List<DevKks> kksList = kksService.selectDevKksList(kks);
        List<SysDictData> DictData = dictDataService.selectDictDataByType("sys_ele_major");
        for (DevKks tempKks : kksList) {
            for (SysDictData dictDatum : DictData) {
                if(dictDatum.getDictValue().equals(tempKks.getMajor())){
                    tempKks.setMajor(dictDatum.getDictLabel());
                    continue;
                }
            }
        }
        List<DevKks> kksTree = kksService.buildKksTree(kksList);
        AjaxResult ajaxResult = AjaxResult.success();
        ajaxResult.put("kksTree", kksTree);
        return ajaxResult;
    }

    /**
     * 导出kks编码列表
     */
    @PreAuthorize("@ss.hasPermi('devsys:kks:export')")
    @Log(title = "kks编码", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(DevKks devKks)
    {
        List<DevKks> list = kksService.selectDevKksList(devKks);
        ExcelUtil<DevKks> util = new ExcelUtil<DevKks>(DevKks.class);
        return util.exportExcel(list, "kks");
    }

    /**
     * 获取kks编码详细信息
     */
    @PreAuthorize("@ss.hasPermi('devsys:kks:query')")
    @GetMapping(value = "/{kksId}")
    public AjaxResult getInfo(@PathVariable("kksId") Long kksId)
    {
        return AjaxResult.success(kksService.selectDevKksById(kksId));
    }

    /**
     * 获取kks编码详细信息以及父编码信息
     */
    @PreAuthorize("@ss.hasPermi('devsys:kks:query')")
    @GetMapping(value = "getUpdateInfo/{kksId}")
    public AjaxResult getUpdateInfo(@PathVariable("kksId") Long kksId)
    {
        DevKks kks1 = kksService.selectDevKksById(kksId);
        DevKks kks2 = kksService.getByNewkks(kks1.getParentKks());
        AjaxResult ajaxResult = AjaxResult.success();
        ajaxResult.put("parent",kks2);
        ajaxResult.put("data",kks1);
        return ajaxResult;
    }

    /**
     * 新增kks编码
     */
    @PreAuthorize("@ss.hasPermi('devsys:kks:add')")
    @Log(title = "kks编码", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DevKks kks)
    {
        // 判断KKS编码唯一性
        if(kksService.checkNewKksUnique(kks.getNewKks())){
            return AjaxResult.error("操作失败，" + kks.getNewKks() + "编码已存在！");
        }
        // 判断子编码和父编码是否一样
        if(kks.getNewKks().equals(kks.getParentKks())){
            return AjaxResult.error("操作失败，父级编码不能是自己！");
        }
        return toAjax(kksService.insertDevKks(kks));
    }

    /**
     * 修改kks编码
     */
    @PreAuthorize("@ss.hasPermi('devsys:kks:edit')")
    @Log(title = "kks编码", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DevKks kks)
    {
        // 判断修改后KKS编码的唯一性
        if(kksService.checkNewKksUniqueExcludeSelf(kks)){
            return AjaxResult.error("操作失败，" + kks.getNewKks() + "编码已存在！");
        }
        // 判断父编码是否是自己
        if(kks.getNewKks().equals(kks.getParentKks())){
            return AjaxResult.error("操作失败，新编码和父编码冲突！");
        }
        return toAjax(kksService.updateDevKks(kks));
    }

    /**
     * 删除kks编码
     */
    @PreAuthorize("@ss.hasPermi('devsys:kks:remove')")
    @Log(title = "kks编码", businessType = BusinessType.DELETE)
	@DeleteMapping("/{kksIds}")
    public AjaxResult remove(@PathVariable Long[] kksIds)
    {
        return toAjax(kksService.deleteDevKksByIds(kksIds));
    }

    /**
     * 获取导入的模板excel
     * @return
     */
    @GetMapping("/importTemplate")
    public AjaxResult importTemplate()
    {
        ExcelUtil<DevKks> util = new ExcelUtil<DevKks>(DevKks.class);
        return util.importTemplateExcel("kks编码数据");
    }

    /**
     * 导入kks编码
     * @param file
     * @param updateSupport
     * @return
     * @throws Exception
     */
    @Log(title = "设备管控", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('devsys:kks:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<DevKks> util = new ExcelUtil<DevKks>(DevKks.class);
        List<DevKks> kksList = util.importExcel(file.getInputStream());
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        String operName = loginUser.getUsername();
        String message = kksService.importKks(kksList, updateSupport, operName);
        return AjaxResult.success(message);
    }


    @GetMapping("/treeselect") // 暂时没有用到这个方法
    public AjaxResult treeSelect(DevKks kks){
        List<DevKks> kksList = kksService.selectDevKksList(kks);
        return AjaxResult.success(kksService.buildKksTreeSelect(kksList));
    }

    /**
     * 获取根节点并返回 用于表格树
     * @return
     */
    @GetMapping("/getRoots")
    public AjaxResult getRoots(){
        List<DevKks> roots = kksService.getRoots();
        return AjaxResult.success(roots);
    }

    /**
     * 用于树形结构的懒加载 通过id 查询下一级KKS编码 用于表格树
     * @param id
     * @return
     */
    @GetMapping("/getChildrenById/{id}")
    public AjaxResult getChildrenById(@PathVariable Long id){
        List<DevKks> kksList = kksService.getChildListById(id);
        return AjaxResult.success(kksList);
    }


    /**
     * 用于新增时获取treeselect的根节点数据
     * @return
     */
    @GetMapping("/getTreeRoots")
    public AjaxResult getTreeRoots(){
        List<KKSSelectTreeVo> roots = kksService.getTreeRoots();
        return AjaxResult.success(roots);
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/getTreeByParentKks/{parentKks}")
    public AjaxResult getTreeByParentKks(@PathVariable String parentKks){
        List<KKSSelectTreeVo> kksList = kksService.getChildByParentKks(parentKks);
        return AjaxResult.success(kksList);
    }




}
