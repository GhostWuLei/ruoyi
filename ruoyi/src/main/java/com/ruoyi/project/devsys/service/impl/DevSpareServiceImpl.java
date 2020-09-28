package com.ruoyi.project.devsys.service.impl;

import java.io.File;
import java.util.List;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.config.RuoYiConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.devsys.mapper.DevSpareMapper;
import com.ruoyi.project.devsys.domain.DevSpare;
import com.ruoyi.project.devsys.service.IDevSpareService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 备品备件Service业务层处理
 * 
 * @author wulei
 * @date 2020-06-08
 */
@Service
@Transactional
public class DevSpareServiceImpl implements IDevSpareService 
{

    @Autowired
    private DevSpareMapper devSpareMapper;

    /**
     * 查询备品备件
     * 
     * @param spareId 备品备件ID
     * @return 备品备件
     */
    @Override
    public DevSpare selectDevSpareById(Long spareId)
    {
        return devSpareMapper.selectDevSpareById(spareId);
    }

    /**
     * 查询备品备件列表
     * 
     * @param devSpare 备品备件
     * @return 备品备件
     */
    @Override
    public List<DevSpare> selectDevSpareList(DevSpare devSpare)
    {
        return devSpareMapper.selectDevSpareList(devSpare);
    }

    /**
     * 新增备品备件
     * 
     * @param devSpare 备品备件
     * @return 结果
     */
    @Override
    public int insertDevSpare(DevSpare devSpare)
    {
        devSpare.setCreateTime(DateUtils.getNowDate());
        return devSpareMapper.insertDevSpare(devSpare);
    }

    /**
     * 修改备品备件
     * 
     * @param devSpare 备品备件
     * @return 结果
     */
    @Override
    public int updateDevSpare(DevSpare devSpare)
    {
        devSpare.setUpdateTime(DateUtils.getNowDate());
        return devSpareMapper.updateDevSpare(devSpare);
    }

    /**
     * 批量删除备品备件
     * 
     * @param spareIds 需要删除的备品备件ID
     * @return 结果
     */
    @Override
    public int deleteDevSpareByIds(Long[] spareIds)
    {
        return devSpareMapper.deleteDevSpareByIds(spareIds);
    }

    /**
     * 删除备品备件信息
     * 
     * @param spareId 备品备件ID
     * @return 结果
     */
    @Override
    public int deleteDevSpareById(Long spareId)
    {
        return devSpareMapper.deleteDevSpareById(spareId);
    }

    /**
     * 删除附件 annex: 附件
     * @param fpath
     */
    @Override
    public void deleteAnnex(String fpath){
        //将相对路径转换为绝对路径
        String newPath = fpath.replaceAll(Constants.RESOURCE_PREFIX, RuoYiConfig.getProfile());
        File file = new File(newPath);
        if(file.exists()){
            if(!file.delete()){
                throw new CustomException("删除失败", 401);
            }
        }
    }
}
