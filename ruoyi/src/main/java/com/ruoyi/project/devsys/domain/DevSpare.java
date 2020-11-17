package com.ruoyi.project.devsys.domain;

import lombok.Data;
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
@Data
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

    /** 备注 */
    @Excel(name = "备注")
    private String remark;

}
