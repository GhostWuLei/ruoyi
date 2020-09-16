package com.ruoyi.project.devsys.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.TreeTableUtils;
import com.ruoyi.framework.web.domain.TreeSelect;
import com.ruoyi.project.devsys.domain.vo.KKSSelectTreeVo;
import com.ruoyi.project.devsys.domain.vo.KksTreeVo;
import com.ruoyi.project.system.service.impl.SysUserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.devsys.mapper.DevKksMapper;
import com.ruoyi.project.devsys.domain.DevKks;
import com.ruoyi.project.devsys.service.IDevKksService;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotBlank;

/**
 * kks编码Service业务层处理
 *
 * @author wulei
 * @date 2020-05-26
 */
@Service
public class DevKksServiceImpl implements IDevKksService
{
    private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Autowired
    private DevKksMapper kksMapper;

    /**
     * 查询kks编码
     *
     * @param kksId kks编码ID
     * @return kks编码
     */
    @Override
    public DevKks selectDevKksById(Long kksId)
    {
        return kksMapper.selectDevKksById(kksId);
    }

    /**
     * 查询kks编码列表
     *
     * @param devKks kks编码
     * @return kks编码
     */
    @Override
    public List<DevKks> selectDevKksList(DevKks devKks)
    {
        return kksMapper.selectDevKksList(devKks);
    }

    /**
     * 新增kks编码
     *
     * @param kks kks编码
     * @return 结果
     */
    @Override
    @Transactional
    public int insertDevKks(DevKks kks)
    {
        kks.setCreateTime(DateUtils.getNowDate());
        kks.setCreateBy(SecurityUtils.getUsername());
        return kksMapper.insertDevKks(kks);
    }

    /**
     * 根据newKks查询kks编码
     *
     * @param newKks
     * @return
     */
    @Override
    public boolean checkNewKksUnique(String newKks) {
        int count = kksMapper.checkNewKksUnique(newKks);
        return count > 0 ? true : false;
    }

    @Override
    public boolean checkNewKksUniqueExcludeSelf(DevKks kks) {
        int count = kksMapper.checkNewKksUniqueExcludeSelf(kks.getKksId(), kks.getNewKks());
        return count > 0 ? true : false;
    }

    /**
     * 通过id来获取下一级
     *
     * @param id
     * @return
     */
    @Override
    public List<DevKks> getChildListById(Long id) {
        List<DevKks> retList = new ArrayList<>();
        DevKks kks = kksMapper.selectDevKksById(id);
        if(StringUtils.isNotNull(kks)){
            retList = kksMapper.selectByParentKks(kks.getNewKks());
        }
        for (DevKks tmpKKS : retList) {
            List<DevKks> tmpList = kksMapper.selectByParentKks(tmpKKS.getNewKks());
            if(tmpList.size()>0){
                tmpKKS.setHasChildren(true);
            }
        }
        return retList;
    }

    /**
     * 获取根节点 根节点的parentKKS为0
     *
     * @return
     */
    @Override
    public List<DevKks> getRoots() {

        List<DevKks> retList = kksMapper.getRoots();
        for (DevKks tmpKKS : retList) {
            List<DevKks> tmpList = kksMapper.selectByParentKks(tmpKKS.getNewKks());
            if(tmpList.size()>0){
                tmpKKS.setHasChildren(true);
            }
        }
        return retList;
    }

    /**
     * 获取treeselect的根节点数据
     *
     * @return
     */
    @Override
    public List<KKSSelectTreeVo> getTreeRoots() {
        List<KKSSelectTreeVo> retList = new ArrayList<>();
        List<DevKks> list = kksMapper.getRoots();
        for (DevKks tmpKKS : list) {
            List<DevKks> tmpList = kksMapper.selectByParentKks(tmpKKS.getNewKks());
            if(tmpList.size()>0){
                tmpKKS.setHasChildren(true);
            }
        }
        retList = list.stream().map(KKSSelectTreeVo::new).collect(Collectors.toList());
        System.out.println(retList);
        return retList;
    }

    /**
     * 通过父级KKS获取下一级
     *
     * @param parentKks
     * @return
     */
    @Override
    public List<KKSSelectTreeVo> getChildByParentKks(String parentKks) {
        List<KKSSelectTreeVo> retList = new ArrayList<>();
        List<DevKks> list = kksMapper.selectByParentKks(parentKks);
        for (DevKks tmpKKS : list) {
            List<DevKks> tmpList = kksMapper.selectByParentKks(tmpKKS.getNewKks());
            if(tmpList.size()>0){
                tmpKKS.setHasChildren(true);
            }
        }
        retList = list.stream().map(KKSSelectTreeVo::new).collect(Collectors.toList());
        return retList;
    }

    /**
     * 通过kks编码获取记录
     *
     * @param newKKS
     */
    @Override
    public DevKks getByNewkks(String newKKS) {
        return kksMapper.selectKksByNewKks(newKKS);
    }


//    //这种方法数据太多会导致响应太慢
//    @Override
//    public List<DevKks> getRoots() {
//        List<String> sons =  kksMapper.getNewKKSList();
//        List<String> parents = kksMapper.getParentKKSList();
//        Set<String> set = new HashSet<>();
//        List<DevKks> finalList = new ArrayList<>();
//        //判断是不是顶级节点 对父级进行遍历
//        for (String parent : parents) {
//            if(!sons.contains(parent)){
//                set.add(parent);
//            }
//        }
//        //对set集合遍历 如果父节点在set集合中 则表示该节点为根节点
//        for (String s : set) {
//            List<DevKks> childs = kksMapper.getChilds(s);
//            finalList.addAll(childs);
//        }
//        //对finalList进行遍历 判断有没有孩子
//        for (DevKks tmpKKS : finalList) {
//            List<DevKks> tmpList = kksMapper.getChilds(tmpKKS.getNewKks());
//            if(tmpList.size()>0){
//                tmpKKS.setHasChildren(true);
//            }
//        }
//        return finalList;
//    }


    /**
     * 修改kks编码
     *
     * @param kks kks编码
     * @return 结果
     */
    @Override
    @Transactional
    public int updateDevKks(DevKks kks)
    {
        //查询出原来的编码
        DevKks oldKks = kksMapper.selectDevKksById(kks.getKksId());

        // 判断有没有修改编码 如果修改了编码 则所有子节点的父节点都要修改
        if(!kks.getNewKks().equals(oldKks.getNewKks())){
           kksMapper.updateParentKks(kks.getNewKks(), oldKks.getNewKks());
        }
        kks.setUpdateTime(DateUtils.getNowDate());
        return kksMapper.updateDevKks(kks);
    }

    /**
     * 批量删除kks编码
     *
     * @param kksIds 需要删除的kks编码ID
     * @return 结果
     */
    @Override
    public int deleteDevKksByIds(Long[] kksIds)
    {
        return kksMapper.deleteDevKksByIds(kksIds);
    }

    /**
     * 删除kks编码信息
     *
     * @param kksId kks编码ID
     * @return 结果
     */
    @Override
    public int deleteDevKksById(Long kksId)
    {
        return kksMapper.deleteDevKksById(kksId);
    }

    /**
     * 导入kks编码
     *
     * @param kksList
     * @param isUpdateSupport
     * @param operName
     * @return
     */
    @Override
    @Transactional
    public String importKks(List<DevKks> kksList, boolean isUpdateSupport, String operName) {
        if(StringUtils.isNull(kksList) || kksList.size() == 0){
            throw new CustomException("导入的kks编码数据不能为空");
        }
        int insertNum = 0;
        int updateNum = 0;
        int repeatNum = 0;
        StringBuilder successMsg = new StringBuilder();
        for (DevKks kks : kksList) {
            //验证父节点是否已存在 如果不存在 则导入失败 回滚事务
            DevKks parent = kksMapper.selectKksByNewKks(kks.getParentKks());
            if(StringUtils.isNull(parent)){
                throw new CustomException("编码"+kks.getNewKks()+"的父级编码不存在，导入失败！！");
            }
            //验证是否已存在这个kks编码
            DevKks k = kksMapper.selectKksByNewKks(kks.getNewKks());

            if(StringUtils.isNull(k)){
                //该kks编码不存在 可以直接插入
                kks.setCreateBy(operName);
                this.insertDevKks(kks);
                insertNum++;
            } else{
                // 说明这个KKS编码存在
                if(isUpdateSupport){
                    //允许修改
                    kks.setKksId(k.getKksId());
                    kks.setUpdateBy(operName);
                    this.updateDevKks(kks);
                    updateNum++;
                }else{
                    //不允许修改
                    repeatNum++;
                }
            }
        }
        successMsg.insert(0, "导入数据已完成！新增"+insertNum+"条，更新"+updateNum+"条，"+repeatNum+"条数据已存在，未修改");
        return successMsg.toString();
    }

    /**
     * 查询kks 并返回树
     *
     * @param kks
     * @return
     */
    @Override
    public List<DevKks> selectKksTree(DevKks kks) {
        List<DevKks> orginalList = kksMapper.selectDevKksList(kks);
        List<DevKks> newList = TreeTableUtils.list2TreeList(orginalList, "newKks", "parentKks", "children");
        return newList;
    }

    /**
     * 下拉树
     *
     * @param kksList
     * @return
     */
    @Override
    public List<KksTreeVo> buildKksTreeSelect(List<DevKks> kksList) {
        List<DevKks> kksTrees = buildKksTree(kksList);
        List<KksTreeVo> collect = kksTrees.stream().map(KksTreeVo::new).collect(Collectors.toList());
        return collect;
    }


    /**
     * 构建el-table表格树
     *
     * @param kksList
     * @return
     */
    @Override
    public List<DevKks> buildKksTree(List<DevKks> kksList) {
        List<DevKks> returnList = new ArrayList<>();
        List<String> tempList = new ArrayList<>();
        for (DevKks kks : kksList) {
            tempList.add(kks.getNewKks());
        }
        //判断是不是顶级节点
        for(Iterator<DevKks> iterator = kksList.iterator(); iterator.hasNext();){
            DevKks tempKks = (DevKks)iterator.next();
            if(!tempList.contains(tempKks.getParentKks())){
                //如果是
                recursionFn(kksList, tempKks);
                returnList.add(tempKks);
            }
        }
        if (returnList.isEmpty()){
            returnList = kksList;
        }

        return returnList;
    }

    /**
     * 递归构建树
     *
     * @param kksList
     * @param kks
     */
    @Override
    public void recursionFn(List<DevKks> kksList, DevKks kks) {
        // 得到子节点列表
        List<DevKks> childList = getChildList(kksList, kks);
        kks.setChildren(childList);
        // 遍历子节点
        for (DevKks tChild : childList) {
            // 判断子节点是否有子节点
            if(hasChild(kksList, tChild)){
                // 说明tChild有子节点
                Iterator<DevKks> iterator = childList.iterator();
                while (iterator.hasNext()) {
                    DevKks k = (DevKks) iterator.next();
                    recursionFn(kksList, k);
                }

            }
        }
    }

    /**
     * 获取子节点列表
     *
     * @param kksList
     * @param kks
     * @return
     */
    @Override
    public List<DevKks> getChildList(List<DevKks> kksList, DevKks kks) {
        List<DevKks> childList = new ArrayList<>();
        Iterator<DevKks> iterator = kksList.iterator();
        while (iterator.hasNext()){
            DevKks n = (DevKks) iterator.next();
            if(StringUtils.isNotNull(n.getParentKks()) && n.getParentKks().equals(kks.getNewKks())){
                // 是子节点
                childList.add(n);
            }
        }
        return childList;
    }

    /**
     * 判断有没有子节点
     *
     * @param kksList
     * @param tempKks
     * @return
     */
    @Override
    public boolean hasChild(List<DevKks> kksList, DevKks tempKks) {
        return getChildList(kksList, tempKks).size() > 0 ? true : false;
    }


}
