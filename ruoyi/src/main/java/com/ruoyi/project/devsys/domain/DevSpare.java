package com.ruoyi.project.devsys.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 备品备件对象 dev_spare
 * 
 * @author wulei
 * @date 2020-06-08
 */
public class DevSpare extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 备件ID */
    private Long spareId;

    /** 备件名称 */
    @Excel(name = "备件名称")
    private String spareName;

    /** 设备ID */
    @Excel(name = "设备ID")
    private Long equipId;

    /** 备件编号 */
    @Excel(name = "备件编号")
    private String spareCode;

    /** 备件类型 */
    @Excel(name = "备件类型")
    private String spareType;

    /** 库存数量 */
    @Excel(name = "库存数量")
    private String stockNum;

    /** 库存地址 */
    @Excel(name = "库存地址")
    private String stockPlace;

    /** 已换数量 */
    @Excel(name = "已换数量")
    private String consumeNum;

    /** 更换时间 */
    @Excel(name = "更换时间", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date consumeTime;

    /** 附件名称 */
    @Excel(name = "附件名称")
    private String fname;

    /** 附件路径 */
    @Excel(name = "附件路径")
    private String fpath;

    public void setSpareId(Long spareId) 
    {
        this.spareId = spareId;
    }

    public Long getSpareId() 
    {
        return spareId;
    }
    public void setSpareName(String spareName) 
    {
        this.spareName = spareName;
    }

    public String getSpareName() 
    {
        return spareName;
    }
    public void setEquipId(Long equipId) 
    {
        this.equipId = equipId;
    }

    public Long getEquipId() 
    {
        return equipId;
    }
    public void setSpareCode(String spareCode) 
    {
        this.spareCode = spareCode;
    }

    public String getSpareCode() 
    {
        return spareCode;
    }
    public void setSpareType(String spareType) 
    {
        this.spareType = spareType;
    }

    public String getSpareType() 
    {
        return spareType;
    }
    public void setStockNum(String stockNum) 
    {
        this.stockNum = stockNum;
    }

    public String getStockNum() 
    {
        return stockNum;
    }
    public void setStockPlace(String stockPlace) 
    {
        this.stockPlace = stockPlace;
    }

    public String getStockPlace() 
    {
        return stockPlace;
    }
    public void setConsumeNum(String consumeNum) 
    {
        this.consumeNum = consumeNum;
    }

    public String getConsumeNum() 
    {
        return consumeNum;
    }
    public void setConsumeTime(Date consumeTime) 
    {
        this.consumeTime = consumeTime;
    }

    public Date getConsumeTime() 
    {
        return consumeTime;
    }
    public void setFname(String fname) 
    {
        this.fname = fname;
    }

    public String getFname() 
    {
        return fname;
    }
    public void setFpath(String fpath) 
    {
        this.fpath = fpath;
    }

    public String getFpath() 
    {
        return fpath;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("spareId", getSpareId())
            .append("spareName", getSpareName())
            .append("equipId", getEquipId())
            .append("spareCode", getSpareCode())
            .append("spareType", getSpareType())
            .append("stockNum", getStockNum())
            .append("stockPlace", getStockPlace())
            .append("consumeNum", getConsumeNum())
            .append("consumeTime", getConsumeTime())
            .append("fname", getFname())
            .append("fpath", getFpath())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
