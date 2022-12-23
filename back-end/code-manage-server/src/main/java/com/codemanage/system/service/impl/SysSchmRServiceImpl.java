package com.codemanage.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codemanage.common.constants.Constants;
import com.codemanage.common.util.UserUtils;
import com.codemanage.system.dto.request.SysSchmRSaveDto;
import com.codemanage.system.entity.SysSchmR;
import com.codemanage.system.mapper.SysSchmRMapper;
import com.codemanage.system.service.ISysSchmRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * CVSO_系统数据库模式_关系表 服务实现类
 * </p>
 *
 * @author hyh
 * @since 2022-06-16
 */
@Service
public class SysSchmRServiceImpl extends ServiceImpl<SysSchmRMapper, SysSchmR> implements ISysSchmRService {

    @Autowired
    private SysSchmRMapper sysSchmRMapper;

    /**
     * 保存系统与模式关系
     * @param sysId
     * @param dbmsId
     * @param schmId
     * @param newBgnDttm
     * @return
     */
    @Override
    public boolean addObj(String sysId, String dbmsId, String schmId, Date newBgnDttm) {
        // 获取序号
        Integer sortSrno = sysSchmRMapper.getSortSrno(sysId);
        SysSchmR obj = new SysSchmR();
        obj.setCvSysId(sysId);
        obj.setCvDbmsId(dbmsId);
        obj.setCvSchmId(schmId);
        obj.setRecFnshDttm(Constants.FNSHDTTM);
        obj.setRecBgnDttm(newBgnDttm);
        obj.setCvSortSrno(sortSrno);
        obj.setCretCvUserId(UserUtils.getLoginUser().getUserId());
        return this.save(obj);
    }

    /**
     * 更新系统与模式关系
     * @param cvSysId
     * @param schmList
     * @param oldFnshDttm
     * @param newBgnDttm
     * @return
     */
    @Override
    public boolean updateSysSchmR(String cvSysId, List<SysSchmRSaveDto> schmList, Date oldFnshDttm, Date newBgnDttm) {
        List<SysSchmR> oldList = this.getBySysId(cvSysId);
        List<SysSchmR> addList = new ArrayList<>();
        List<SysSchmR> delList = new ArrayList<>();
        if (CollectionUtils.isEmpty(oldList) && CollectionUtils.isNotEmpty(schmList)) {
            for (SysSchmRSaveDto saveDto : schmList) {
                SysSchmR obj = new SysSchmR();
                obj.setCvSysId(cvSysId);
                obj.setCvDbmsId(saveDto.getCvDbmsId());
                obj.setCvSchmId(saveDto.getCvSchmId());
                addList.add(obj);
            }
        } else if (CollectionUtils.isNotEmpty(oldList) && CollectionUtils.isEmpty(schmList)) {
            delList = oldList;
        } else {
            for (SysSchmR obj : oldList) {
                boolean flag = true;
                for (SysSchmRSaveDto saveDto : schmList) {
                    if (obj.getCvDbmsId().equals(saveDto.getCvDbmsId()) &&
                            obj.getCvSchmId().equals(saveDto.getCvSchmId())) {
                        flag = false;
                    }
                }
                if (flag) {
                    delList.add(obj);
                }
            }
            for (SysSchmRSaveDto saveDto : schmList) {
                boolean flag = true;
                for (SysSchmR obj : oldList) {
                    if (saveDto.getCvDbmsId().equals(obj.getCvDbmsId()) &&
                            saveDto.getCvSchmId().equals(obj.getCvSchmId())) {
                        flag = false;
                    }
                }
                if (flag) {
                    SysSchmR obj = new SysSchmR();
                    obj.setCvSysId(cvSysId);
                    obj.setCvDbmsId(saveDto.getCvDbmsId());
                    obj.setCvSchmId(saveDto.getCvSchmId());
                    addList.add(obj);
                }
            }
        }
        if (CollectionUtils.isNotEmpty(delList)) {
            for (SysSchmR obj : delList) {
                this.updateFnlYn(obj, oldFnshDttm);
            }
        }
        if (CollectionUtils.isNotEmpty(addList)) {
            for (SysSchmR obj : addList) {
                this.addObj(obj.getCvSysId(), obj.getCvDbmsId(), obj.getCvSchmId(), newBgnDttm);
            }
        }

        return true;
    }

    /**
     * 删除系统与模式关系
     * @param sysId
     * @param oldFnshDttm
     * @param newBgnDttm
     * @return
     */
    @Override
    public boolean delSysSchmR(String sysId, Date oldFnshDttm, Date newBgnDttm) {
        List<SysSchmR> oldList = this.getBySysId(sysId);
        boolean flag = this.updateFnlYnBySysId(sysId, oldFnshDttm);
        if (flag) {
            for (SysSchmR obj : oldList) {
                obj.setRecFnshDttm(newBgnDttm);
                obj.setRecBgnDttm(newBgnDttm);
                obj.setFnlYn(Constants.FnlYn.YES);
                obj.setCretCvUserId(UserUtils.getLoginUser().getUserId());
            }
            return this.saveBatch(oldList);
        }
        return false;
    }

    /**
     * 删除dbms时需删除的sysSchmR
     * @param dbmsId
     * @param oldFnshDttm
     * @param newBgnDttm
     * @return
     */
    @Override
    public boolean delByDbms(String dbmsId, Date oldFnshDttm, Date newBgnDttm) {
        List<SysSchmR> oldList = this.getByDbmsId(dbmsId);
        boolean flag = this.updateFnlYnByDbmsId(dbmsId, oldFnshDttm);
        if (flag) {
            String createUserId = UserUtils.getLoginUser().getUserId();
            for (SysSchmR obj : oldList) {
                obj.setRecFnshDttm(newBgnDttm);
                obj.setRecBgnDttm(newBgnDttm);
                obj.setFnlYn(Constants.FnlYn.YES);
                obj.setCretCvUserId(createUserId);
            }
            return this.saveBatch(oldList);
        }
        return false;
    }

//    @Override
//    public boolean editBySchm(SchmMSaveDto dto, Date oldFnshDttm, Date bgnDttm) {
//        List<SysSchmR> oldList = this.getByDbmsIdAndSchmId(dto.getCvDbmsId(), dto.getCvSchmId());
//        boolean flag = this.updateFnlYnByDbmsIdAndSchmId(dto.getCvDbmsId(), dto.getCvSchmId(), oldFnshDttm);
//        if (flag) {
//            String createUserId = UserUtils.getLoginUser().getUserId();
//            for (SysSchmR obj : oldList) {
//                obj.setRecFnshDttm(Constants.FNSHDTTM);
//                obj.setRecBgnDttm(bgnDttm);
//                obj.setFnlYn(Constants.FnlYn.YES);
//                obj.setCretCvUserId(createUserId);
//            }
//            return this.saveBatch(oldList);
//        }
//
//        return false;
//    }

    @Override
    public boolean delBySchm(String dbmsId, String schmId, Date oldFnshDttm, Date newBgnDttm) {
        List<SysSchmR> oldList = this.getByDbmsIdAndSchmId(dbmsId, schmId);
        boolean flag = this.updateFnlYnByDbmsIdAndSchmId(dbmsId, schmId, oldFnshDttm);
        if (flag) {
            String createUserId = UserUtils.getLoginUser().getUserId();
            for (SysSchmR obj : oldList) {
                obj.setRecFnshDttm(newBgnDttm);
                obj.setRecBgnDttm(newBgnDttm);
                obj.setFnlYn(Constants.FnlYn.YES);
                obj.setCretCvUserId(createUserId);
            }
            return this.saveBatch(oldList);
        }
        return false;
    }


    /**
     * 查询系统与模式的关联关系
     * @param sysId
     * @return
     */
    private List<SysSchmR> getBySysId(String sysId) {
        QueryWrapper<SysSchmR> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysSchmR::getCvSysId, sysId)
                .eq(SysSchmR::getRecFnshDttm, Constants.FNSHDTTM);
        return this.list(queryWrapper);
    }

    private boolean updateFnlYn(SysSchmR obj, Date oldFnshDttm){
        UpdateWrapper<SysSchmR> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().set(SysSchmR::getRecFnshDttm, oldFnshDttm)
                .set(SysSchmR::getFnlYn, Constants.FnlYn.NO)
                .eq(SysSchmR::getCvSysId, obj.getCvSysId())
                .eq(SysSchmR::getCvDbmsId, obj.getCvDbmsId())
                .eq(SysSchmR::getCvSchmId, obj.getCvSchmId())
                .eq(SysSchmR::getRecFnshDttm, Constants.FNSHDTTM);
        return this.update(updateWrapper);
    }
    
    private boolean updateFnlYnBySysId(String sysId, Date oldFnshDttm) {
        UpdateWrapper<SysSchmR> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().set(SysSchmR::getRecFnshDttm, oldFnshDttm)
                .set(SysSchmR::getFnlYn, Constants.FnlYn.NO)
                .eq(SysSchmR::getCvSysId, sysId)
                .eq(SysSchmR::getRecFnshDttm, Constants.FNSHDTTM);
        return this.update(updateWrapper);
    }

    private boolean updateFnlYnByDbmsId(String dbmsId, Date oldFnshDttm) {
        UpdateWrapper<SysSchmR> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().set(SysSchmR::getRecFnshDttm, oldFnshDttm)
                .set(SysSchmR::getFnlYn, Constants.FnlYn.NO)
                .eq(SysSchmR::getCvDbmsId, dbmsId)
                .eq(SysSchmR::getRecFnshDttm, Constants.FNSHDTTM);
        return this.update(updateWrapper);
    }

    private List<SysSchmR> getByDbmsId(String dbmsId) {
        QueryWrapper<SysSchmR> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysSchmR::getCvDbmsId, dbmsId)
                .eq(SysSchmR::getRecFnshDttm, Constants.FNSHDTTM);
        return this.list(queryWrapper);
    }


    private boolean updateFnlYnByDbmsIdAndSchmId(String dbmsId, String schmId, Date oldFnshDttm) {
        UpdateWrapper<SysSchmR> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().set(SysSchmR::getRecFnshDttm, oldFnshDttm)
                .set(SysSchmR::getFnlYn, Constants.FnlYn.NO)
                .eq(SysSchmR::getCvDbmsId, dbmsId)
                .eq(SysSchmR::getCvSchmId, schmId)
                .eq(SysSchmR::getRecFnshDttm, Constants.FNSHDTTM);
        return this.update(updateWrapper);
    }

    private List<SysSchmR> getByDbmsIdAndSchmId(String dbmsId, String schmId) {
        QueryWrapper<SysSchmR> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysSchmR::getCvDbmsId, dbmsId)
                .eq(SysSchmR::getCvSchmId, schmId)
                .eq(SysSchmR::getRecFnshDttm, Constants.FNSHDTTM);
        return this.list(queryWrapper);
    }
}
