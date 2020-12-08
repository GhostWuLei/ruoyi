package com.ruoyi.project.devsys.service.impl;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.framework.config.RuoYiConfig;
import com.ruoyi.project.devsys.domain.DevEquip;
import com.ruoyi.project.devsys.domain.DevFile;
import com.ruoyi.project.devsys.domain.DevRepair;
import com.ruoyi.project.devsys.mapper.DevRepairMapper;
import com.ruoyi.project.devsys.service.IDevEquipService;
import com.ruoyi.project.devsys.service.IDevFileService;
import com.ruoyi.project.devsys.service.IDevRepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 检修记录Service业务层处理
 *
 * @author wulei
 * @date 2020-10-26
 */
@Service
public class DevRepairServiceImpl implements IDevRepairService
{
    @Autowired
    private DevRepairMapper devRepairMapper;
    @Autowired
    private IDevFileService fileService;
    @Autowired
    private IDevEquipService equipService;

    /**
     * 查询检修记录
     *
     * @param repairId 检修记录ID
     * @return 检修记录
     */
    @Override
    public DevRepair selectDevRepairById(Long repairId)
    {
        DevRepair devRepair = devRepairMapper.selectDevRepairById(repairId);
        String repairContent = devRepair.getRepairContent();
        devRepair.setRepairContent(repairContent.replace("<br/>","\n"));
        return devRepair;
    }

    /**
     * 查询检修记录列表
     *
     * @param devRepair 检修记录
     * @return 检修记录
     */
    @Override
    public List<DevRepair> selectDevRepairList(DevRepair devRepair)
    {
        List<DevRepair> devRepairList = devRepairMapper.selectDevRepairList(devRepair);
        for (DevRepair repair : devRepairList) {
            String repairContent = repair.getRepairContent();
            repair.setRepairContent(repairContent.replace("\n","<br/>"));
        }
        return devRepairList;
    }

    /**
     * 新增检修记录
     *
     * @param repair 检修记录
     * @return 结果
     */
    @Override
    public int insertDevRepair(DevRepair repair)
    {
        //对换行进行处理检修情况、主要处理问题、遗留问题的换行进行处理
        String repairContent = repair.getRepairContent();
        repair.setCreateTime(DateUtils.getNowDate());
        repair.setCreateBy(SecurityUtils.getUsername());

        String repairRepairContent = repair.getRepairContent();
        repair.setRepairContent(repairContent.replace("\n","<br/>"));
        return devRepairMapper.insertDevRepair(repair);
    }
    /**
     * 修改检修记录
     *
     * @param devRepair 检修记录
     * @return 结果
     */
    @Override
    public int updateDevRepair(DevRepair devRepair)
    {
        devRepair.setUpdateTime(DateUtils.getNowDate());
        String repairContent = devRepair.getRepairContent();
        devRepair.setRepairContent(repairContent.replace("\n","</br>"));
        return devRepairMapper.updateDevRepair(devRepair);
    }

    /**
     * 批量删除检修记录
     *
     * @param repairIds 需要删除的检修记录ID
     * @return 结果
     */
    @Override
    public int deleteDevRepairByIds(Long[] repairIds)
    {
        return devRepairMapper.deleteDevRepairByIds(repairIds);
    }

    /**
     * 删除检修记录信息
     *
     * @param repairId 检修记录ID
     * @return 结果
     */
    @Override
    public int deleteDevRepairById(Long repairId)
    {
        return devRepairMapper.deleteDevRepairById(repairId);
    }

    @Override
    public boolean uploadFile(Long repairId, MultipartFile[] files) {
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
                String fpath = FileUploadUtils.upload(RuoYiConfig.getRepairPath(), file);
                String substring = fpath.substring(0, fpath.lastIndexOf("/"));
                List<DevFile> devFiles = fileService.selectrepairFileById(repairId);
                for (DevFile devFile : devFiles) {
                    String fpath1 = devFile.getFpath();
                    String substring1 = fpath1.substring(0, fpath.lastIndexOf("/"));
                        if (devFile.getFname().equals(file.getOriginalFilename())) {
                            if(substring.equals(substring1)||devFile.getRepairId().equals(repairId)){
                                fileService.deleteDevFileByFileId(devFile.getFileId());
                                this.deleteAnnex(devFile.getFpath());
                                }
                            }
                        }
                    DevFile devFile = new DevFile();
                    devFile.setRepairId(repairId);
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

    @Override
    public void deleteAnne(String fpath) {
        //将相对路径转换为绝对路径
        String newPath = fpath.replaceAll(Constants.RESOURCE_PREFIX, RuoYiConfig.getProfile());
        File file = new File(newPath);
        if (file.exists()) {
            if (!file.delete()) {
                throw new CustomException("删除失败", 401);
            }
        }
    }

    @Override
    public String importUser(List<DevRepair> devRepairList, boolean updateSupport, String username, Long equipId) {
        if(StringUtils.isNull(devRepairList)|| devRepairList.size() == 0){
            throw new CustomException("导入的数据不能为空");
        }
        int insertNum = 0;
        int updateNum = 0;
        int repeatNum = 0;
        StringBuilder successMsg = new StringBuilder();
        for (DevRepair devRepair : devRepairList) {
            DevRepair devSpareName = devRepairMapper.selectByName(devRepair.getRepairContent());
            if (StringUtils.isNull(devSpareName)) {
                devRepair.setCreateBy(username);
                devRepair.setEquipId(equipId);
                this.insertDevRepair(devRepair);
                insertNum++;
            } else {
                if (updateSupport) {
                    //允许修改
                    devRepair.setEquipId(equipId);
                    devRepair.setUpdateBy(username);
                    this.updateDevRepair(devRepair);
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
    public List<DevRepair> selectDevRepairListIn(List<DevEquip> devEquips) {
        List<Long> list=new ArrayList<>();
        for (DevEquip equip : devEquips) {
            list.add(equip.getParentId());
            list.add(equip.getEquipId());
        }
        List<DevRepair> devRepairList=devRepairMapper.selectDevRepairListIn(list);
        for (DevRepair repair : devRepairList) {
            String repairContent = repair.getRepairContent();
            repair.setRepairContent(repairContent.replace("\n","<br/>"));

        }
        return devRepairList;
    }

    @Override
    public int deleteequipId(Long equipId) {
        return devRepairMapper.deleteequipId(equipId);
    }

    @Override
    public List<DevRepair> selectDevRepairListAll(DevRepair devRepair) {
        List<DevRepair> devRepairs = devRepairMapper.selectDevRepairList(devRepair);
        for (DevRepair repair : devRepairs) {
            String repairContent = repair.getRepairContent();
            repair.setRepairContent(repairContent.replace("<br/>","\n"));
        }
        return devRepairs;
    }


    public  void deleteAnnex(String fpath) {
        //将相对路径转换为绝对路径
        String newPath = fpath.replaceAll(Constants.RESOURCE_PREFIX, RuoYiConfig.getProfile());
        File file = new File(newPath);
        if (file.exists()) {
            if (!file.delete()) {
                throw new CustomException("删除失败", 401);
            }
        }
    }
}
