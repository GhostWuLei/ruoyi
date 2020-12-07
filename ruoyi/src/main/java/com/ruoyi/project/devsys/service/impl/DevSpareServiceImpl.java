package com.ruoyi.project.devsys.service.impl;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.framework.config.RuoYiConfig;
import com.ruoyi.project.devsys.domain.DevEquip;
import com.ruoyi.project.devsys.domain.DevFile;
import com.ruoyi.project.devsys.domain.DevSpare;
import com.ruoyi.project.devsys.mapper.DevSpareMapper;
import com.ruoyi.project.devsys.service.IDevFileService;
import com.ruoyi.project.devsys.service.IDevSpareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 备品备件Service业务层处理
 *
 * @author wulei
 * @date 2020-10-30
 */
@Service
public class DevSpareServiceImpl  implements IDevSpareService {
    @Autowired
    private DevSpareMapper devSpareMapper;
    @Autowired
    private IDevFileService fileService;

    /**
     * 查询备品备件
     *
     * @param spareId 备品备件ID
     * @return 备品备件
     */
    @Override
    public DevSpare selectDevSpareById(Long spareId) {
        DevSpare devSpare = devSpareMapper.selectDevSpareById(spareId);
        String techParam = devSpare.getTechParam();
        devSpare.setTechParam(techParam.replace("\n","</br>"));
        return devSpare;
    }

    /**
     * 查询备品备件列表
     *
     * @param devSpare 备品备件
     * @return 备品备件
     */
    @Override
    public List<DevSpare> selectDevSpareList(DevSpare devSpare) {
        List<DevSpare> devSpares = devSpareMapper.selectDevSpareList(devSpare);
        for (DevSpare spare : devSpares) {
            String techParam = spare.getTechParam();
            spare.setTechParam(techParam.replace("\n","</br>"));
        }
        return devSpares;
    }

    /**
     * 新增备品备件
     *
     * @param devSpare 备品备件
     * @return 结果
     */
    @Override
    public int insertDevSpare(DevSpare devSpare) {
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
    public int updateDevSpare(DevSpare devSpare) {
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
    public int deleteDevSpareByIds(Long[] spareIds) {
        return devSpareMapper.deleteDevSpareByIds(spareIds);
    }

    /**
     * 删除备品备件信息
     *
     * @param spareId 备品备件ID
     * @return 结果
     */
    @Override
    public int deleteDevSpareById(Long spareId) {
        return devSpareMapper.deleteDevSpareById(spareId);
    }

    //上传文件
    @Override
    public boolean uploadFile(Long spareId, MultipartFile[] files) {
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                // 兼容IE
                String fname = file.getOriginalFilename(); // IE浏览器返回的是路径 chrome浏览器返回的是文件名加后缀
                int unixSep = fname.lastIndexOf("/");
                int winSep = fname.lastIndexOf("\\");
                int pos = (winSep > unixSep ? winSep : unixSep);
                if (pos != -1) {
                    fname = fname.substring(pos + 1);
                }
                try {
                String fpath = FileUploadUtils.upload(RuoYiConfig.getAccoutPath(), file);
                String substring = fpath.substring(0, fpath.lastIndexOf("/"));
                List<DevFile> devFiles = fileService.selectDevFileById(spareId);
                for (DevFile devFile : devFiles) {
                    String fpath1 = devFile.getFpath();
                    String substring1 = fpath1.substring(0, fpath.lastIndexOf("/"));
                    if (devFile.getFname().equals(file.getOriginalFilename())) {
                        if(substring.equals(substring1)||devFile.getSpareId().equals(spareId)){
                            fileService.deleteDevFileByFileId(devFile.getFileId());
                            deleteAnnex(devFile.getFpath());
                        }
                    }
                }
                DevFile devFile = new DevFile();
                devFile.setSpareId(spareId);
                devFile.setFname(file.getOriginalFilename());
                devFile.setFpath(fpath);
                fileService.insertDevFile(devFile);
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }

            }
        }
        return true;
    }

    @Override
    public void deleteAnnexFile(String fpath) {
        //将相对路径转换为绝对路径
        String newPath = fpath.replaceAll(Constants.RESOURCE_PREFIX, RuoYiConfig.getProfile());
        File file = new File(newPath);
        if(file.exists()){
            if(!file.delete()){
                throw new CustomException("删除失败", 401);
            }
        }
    }

    @Override
    public String importUser(List<DevSpare> spareList, boolean updateSupport, String username,Long equipId) {
        if(StringUtils.isNull(spareList)|| spareList.size() == 0){
            throw new CustomException("导入的数据不能为空");
        }
        int insertNum = 0;
        int updateNum = 0;
        int repeatNum = 0;
        StringBuilder successMsg = new StringBuilder();
        for (DevSpare devSpare : spareList) {
            DevSpare devSpareName = devSpareMapper.selectDevSpareByName(devSpare.getSpareName());
            if (StringUtils.isNull(devSpareName)) {
                devSpare.setCreateBy(username);
                devSpare.setEquipId(equipId);
                this.insertDevSpare(devSpare);
                insertNum++;
            } else {
                if (updateSupport) {
                    //允许修改
                    devSpare.setEquipId(equipId);
                    devSpare.setUpdateBy(username);
                    this.updateDevSpare(devSpare);
                    updateNum++;
                } else {
                    repeatNum++;
                }
            }
        }
        successMsg.insert(0, "导入数据已完成！新增"+insertNum+"条，更新"+updateNum+"条，"+repeatNum+"条数据已存在，未修改");
        return successMsg.toString();
    }

    @Override
    public List<DevSpare> selectDevSpareListIn(List<DevEquip> devEquipList) {
        List<Long> list=new ArrayList<>();
        for (DevEquip devEquip : devEquipList) {
            list.add(devEquip.getEquipId());
            list.add(devEquip.getParentId());
        }
        List<DevSpare> devSpares=devSpareMapper.selectDevSpareListIn(list);
        for (DevSpare spare : devSpares) {
            String techParam = spare.getTechParam();
            spare.setTechParam(techParam.replace("\n","</br>"));
        }
        return devSpares;
    }

    @Override
    public int deleteequipId(Long equipId) {
        return devSpareMapper.deleteequipId(equipId);
    }

    public static void deleteAnnex(String fpath){
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
