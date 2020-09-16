package com.ruoyi.project.stutestsys.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 产品对象 sys_product
 * 
 * @author wulei
 * @date 2020-05-27
 */
public class SysProduct extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 产品id */
    private Long productId;

    /** 父产品id */
    @Excel(name = "父产品id")
    private Long parentId;

    /** 产品名称 */
    @Excel(name = "产品名称")
    private String productName;

    /** 显示顺序 */
    @Excel(name = "显示顺序")
    private Integer orderNum;

    /** 产品状态（0正常 1停用） */
    @Excel(name = "产品状态", readConverterExp = "0=正常,1=停用")
    private String status;

    public void setProductId(Long productId) 
    {
        this.productId = productId;
    }

    public Long getProductId() 
    {
        return productId;
    }
    public void setParentId(Long parentId) 
    {
        this.parentId = parentId;
    }

    public Long getParentId() 
    {
        return parentId;
    }
    public void setProductName(String productName) 
    {
        this.productName = productName;
    }

    public String getProductName() 
    {
        return productName;
    }
    public void setOrderNum(Integer orderNum) 
    {
        this.orderNum = orderNum;
    }

    public Integer getOrderNum() 
    {
        return orderNum;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("productId", getProductId())
            .append("parentId", getParentId())
            .append("productName", getProductName())
            .append("orderNum", getOrderNum())
            .append("status", getStatus())
            .toString();
    }
}
