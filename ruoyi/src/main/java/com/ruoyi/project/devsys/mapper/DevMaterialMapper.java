package com.ruoyi.project.devsys.mapper;

import com.ruoyi.project.devsys.domain.DevMaterial;

import java.util.List;

/**
 * 技术资料Mapper接口
 * @author wulei
 * @date 2020-06-12
 */
public interface DevMaterialMapper {
    /**
     * 查询技术资料
     * @param materialId 技术资料ID
     * @return 技术资料
     */
    public DevMaterial selectDevMaterialById(Long materialId);

    /**
     * 查询技术资料列表
     * @param devMaterial 技术资料
     * @return 技术资料集合
     */
    public List<DevMaterial> selectDevMaterialList(DevMaterial devMaterial);

    /**
     * 新增技术资料
     * @param devMaterial 技术资料
     * @return 结果
     */
    public int insertDevMaterial(DevMaterial devMaterial);

    /**
     * 修改技术资料
     * @param devMaterial 技术资料
     * @return 结果
     */
    public int updateDevMaterial(DevMaterial devMaterial);

    /**
     * 删除技术资料
     * @param materialId 技术资料ID
     * @return 结果
     */
    public int deleteDevMaterialById(Long materialId);

    /**
     * 批量删除技术资料
     * @param materialIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteDevMaterialByIds(Long[] materialIds);
}
