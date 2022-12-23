package com.codemanage.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codemanage.common.constants.Constants;
import com.codemanage.common.entity.BaseResult;
import com.codemanage.common.util.UserUtils;
import com.codemanage.system.dto.request.DbFildMQueryDto;
import com.codemanage.system.dto.request.DbFildMSaveDto;
import com.codemanage.system.dto.request.DbFildQueryDto;
import com.codemanage.system.dto.request.DbmsQueryDto;
import com.codemanage.system.dto.response.DbFildMVo;
import com.codemanage.system.dto.response.DbmsTreeDto;
import com.codemanage.system.entity.DbFildM;
import com.codemanage.system.mapper.DbFildMMapper;
import com.codemanage.system.service.IDbFildMService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * CVSO_数据库字段_主表 服务实现类
 * </p>
 *
 * @author hyh
 * @since 2022-07-19
 */
@Service
public class DbFildMServiceImpl extends ServiceImpl<DbFildMMapper, DbFildM> implements IDbFildMService {


    @Autowired
    private DbFildMMapper dbFildMMapper;

    /**
     * 根据条件查询可用的专栏
     * @param dto
     * @return
     */
    @Override
    public List<DbFildMVo> getFildByCondition(DbFildMQueryDto dto) {
        return dbFildMMapper.getFildByCondition(dto);
    }

    @Override
    public DbFildMVo getFildById(DbFildMQueryDto dto) {
        return dbFildMMapper.getFildById(dto);
    }

    @Override
    public List<DbmsTreeDto> getDbmsTreeList(DbmsQueryDto dto) {
        return dbFildMMapper.getDbmsTreeList(dto);
    }

    @Override
    public boolean delByDbms(String dbmsId, Date oldFnshDttm, Date newBgnDttm) {
        List<DbFildM> oldList = this.getByDbmsId(dbmsId);
        boolean flag = this.updateDelYn(dbmsId, null, null, null, oldFnshDttm);
        if (flag) {
            String createUserId = UserUtils.getLoginUser().getUserId();
            for (DbFildM obj : oldList) {
                obj.setRecFnshDttm(newBgnDttm);
                obj.setRecBgnDttm(newBgnDttm);
                obj.setDelYn(Constants.DelYn.NO);
                obj.setCretCvUserId(createUserId);
            }
            return this.saveBatch(oldList);
        }
        return false;
    }

    @Override
    public boolean delBySchm(String dbmsId, String schmId, Date oldFnshDttm, Date newBgnDttm) {
        List<DbFildM> oldList = this.getByDbmsIdAndSchmId(dbmsId, schmId);
        boolean flag = this.updateDelYn(dbmsId, schmId, null, null, oldFnshDttm);
        if (flag) {
            String createUserId = UserUtils.getLoginUser().getUserId();
            for (DbFildM obj : oldList) {
                obj.setRecFnshDttm(newBgnDttm);
                obj.setRecBgnDttm(newBgnDttm);
                obj.setDelYn(Constants.DelYn.NO);
                obj.setCretCvUserId(createUserId);
            }
            return this.saveBatch(oldList);
        }
        return false;
    }

    @Override
    public boolean delByTab(String dbmsId, String schmId, String tabId, Date oldFnshDttm, Date newBgnDttm) {
        List<DbFildM> oldList = this.getByDbmsIdAndSchmIdAndTabId(dbmsId, schmId, tabId);
        boolean flag = this.updateDelYn(dbmsId, schmId, tabId, null, oldFnshDttm);
        if (flag) {
            String createUserId = UserUtils.getLoginUser().getUserId();
            for (DbFildM obj : oldList) {
                obj.setRecFnshDttm(newBgnDttm);
                obj.setRecBgnDttm(newBgnDttm);
                obj.setDelYn(Constants.DelYn.NO);
                obj.setCretCvUserId(createUserId);
            }
            return this.saveBatch(oldList);
        }
        return false;
    }

    /**
     * 校验fild名称
     * @param dto
     * @return
     */
    @Override
    public boolean checkFildNm(DbFildMQueryDto dto) {
        int count = dbFildMMapper.checkFildNm(dto);
        if (count < 1) {
            return true;
        }
        return false;
    }

    /**
     * 获取排序序号
     * @param dbmsId
     * @param schmId
     * @param tabId
     * @return
     */
    @Override
    public Integer getSortSrno(String dbmsId, String schmId, String tabId) {
        return dbFildMMapper.getSortSrno(dbmsId, schmId, tabId);
    }

    @Override
    public BaseResult addFild(DbFildMSaveDto dto) {
        String fildId = dbFildMMapper.getFildId();
        Integer dataLen = dto.getIntmLen() + dto.getDcmlLen();
        DbFildM obj = new DbFildM();
        BeanUtils.copyProperties(dto, obj);
        obj.setCvDbFildId(fildId);
        obj.setRecFnshDttm(Constants.FNSHDTTM);
        obj.setRecBgnDttm(new Date());
        obj.setDataLen(dataLen);
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
    public DbFildMVo getFildEditById(DbFildMQueryDto dto) {
        return dbFildMMapper.getFildEditById(dto);
    }

    /**
     * 编辑fild
     * @param dto
     * @return
     */
    @Override
    public BaseResult editFild(DbFildMSaveDto dto) {
        String dbmsId = dto.getCvDbmsId();
        String schmId = dto.getCvSchmId();
        String tabId = dto.getCvDbTabId();
        String fildId = dto.getCvDbFildId();
        if (StringUtils.isNotEmpty(dbmsId) && StringUtils.isNotEmpty(schmId)
                && StringUtils.isNotEmpty(tabId) && StringUtils.isNotEmpty(fildId)) {
            DbFildM oldObj = this.getObjById(dbmsId, schmId, tabId, fildId);
            boolean checkFlag = this.checkIsSave(fildId, oldObj.getDbFildNm(), dto.getDbFildNm());
            if (checkFlag) {
                Date oldFnshDttm = new Date();
                Date bgnDttm = new Date(oldFnshDttm.getTime() + 1);
                this.updateDelYn(dbmsId, schmId, tabId, fildId, oldFnshDttm);
                BeanUtils.copyProperties(dto, oldObj);
                Integer dataLen = dto.getIntmLen() + dto.getDcmlLen();
                oldObj.setRecFnshDttm(Constants.FNSHDTTM);
                oldObj.setRecBgnDttm(bgnDttm);
                oldObj.setRecBgnDttm(new Date());
                oldObj.setDataLen(dataLen);
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

    @Override
    public boolean delFild(DbFildMQueryDto dto) {
        String dbmsId = dto.getCvDbmsId();
        String schmId = dto.getCvSchmId();
        String tabId = dto.getCvDbTabId();
        String fildId = dto.getCvDbFildId();
        Date oldFnshDttm = new Date();
        Date newBgnDttm = new Date(oldFnshDttm.getTime() + 1);
        DbFildM oldObj = this.getObjById(dbmsId, schmId, tabId, fildId);
        this.updateDelYn(dbmsId, schmId, tabId, fildId, oldFnshDttm);
        oldObj.setRecFnshDttm(newBgnDttm);
        oldObj.setRecBgnDttm(newBgnDttm);
        oldObj.setDelYn(Constants.FnlYn.YES);
        oldObj.setCretCvUserId(UserUtils.getLoginUser().getUserId());
        return this.save(oldObj);
    }

    @Override
    public List<DbFildMVo> getFildByCodeAttrColumn(DbFildQueryDto dto) {
        return dbFildMMapper.getFildByCodeAttrColumn(dto);
    }

    private boolean checkIsSave(String fildId, String oldNm, String newNm) {
        if (fildId.length() < oldNm.length()) {
            return true;
        }
        return !oldNm.equals(fildId.substring(0, oldNm.length())) || oldNm.equals(newNm);
    }

    private DbFildM getObjById(String dbmsId, String schmId, String tabId, String fildId) {
        QueryWrapper<DbFildM> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(DbFildM::getCvDbmsId, dbmsId)
                .eq(DbFildM::getCvSchmId, schmId)
                .eq(DbFildM::getCvDbTabId, tabId)
                .eq(DbFildM::getCvDbFildId, fildId)
                .eq(DbFildM::getRecFnshDttm, Constants.FNSHDTTM);
        return this.getOne(queryWrapper);
    }


    private boolean updateDelYn(String dbmsId, String schmId, String tabId, String fildId,Date oldFnshDttm) {
        if (StringUtils.isEmpty(dbmsId) && StringUtils.isEmpty(schmId)
                && StringUtils.isEmpty(tabId) && StringUtils.isEmpty(fildId)) {
            return false;
        }
        UpdateWrapper<DbFildM> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().set(DbFildM::getRecFnshDttm, oldFnshDttm)
                .set(DbFildM::getDelYn, Constants.DelYn.NO);
        updateWrapper.lambda().eq(DbFildM::getRecFnshDttm, Constants.FNSHDTTM);
        if (StringUtils.isNotEmpty(dbmsId)) {
            updateWrapper.lambda().eq(DbFildM::getCvDbmsId, dbmsId);
        }
        if (StringUtils.isNotEmpty(schmId)) {
            updateWrapper.lambda().eq(DbFildM::getCvSchmId, schmId);
        }
        if (StringUtils.isNotEmpty(tabId)) {
            updateWrapper.lambda().eq(DbFildM::getCvDbTabId, tabId);
        }
        if (StringUtils.isNotEmpty(fildId)) {
            updateWrapper.lambda().eq(DbFildM::getCvDbFildId, fildId);
        }
        return this.update(updateWrapper);
    }

    private List<DbFildM> getByDbmsId(String dbmsId) {
        QueryWrapper<DbFildM> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(DbFildM::getCvDbmsId, dbmsId)
                .eq(DbFildM::getRecFnshDttm, Constants.FNSHDTTM);
        return this.list(queryWrapper);
    }

    private List<DbFildM> getByDbmsIdAndSchmId(String dbmsId, String schmId) {
        QueryWrapper<DbFildM> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(DbFildM::getCvDbmsId, dbmsId)
                .eq(DbFildM::getCvSchmId, schmId)
                .eq(DbFildM::getRecFnshDttm, Constants.FNSHDTTM);
        return this.list(queryWrapper);
    }

    private List<DbFildM> getByDbmsIdAndSchmIdAndTabId(String dbmsId, String schmId, String tabId) {
        QueryWrapper<DbFildM> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(DbFildM::getCvDbmsId, dbmsId)
                .eq(DbFildM::getCvSchmId, schmId)
                .eq(DbFildM::getCvDbTabId, tabId)
                .eq(DbFildM::getRecFnshDttm, Constants.FNSHDTTM);
        return this.list(queryWrapper);
    }
}
