package com.ruoyi.project.devsys.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 设备跟踪对象 dev_track
 * 
 * @author wulei
 * @date 2020-06-16
 */
public class DevTrack extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 跟踪ID */
    private Long trackId;

    /** 跟踪事件名称 */
    @Excel(name = "跟踪事件名称")
    private String trackName;

    /** 设备ID */
    private Long equipId;

    /** 跟踪事件内容 */
    @Excel(name = "跟踪事件内容")
    private String trackContent;

    /** 开始时间 */
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    /** 结束时间 */
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date finishTime;

    /** 发起者 */
    @Excel(name = "发起者")
    private String commander;

    /** 执行者 */
    @Excel(name = "执行者")
    private String worker;

    /** 反馈内容 */
    @Excel(name = "反馈内容")
    private String feedback;

    /** 完成情况 */
    @Excel(name = "完成情况")
    private String completeStatus;

    /** 执行单位 */
    @Excel(name = "执行单位")
    private String workUnit;

    /** 附件名称 */
    private String fname;

    /** 附件路径 */
    private String fpath;

    public void setTrackId(Long trackId) 
    {
        this.trackId = trackId;
    }

    public Long getTrackId() 
    {
        return trackId;
    }
    public void setTrackName(String trackName) 
    {
        this.trackName = trackName;
    }

    public String getTrackName() 
    {
        return trackName;
    }
    public void setEquipId(Long equipId) 
    {
        this.equipId = equipId;
    }

    public Long getEquipId() 
    {
        return equipId;
    }
    public void setTrackContent(String trackContent) 
    {
        this.trackContent = trackContent;
    }

    public String getTrackContent() 
    {
        return trackContent;
    }
    public void setStartTime(Date startTime) 
    {
        this.startTime = startTime;
    }

    public Date getStartTime() 
    {
        return startTime;
    }
    public void setFinishTime(Date finishTime) 
    {
        this.finishTime = finishTime;
    }

    public Date getFinishTime() 
    {
        return finishTime;
    }
    public void setCommander(String commander) 
    {
        this.commander = commander;
    }

    public String getCommander() 
    {
        return commander;
    }
    public void setWorker(String worker) 
    {
        this.worker = worker;
    }

    public String getWorker() 
    {
        return worker;
    }
    public void setFeedback(String feedback) 
    {
        this.feedback = feedback;
    }

    public String getFeedback() 
    {
        return feedback;
    }
    public void setCompleteStatus(String completeStatus) 
    {
        this.completeStatus = completeStatus;
    }

    public String getCompleteStatus() 
    {
        return completeStatus;
    }
    public void setWorkUnit(String workUnit) 
    {
        this.workUnit = workUnit;
    }

    public String getWorkUnit() 
    {
        return workUnit;
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
            .append("trackId", getTrackId())
            .append("trackName", getTrackName())
            .append("equipId", getEquipId())
            .append("trackContent", getTrackContent())
            .append("startTime", getStartTime())
            .append("finishTime", getFinishTime())
            .append("commander", getCommander())
            .append("worker", getWorker())
            .append("feedback", getFeedback())
            .append("completeStatus", getCompleteStatus())
            .append("workUnit", getWorkUnit())
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
