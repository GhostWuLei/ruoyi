package com.ruoyi.project.devsys.domain;

import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 设备信息对象 dev_information
 *
 * @author wulei
 * @date 2020-10-30
 */
@Data
public class DevInformation extends BaseEntity
{
    private static final long serialVersionUID = 1L;
    

    /** 信息ID */
    private Long informationId;

    /** 设备ID */
    private Long equipId;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String equipName;

    /** 设备型号 */
    @Excel(name = "设备型号")
    private String specification;

    /** 设备参数 */
    @Excel(name = "设备参数")
    private String equipParam;

    /** 技术要求 */
    @Excel(name = "技术要求")

    private String techParam;

    /** 检修周期 */
    @Excel(name = "检修周期")
    private String cycle;

    /** 备注 */
    @Excel(name = "备注")
    private String remark;

    /** 安装日期 */
    @Excel(name = "安装日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date installTime;

    /** 安装地点 */
    @Excel(name = "安装地点")
    private String installPlace;

}
