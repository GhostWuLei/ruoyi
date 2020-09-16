package com.ruoyi.project.devsys.service;

import java.util.List;
import java.util.Set;

import com.ruoyi.framework.web.domain.TreeSelect;
import com.ruoyi.project.devsys.domain.DevKks;
import com.ruoyi.project.devsys.domain.vo.KKSSelectTreeVo;
import com.ruoyi.project.devsys.domain.vo.KksTreeVo;

/**
 * kks编码Service接口
 * 
 * @author wulei
 * @date 2020-05-26
 */
public interface IDevKksService 
{
    /**
     * 查询kks编码
     * 
     * @param kksId kks编码ID
     * @return kks编码
     */
    public DevKks selectDevKksById(Long kksId);

    /**
     * 查询kks编码列表
     * 
     * @param devKks kks编码
     * @return kks编码集合
     */
    public List<DevKks> selectDevKksList(DevKks devKks);

    /**
     * 新增kks编码
     * 
     * @param devKks kks编码
     * @return 结果
     */
    public int insertDevKks(DevKks devKks);

    /**
     * 修改kks编码
     * 
     * @param devKks kks编码
     * @return 结果
     */
    public int updateDevKks(DevKks devKks);

    /**
     * 批量删除kks编码
     * 
     * @param kksIds 需要删除的kks编码ID
     * @return 结果
     */
    public int deleteDevKksByIds(Long[] kksIds);

    /**
     * 删除kks编码信息
     * 
     * @param kksId kks编码ID
     * @return 结果
     */
    public int deleteDevKksById(Long kksId);

    /**
     * 导入kks编码
     *
     * @param kksList
     * @param updateSupport
     * @param operName
     * @return
     */
    String importKks(List<DevKks> kksList, boolean updateSupport, String operName);

    /**
     * 查询kks 并返回树
     *
     * @param kks
     * @return
     */
    List<DevKks> selectKksTree(DevKks kks);

    /**
     * 构建el-table表格树
     *
     * @param list
     * @return
     */
    List<DevKks> buildKksTree(List<DevKks> list);

    /**
     * 递归构建树
     *
     * @param kksList
     * @param kks
     */
    void recursionFn(List<DevKks> kksList, DevKks kks);

    /**
     * 获取子节点列表
     *
     * @param kksList
     * @param kks
     * @return
     */
    List<DevKks> getChildList(List<DevKks> kksList, DevKks kks);

    /**
     * 判断有没有子节点
     *
     * @param kksList
     * @param t
     * @return
     */
    boolean hasChild(List<DevKks> kksList, DevKks t);

    /**
     * 下拉树
     *
     * @param kksList
     * @return
     */
    List<KksTreeVo> buildKksTreeSelect(List<DevKks> kksList);

    /**
     * 查询是否存在此KKS编码
     *
     * @param newKks
     * @return
     */
    boolean checkNewKksUnique(String newKks);

    /**
     * 判断是否存在此KKS编码 除自己
     *
     * @param kks
     * @return
     */
    boolean checkNewKksUniqueExcludeSelf(DevKks kks);

    /**
     * 通过id来获取下一级
     * @param id
     * @return
     */
    List<DevKks> getChildListById(Long id);

    /**
     * 获取根节点
     * @return
     */
    List<DevKks> getRoots();

    /**
     * 获取treeselect的根节点数据
     * @return
     */
    List<KKSSelectTreeVo> getTreeRoots();

    /**
     * 通过父级KKS获取下一级
     * @param parentKks
     * @return
     */
    List<KKSSelectTreeVo> getChildByParentKks(String parentKks);

    /**
     * 通过kks编码获取记录
     * @param newKks
     */
    DevKks getByNewkks(String newKks);

}
