package com.codemanage.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codemanage.common.constants.Constants;
import com.codemanage.common.entity.BaseResult;
import com.codemanage.common.util.UserUtils;
import com.codemanage.system.dto.request.DbTabMQueryDto;
import com.codemanage.system.dto.request.DbTabMSaveDto;
import com.codemanage.system.dto.request.DbmsQueryDto;
import com.codemanage.system.dto.response.DbTabMVo;
import com.codemanage.system.dto.response.DbmsTreeDto;
import com.codemanage.system.entity.DbTabM;
import com.codemanage.system.mapper.DbTabMMapper;
import com.codemanage.system.service.IDbFildMService;
import com.codemanage.system.service.IDbTabMService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * CVSO_数据库表_主表 服务实现类
 * </p>
 *
 * @author hyh
 * @since 2022-06-16
 */
@Service
public class DbTabMServiceImpl extends ServiceImpl<DbTabMMapper, DbTabM> implements IDbTabMService {

    @Autowired
    private DbTabMMapper dbTabMMapper;

    @Autowired
    @Lazy
    private IDbFildMService fildMService;

    /**
     * 根据条件查询可用的平台
     * @param dto
     * @return
     */
    @Override
    public List<DbTabMVo> getTableByCondition(DbTabMQueryDto dto) {
        return dbTabMMapper.getTableByCondition(dto);
    }

    /**
     * 根据id查询平台
     * @param dto
     * @return
     */
    @Override
    public DbTabMVo getTableById(DbTabMQueryDto dto) {
        return dbTabMMapper.getTableById(dto);
    }

    @Override
    public List<DbmsTreeDto> getDbmsTreeList(DbmsQueryDto dto) {
        return dbTabMMapper.getDbmsTreeList(dto);
    }

    /**
     * 删除dbms时需删除的tab
     * @param dbmsId
     * @param oldFnshDttm
     * @param newBgnDttm
     * @return
     */
    @Override
    public boolean delByDbms(String dbmsId, Date oldFnshDttm, Date newBgnDttm) {
        List<DbTabM> oldList = this.getByDbmsId(dbmsId);
        boolean flag = this.updateFnlYn(dbmsId, null, null, oldFnshDttm);
        if (flag) {
            String createUserId = UserUtils.getLoginUser().getUserId();
            for (DbTabM obj : oldList) {
                obj.setRecFnshDttm(newBgnDttm);
                obj.setRecBgnDttm(newBgnDttm);
                obj.setFnlYn(Constants.FnlYn.YES);
                obj.setCretCvUserId(createUserId);
            }
            return this.saveBatch(oldList);
        }
        return false;
    }

    @Override
    public boolean delBySchm(String dbmsId, String schmId, Date oldFnshDttm, Date newBgnDttm) {
        List<DbTabM> oldList = this.getByDbmsIdAndSchmId(dbmsId, schmId);
        boolean flag = this.updateFnlYn(dbmsId, schmId, null, oldFnshDttm);
        if (flag) {
            String createUserId = UserUtils.getLoginUser().getUserId();
            for (DbTabM obj : oldList) {
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
     * 校验table名称
     * @param dto
     * @return
     */
    @Override
    public boolean checkTabNm(DbTabMQueryDto dto) {
        int count = dbTabMMapper.checkTabNm(dto);
        if (count < 1) {
            return true;
        }
        return false;
    }

    /**
     * 获取排序序号
     * @param dbmsId
     * @param schmId
     * @return
     */
    @Override
    public Integer getSortSrno(String dbmsId, String schmId) {
        return dbTabMMapper.getSortSrno(dbmsId, schmId);
    }

    /**
     * 添加table
     * @param dto
     * @return
     */
    @Override
    public BaseResult addTab(DbTabMSaveDto dto) {
        String tabId = dbTabMMapper.getTabId();
        DbTabM obj = new DbTabM();
        obj.setCvDbmsId(dto.getCvDbmsId());
        obj.setCvSchmId(dto.getCvSchmId());
        obj.setCvDbTabId(tabId);
        obj.setRecFnshDttm(Constants.FNSHDTTM);
        obj.setRecBgnDttm(new Date());
        obj.setDbTabNm(dto.getDbTabNm());
        obj.setDbTabExplTxt(dto.getDbTabExplTxt());
        obj.setCvSortSrno(dto.getCvSortSrno());
        obj.setCretCvUserId(UserUtils.getLoginUser().getUserId());
        boolean flag = this.save(obj);
        if (flag) {
            return BaseResult.successMsg("保存成功!");
        }
        return BaseResult.failedMsg("保存失败!");
    }

    /**
     * 编辑弹窗查询详情
     * @param dto
     * @return
     */
    @Override
    public DbTabMVo getTabEditById(DbTabMQueryDto dto) {
        return dbTabMMapper.getTabEditById(dto);
    }

    /**
     * 编辑table
     * @param dto
     * @return
     */
    @Override
    public BaseResult editTab(DbTabMSaveDto dto) {
        String dbmsId = dto.getCvDbmsId();
        String schmId = dto.getCvSchmId();
        String tabId = dto.getCvDbTabId();
        if (StringUtils.isNotEmpty(dbmsId) && StringUtils.isNotEmpty(schmId)&& StringUtils.isNotEmpty(tabId)) {
            DbTabM oldObj = this.getObjById(dbmsId, schmId, tabId);
            boolean checkFlag = this.checkIsSave(tabId, oldObj.getDbTabNm(), dto.getDbTabNm());
            if (checkFlag) {
                Date oldFnshDttm = new Date();
                Date bgnDttm = new Date(oldFnshDttm.getTime() + 1);
                this.updateFnlYn(dbmsId, schmId, tabId, oldFnshDttm);
                oldObj.setRecFnshDttm(Constants.FNSHDTTM);
                oldObj.setRecBgnDttm(bgnDttm);
                oldObj.setDbTabNm(dto.getDbTabNm());
                oldObj.setDbTabExplTxt(dto.getDbTabExplTxt());
                oldObj.setFnlYn(Constants.FnlYn.YES);
                oldObj.setCvSortSrno(dto.getCvSortSrno());
                oldObj.setCretCvUserId(UserUtils.getLoginUser().getUserId());
                boolean flag = this.save(oldObj);
                if (flag) {
                    return  BaseResult.successMsg("编辑成功!");
                }
                return BaseResult.failedMsg("编辑失败!");
            }
            return BaseResult.failedMsg("检验不通过,不能保存!");
        }
        return BaseResult.failedMsg("编辑失败,缺少参数!");
    }

    /**
     * 删除table
     * @param dto
     * @return
     */
    @Override
    public boolean delTab(DbTabMQueryDto dto) {
        String dbmsId = dto.getCvDbmsId();
        String schmId = dto.getCvSchmId();
        String tabId = dto.getCvDbTabId();
        Date oldFnshDttm = new Date();
        Date newBgnDttm = new Date(oldFnshDttm.getTime() + 1);
        DbTabM oldObj = this.getObjById(dbmsId, schmId, tabId);
        this.updateFnlYn(dbmsId, schmId, tabId, oldFnshDttm);
        oldObj.setRecFnshDttm(newBgnDttm);
        oldObj.setRecBgnDttm(newBgnDttm);
        oldObj.setFnlYn(Constants.FnlYn.YES);
        oldObj.setCretCvUserId(UserUtils.getLoginUser().getUserId());
        boolean flag = this.save(oldObj);
        if (flag) {
            // 删除fild
            fildMService.delByTab(dbmsId, schmId, tabId, oldFnshDttm, newBgnDttm);
            return true;
        }
        return false;
    }

    private boolean checkIsSave(String tabId, String oldNm, String newNm) {
        if (tabId.length() < oldNm.length()) {
            return true;
        }
        return !oldNm.equals(tabId.substring(0, oldNm.length())) || oldNm.equals(newNm);
    }

    private DbTabM getObjById(String dbmsId, String schmId, String tabId) {
        QueryWrapper<DbTabM> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(DbTabM::getCvDbmsId, dbmsId)
                .eq(DbTabM::getCvSchmId, schmId)
                .eq(DbTabM::getCvDbTabId, tabId)
                .eq(DbTabM::getRecFnshDttm, Constants.FNSHDTTM);
        return this.getOne(queryWrapper);
    }


    private boolean updateFnlYn(String dbmsId, String schmId, String tabId, Date oldFnshDttm) {
        if (StringUtils.isEmpty(dbmsId) && StringUtils.isEmpty(schmId) && StringUtils.isEmpty(tabId)) {
            return false;
        }
        UpdateWrapper<DbTabM> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().set(DbTabM::getRecFnshDttm, oldFnshDttm)
                .set(DbTabM::getFnlYn, Constants.FnlYn.NO);
        updateWrapper.lambda().eq(DbTabM::getRecFnshDttm, Constants.FNSHDTTM);
        if (StringUtils.isNotEmpty(dbmsId)) {
            updateWrapper.lambda().eq(DbTabM::getCvDbmsId, dbmsId);
        }
        if (StringUtils.isNotEmpty(schmId)) {
            updateWrapper.lambda().eq(DbTabM::getCvSchmId, schmId);
        }
        if (StringUtils.isNotEmpty(tabId)) {
            updateWrapper.lambda().eq(DbTabM::getCvDbTabId, tabId);
        }
        return this.update(updateWrapper);
    }

    private List<DbTabM> getByDbmsId(String dbmsId) {
        QueryWrapper<DbTabM> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(DbTabM::getCvDbmsId, dbmsId)
                .eq(DbTabM::getRecFnshDttm, Constants.FNSHDTTM);
        return this.list(queryWrapper);
    }

    private List<DbTabM> getByDbmsIdAndSchmId(String dbmsId, String schmId) {
        QueryWrapper<DbTabM> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(DbTabM::getCvDbmsId, dbmsId)
                .eq(DbTabM::getCvSchmId, schmId)
                .eq(DbTabM::getRecFnshDttm, Constants.FNSHDTTM);
        return this.list(queryWrapper);
    }
}
