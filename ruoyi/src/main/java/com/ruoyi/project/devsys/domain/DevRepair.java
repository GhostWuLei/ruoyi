package com.ruoyi.project.devsys.domain;

import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 检修记录对象 dev_repair
 *
 * @author wulei
 * @date 2020-10-26
 */
@Data
public class DevRepair extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 检修ID */
    private Long repairId;

    /** 设备ID */
    private Long equipId;

    /** 检修开始时间 */
    @Excel(name = "检修开始时间",dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /** 检修结束时间 */
    @Excel(name = "检修结束时间",dateFormat = "yyyy-MM-dd")
    private Date finishTime;

    /** 检修级别 */
    @Excel(name = "检修级别")
    private String repairLevel;

    /** 检修内容 */
    @Excel(name = "检修内容")
    private String repairContent;

    /** 主要处理问题 */
    @Excel(name = "主要处理问题")
    private String handleProblem;

    /** 遗留问题 */
    @Excel(name = "遗留问题")
    private String remainProblem;

    /** 检修班组 */
    @Excel(name = "检修班组")
    private String clazz;

    /** 检修单位 */
    @Excel(name = "检修单位")
    private String repairUnit;

    /** 负责人（可以有多个） */
    @Excel(name = "工作负责人", readConverterExp = "可=以有多个")
    private String leader;

    /** 工作联系人（可以有多个） */
    @Excel(name = "工作联系人（是否需要）", readConverterExp = "可=以有多个")
    private String linkman;

    /** 备注 */
    @Excel(name = "备注")
    private String remark;

}
