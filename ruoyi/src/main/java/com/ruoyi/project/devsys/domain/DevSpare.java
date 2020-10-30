package com.ruoyi.project.devsys.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 备品备件对象 dev_spare
 * 
 * @author wulei
 * @date 2020-10-30
 */
public class DevSpare extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 备件ID */
    private Long spareId;

    /** 设备ID */
    private Long equipId;

    /** 备件名称 */
    @Excel(name = "备件名称")
    private String spareName;

    /** 规格型号 */
    @Excel(name = "规格型号")
    private String specification;

    /** 技术参数 */
    @Excel(name = "技术参数")
    private String techParam;

    /** 数量 */
    @Excel(name = "数量")
    private String num;

    /** 图号 */
    @Excel(name = "图号")
    private String pictureNum;

    /** 厂家 */
    @Excel(name = "厂家")
    private String productor;

    public void setSpareId(Long spareId) 
    {
        this.spareId = spareId;
    }

    public Long getSpareId() 
    {
        return spareId;
    }
    public void setEquipId(Long equipId) 
    {
        this.equipId = equipId;
    }

    public Long getEquipId() 
    {
        return equipId;
    }
    public void setSpareName(String spareName) 
    {
        this.spareName = spareName;
    }

    public String getSpareName() 
    {
        return spareName;
    }
    public void setSpecification(String specification) 
    {
        this.specification = specification;
    }

    public String getSpecification() 
    {
        return specification;
    }
    public void setTechParam(String techParam) 
    {
        this.techParam = techParam;
    }

    public String getTechParam() 
    {
        return techParam;
    }
    public void setNum(String num) 
    {
        this.num = num;
    }

    public String getNum() 
    {
        return num;
    }
    public void setPictureNum(String pictureNum) 
    {
        this.pictureNum = pictureNum;
    }

    public String getPictureNum() 
    {
        return pictureNum;
    }
    public void setProductor(String productor) 
    {
        this.productor = productor;
    }

    public String getProductor() 
    {
        return productor;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("spareId", getSpareId())
            .append("equipId", getEquipId())
            .append("spareName", getSpareName())
            .append("specification", getSpecification())
            .append("techParam", getTechParam())
            .append("num", getNum())
            .append("pictureNum", getPictureNum())
            .append("productor", getProductor())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
