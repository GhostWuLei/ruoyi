package com.ruoyi.project.devsys.mapper;

import java.util.List;
import com.ruoyi.project.devsys.domain.DevKks;
import org.apache.ibatis.annotations.Param;

/**
 * kks编码Mapper接口
 * 
 * @author wulei
 * @date 2020-05-26
 */
public interface DevKksMapper 
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
     * 删除kks编码
     * 
     * @param kksId kks编码ID
     * @return 结果
     */
    public int deleteDevKksById(Long kksId);

    /**
     * 批量删除kks编码
     * 
     * @param kksIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteDevKksByIds(Long[] kksIds);

    //#########################################################

    /**
     * 根据newKks查询kks记录
     *
     * @param newKks
     * @return
     */
    DevKks selectKksByNewKks(String newKks);

    /**
     * 根据newKks查询记录数
     *
     * @param newKks
     * @return
     */
    int checkNewKksUnique(String newKks);

    /**
     * 根据newKks查询记录数 不包括自己
     *
     * @param kksId
     * @param newKks
     * @return
     */
    int checkNewKksUniqueExcludeSelf(@Param("kksId") Long kksId, @Param("newKks") String newKks);

    /**
     * 删除了对应的系统图 则修改kks对应的系统图为空
     *
     * @param diagramName
     */
    void updateKksByDiagramName(String diagramName);

    /**
     * 根据修改后的newKks 修改子节点的父节点
     *
     * @param newKks
     * @param oldKks
     */
    void updateParentKks(@Param("newKks") String newKks,@Param("oldKks") String oldKks);

    /**
     * 通过父级KKS编码获取下级编码数据
     * @param parentKks
     * @return
     */
    List<DevKks> selectByParentKks(String parentKks);

    /**
     * 获取根节点数据
     * @return
     */
    List<DevKks> getRoots();

    /**
     * 获取所有的新编码
     * @return
     */
    List<String> getNewKKSList();

    /**
     * 获取所有的父编码
     * @return
     */
    List<String> getParentKKSList();

}
