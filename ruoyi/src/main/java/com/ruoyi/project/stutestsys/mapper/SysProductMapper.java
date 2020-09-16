package com.ruoyi.project.stutestsys.mapper;

import java.util.List;
import com.ruoyi.project.stutestsys.domain.SysProduct;

/**
 * 产品Mapper接口
 * 
 * @author wulei
 * @date 2020-05-27
 */
public interface SysProductMapper 
{
    /**
     * 查询产品
     * 
     * @param productId 产品ID
     * @return 产品
     */
    public SysProduct selectSysProductById(Long productId);

    /**
     * 查询产品列表
     * 
     * @param sysProduct 产品
     * @return 产品集合
     */
    public List<SysProduct> selectSysProductList(SysProduct sysProduct);

    /**
     * 新增产品
     * 
     * @param sysProduct 产品
     * @return 结果
     */
    public int insertSysProduct(SysProduct sysProduct);

    /**
     * 修改产品
     * 
     * @param sysProduct 产品
     * @return 结果
     */
    public int updateSysProduct(SysProduct sysProduct);

    /**
     * 删除产品
     * 
     * @param productId 产品ID
     * @return 结果
     */
    public int deleteSysProductById(Long productId);

    /**
     * 批量删除产品
     * 
     * @param productIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysProductByIds(Long[] productIds);
}
