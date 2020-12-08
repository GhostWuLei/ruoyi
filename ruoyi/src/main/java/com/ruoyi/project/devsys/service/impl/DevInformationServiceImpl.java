package com.ruoyi.project.devsys.service.impl;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.framework.config.RuoYiConfig;
import com.ruoyi.project.devsys.domain.DevEquip;
import com.ruoyi.project.devsys.domain.DevFile;
import com.ruoyi.project.devsys.domain.DevInformation;
import com.ruoyi.project.devsys.mapper.DevInformationMapper;
import com.ruoyi.project.devsys.service.IDevEquipService;
import com.ruoyi.project.devsys.service.IDevFileService;
import com.ruoyi.project.devsys.service.IDevInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 设备信息Service业务层处理
 *
 * @author wulei
 * @date 2020-10-30
 */
@Service
public class DevInformationServiceImpl implements IDevInformationService
{
    @Autowired
    private DevInformationMapper devInformationMapper;
    @Autowired
    private IDevFileService fileService;
    @Autowired
    private IDevEquipService equipService;

    /**
     * 查询设备信息
     *
     * @param informationId 设备信息ID
     * @return 设备信息
     */
    @Override
    public DevInformation selectDevInformationById(Long informationId)
    {
        DevInformation devInformation = devInformationMapper.selectDevInformationById(informationId);
        String equipParam = devInformation.getEquipParam();
        devInformation.setEquipParam(equipParam.replace("<br/>","\n"));
        return devInformation;

    }

    /**
     * 查询设备信息列表
     *
     * @param devInformation 设备信息
     * @return 设备信息
     */
    @Override
    public List<DevInformation> selectDevInformationList(DevInformation devInformation)
    {
        List<DevInformation> devInformationList = devInformationMapper.selectDevInformationList(devInformation);
        for (DevInformation information : devInformationList) {
            String equipParam = information.getEquipParam();
            information.setEquipParam(equipParam.replace("\n","<br/>"));
        }
        return devInformationList;
    }

    /**
     * 新增设备信息
     *
     * @param devInformation 设备信息
     * @return 结果
     */
    @Override
    public int insertDevInformation(DevInformation devInformation)
    {
        devInformation.setCreateTime(DateUtils.getNowDate());
        String equipParam = devInformation.getEquipParam();
        devInformation.setEquipParam(equipParam.replace("\n","<br/>"));
        return devInformationMapper.insertDevInformation(devInformation);
    }

    /**
     * 修改设备信息
     *
     * @param devInformation 设备信息
     * @return 结果
     */
    @Override
    public int updateDevInformation(DevInformation devInformation)
    {
        devInformation.setUpdateTime(DateUtils.getNowDate());
        String equipParam = devInformation.getEquipParam();
        devInformation.setEquipParam(equipParam.replace("\n","</br>"));
        return devInformationMapper.updateDevInformation(devInformation);
    }

    /**
     * 批量删除设备信息
     *
     * @param informationIds 需要删除的设备信息ID
     * @return 结果
     */
    @Override
    public int deleteDevInformationByIds(Long[] informationIds)
    {
        return devInformationMapper.deleteDevInformationByIds(informationIds);
    }

    /**
     * 删除设备信息信息
     *
     * @param informationId 设备信息ID
     * @return 结果
     */
    @Override
    public int deleteDevInformationById(Long informationId)
    {
        return devInformationMapper.deleteDevInformationById(informationId);
    }

    @Override
    public boolean uploadFile(Long informationId, MultipartFile[] files) {
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
                String fpath = FileUploadUtils.upload(RuoYiConfig.getinformation(), file);
                String substring = fpath.substring(0, fpath.lastIndexOf("/"));
                List<DevFile> devFiles = fileService.selectinformationIdById(informationId);
                for (DevFile devFile : devFiles) {
                    String fpath1 = devFile.getFpath();
                    String substring1 = fpath1.substring(0, fpath.lastIndexOf("/"));
                    if (devFile.getFname().equals(file.getOriginalFilename())) {
                        if(substring.equals(substring1)|| devFile.getInformationId().equals(informationId)){
                            fileService.deleteDevFileByFileId(devFile.getFileId());
                            this.deleteAnnex(devFile.getFpath());
                        }
                    }
                }
                    DevFile devFile = new DevFile();
                    devFile.setInformationId(informationId);
                    devFile.setFname(file.getOriginalFilename());
                    devFile.setFpath(fpath);
                    fileService.insertDevFile(devFile);
                }catch (Exception e){
                    e.printStackTrace();
                    return  false;
                }
            }
        }
        return true;
    }
    public  void deleteAnnex(String fpath){
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
    public String importUser(List<DevInformation> devInformationList, boolean updateSupport, String username, Long equipId) {
        if(StringUtils.isNull(devInformationList)|| devInformationList.size() == 0){
            throw new CustomException("导入的数据不能为空");
        }
        int insertNum = 0;
        int updateNum = 0;
        int repeatNum = 0;
        StringBuilder successMsg = new StringBuilder();
        for (DevInformation devInformation : devInformationList) {
            DevInformation devSpareName = devInformationMapper.selectName(devInformation.getEquipName());
            if (StringUtils.isNull(devSpareName)) {
                devInformation.setCreateBy(username);
                devInformation.setEquipId(equipId);
                this.insertDevInformation(devInformation);
                insertNum++;
            } else {
                if (updateSupport) {
                    //允许修改
                    devInformation.setCreateBy(username);
                    devInformation.setEquipId(equipId);
                    this.updateDevInformation(devInformation);
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
    public List<DevInformation> selectDevInformationListIn(List<DevEquip> devEquips) {
        List<Long> list=new ArrayList<>();
        for (DevEquip devEquip : devEquips) {
            list.add(devEquip.getEquipId());
            list.add(devEquip.getParentId());
        }
        List<DevInformation> devInformationList=devInformationMapper.selectDevInformationListIn(list);
        for (DevInformation information : devInformationList) {
            String equipParam = information.getEquipParam();
            information.setEquipParam(equipParam.replace("\n","<br/>"));
        }
        return devInformationList;
    }

    @Override
    public int deleteequipId(Long equipId) {
        return devInformationMapper.deleteequipId(equipId);
    }

    @Override
    public List<DevInformation> selectDevInformationListAll(DevInformation devInformation) {
        List<DevInformation> devInformations = devInformationMapper.selectDevInformationList(devInformation);
            for (DevInformation information : devInformations) {
                String equipParam = information.getEquipParam();
                information.setEquipParam(equipParam.replace("<br/>","\n"));
            }
        return devInformations;
    }
}
