package com.ruoyi.framework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 读取项目相关配置
 *
 * @author ruoyi
 */
@Component
@ConfigurationProperties(prefix = "ruoyi")
public class RuoYiConfig
{
    /** 项目名称 */
    private String name;

    /** 版本 */
    private String version;

    /** 版权年份 */
    private String copyrightYear;

    /** 实例演示开关 */
    private boolean demoEnabled;

    /** 上传路径 */
    private static String profile;

    /** 获取地址开关 */
    private static boolean addressEnabled;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }

    public String getCopyrightYear()
    {
        return copyrightYear;
    }

    public void setCopyrightYear(String copyrightYear)
    {
        this.copyrightYear = copyrightYear;
    }

    public boolean isDemoEnabled()
    {
        return demoEnabled;
    }

    public void setDemoEnabled(boolean demoEnabled)
    {
        this.demoEnabled = demoEnabled;
    }

    public static String getProfile()
    {
        return profile;
    }

    public void setProfile(String profile)
    {
        RuoYiConfig.profile = profile;
    }

    public static boolean isAddressEnabled()
    {
        return addressEnabled;
    }

    public void setAddressEnabled(boolean addressEnabled)
    {
        RuoYiConfig.addressEnabled = addressEnabled;
    }

    /**
     * 获取头像上传路径
     */
    public static String getAvatarPath()
    {
        return getProfile() + "/avatar";
    }

    /**
     * 获取下载路径
     */
    public static String getDownloadPath()
    {
        return getProfile() + "/download/";
    }

    /**
     * 获取上传路径
     */
    public static String getUploadPath()
    {
        return getProfile() + "/upload";
    }

    /**
     * 获取上传系统图的路径
     * @return
     */
    public static String getPicturePath(){
        return getProfile() + "/picture";
    }
    /**
     * 附件上传路径
     */
    public static String getAccoutPath(){
        return getProfile()+"/accout/spare";
    }

    /**
     * 附件上传路径
     */
    public static String getRepairPath(){
        return getProfile()+"/accout/repair";
    }

    /**
     * 附件上传路径
     */
    public static String getNormPath(){
        return getProfile()+"/accout/norm";
    }


    /**
     * 技术资料路径
     */
    public static String getmaterialPath(){
        return getProfile()+"/accout/material";
    }
    /**
     * 设备定值路径
     */
    public static String getconstvalPath(){
        return getProfile()+"/accout/constval";
    }
    /**
     * 设备跟踪路径
     */
    public static String gettrackPath(){
        return getProfile()+"/accout/track";
    }
    /**
     * 重大技改路径
     */
    public static String getreformPath(){
        return getProfile()+"/accout/reform";
    }

    /**
     * 重大技改路径
     */
    public static String getinformation(){
        return getProfile()+"/accout/information";
    }
    /**
     * 故障记录
     */
    public static String getfault(){
        return getProfile()+"/accout/fault";
    }
    /**
     * 异动变更
     */
    public static String getalteration(){
        return getProfile()+"/accout/alteration";
    }

    /**
     *
     * 附属设备明细id
     */
    public static String getsubsidiary(){
        return getProfile()+"/accout/subsidiary";
    }
}
