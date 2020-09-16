package com.ruoyi.project.devsys.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.devsys.mapper.DevMaterialMapper;
import com.ruoyi.project.devsys.domain.DevMaterial;
import com.ruoyi.project.devsys.service.IDevMaterialService;

/**
 * 技术资料Service业务层处理
 * @author wulei
 * @date 2020-06-12
 */
@Service
public class DevMaterialServiceImpl implements IDevMaterialService
{
    @Autowired
    private DevMaterialMapper devMaterialMapper;

    /**
     * 查询技术资料
     * @param materialId 技术资料ID
     * @return 技术资料
     */
    @Override
    public DevMaterial selectDevMaterialById(Long materialId)
    {
        return devMaterialMapper.selectDevMaterialById(materialId);
    }

    /**
     * 查询技术资料列表
     * @param devMaterial 技术资料
     * @return 技术资料
     */
    @Override
    public List<DevMaterial> selectDevMaterialList(DevMaterial devMaterial)
    {
        return devMaterialMapper.selectDevMaterialList(devMaterial);
    }

    /**
     * 新增技术资料
     * @param devMaterial 技术资料
     * @return 结果
     */
    @Override
    public int insertDevMaterial(DevMaterial devMaterial)
    {
        devMaterial.setCreateTime(DateUtils.getNowDate());
        return devMaterialMapper.insertDevMaterial(devMaterial);
    }

    /**
     * 修改技术资料
     * @param devMaterial 技术资料
     * @return 结果
     */
    @Override
    public int updateDevMaterial(DevMaterial devMaterial)
    {
        devMaterial.setUpdateTime(DateUtils.getNowDate());
        return devMaterialMapper.updateDevMaterial(devMaterial);
    }

    /**
     * 批量删除技术资料
     * @param materialIds 需要删除的技术资料ID
     * @return 结果
     */
    @Override
    public int deleteDevMaterialByIds(Long[] materialIds)
    {
        return devMaterialMapper.deleteDevMaterialByIds(materialIds);
    }

    /**
     * 删除技术资料信息
     * @param materialId 技术资料ID
     * @return 结果
     */
    @Override
    public int deleteDevMaterialById(Long materialId)
    {
        return devMaterialMapper.deleteDevMaterialById(materialId);
    }
}
