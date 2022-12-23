package com.codemanage.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codemanage.common.constants.Constants;
import com.codemanage.common.entity.BaseResult;
import com.codemanage.common.util.PasswordUtils;
import com.codemanage.common.util.UserUtils;
import com.codemanage.system.dto.request.DbmsQueryDto;
import com.codemanage.system.dto.request.DbmsSaveDto;
import com.codemanage.system.dto.response.DbmsMVo;
import com.codemanage.system.dto.response.DbmsTreeDto;
import com.codemanage.system.entity.DbmsM;
import com.codemanage.system.mapper.DbmsMMapper;
import com.codemanage.system.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * CVSO_数据库管理系统_主表 服务实现类
 * </p>
 *
 * @author hyh
 * @since 2022-06-16
 */
@Service
public class DbmsMServiceImpl extends ServiceImpl<DbmsMMapper, DbmsM> implements IDbmsMService {

    @Autowired
    private DbmsMMapper dbmsMMapper;

    @Autowired
    @Lazy
    private ISchmMService schmMService;

    @Autowired
    @Lazy
    private IDbTabMService tabMService;

    @Autowired
    @Lazy
    private IDbFildMService fildMService;

    @Autowired
    @Lazy
    private ISysSchmRService sysSchmRService;


    /**
     * 查询所有DBMS
     * @return
     */
    @Override
    public List<DbmsMVo> getAllDbms() {
        return dbmsMMapper.getAllDbms();
    }

    /**
     * 查询状态为Y的DBMS
     * @return
     */
    @Override
    public List<DbmsMVo> getDbmsList() {
        return dbmsMMapper.getDbmsList();
    }

    /**
     * 根据id查询DBMS
     * @param dbmsId
     * @param recBgnDttm
     * @return
     */
    @Override
    public DbmsMVo getDbmsById(String dbmsId, String recBgnDttm) {
        return dbmsMMapper.getDbmsById(dbmsId, recBgnDttm);
    }

    /**
     * DbmsQueryDto dto
     * @param dto
     * @return
     */
    @Override
    public List<DbmsTreeDto> getDbmsTree(DbmsQueryDto dto) {
        String parentType = dto.getType();
        List<DbmsTreeDto> list = new ArrayList<>();
        if (Constants.DbmsType.ROOT.equals(parentType)) {
            // 查询DB
            list = dbmsMMapper.getDbmsTreeList();
        } else if (Constants.DbmsType.DB.equals(parentType)) {
            // 查询SCHM
            list = schmMService.getDbmsTreeList(dto);
        } else if (Constants.DbmsType.SCHM.equals(parentType)) {
            // 查询TAB
            list = tabMService.getDbmsTreeList(dto);
        } else if (Constants.DbmsType.TAB.equals(parentType)) {
            // 查询FILD
            list = fildMService.getDbmsTreeList(dto);
        }
        return list;
    }

    /**
     * 校验DBMS名称
     * @param dbmsNm
     * @return
     */
    @Override
    public boolean checkDdmsNm(String dbmsNm) {
        int count = dbmsMMapper.checkDdmsNm(dbmsNm);
        if (count < 1) {
            return true;
        }
        return false;
    }

    /**
     * 获取排序序号
     * @return
     */
    @Override
    public Integer getSortSrno() {
        return dbmsMMapper.getSortSrno();
    }

    /**
     * 添加DBMS
     * @param dto
     * @return
     */
    @Override
    public BaseResult addDbms(DbmsSaveDto dto) {
        String cvDbmsId = dbmsMMapper.getDbmsId();
        DbmsM obj = new DbmsM();
        obj = dtoToObj(obj,dto);
        obj.setCvDbmsId(cvDbmsId);
        obj.setRecBgnDttm(new Date());
        obj.setRecFnshDttm(Constants.FNSHDTTM);
        obj.setFnlYn(Constants.FnlYn.YES);
        if (StringUtils.isNotEmpty(dto.getUserPwdEncd())) {
            obj.setUserPwdEncd(PasswordUtils.getSHA256Bytes(dto.getUserPwdEncd()));
        }
        obj.setCretCvUserId(UserUtils.getLoginUser().getUserId());
        if (obj.getUserPwdEncd() == null) {
            obj.setUserPwdEncd(Constants.DEFAULT_PASSWORD_DBMS.getBytes());
        }
        boolean flag = this.save(obj);
        if (flag) {
            return BaseResult.successMsg("保存成功!");
        }
        return BaseResult.failedMsg("保存失败!");
    }

    /**
     * 编辑DBMS
     * @param dto
     * @return
     */
    @Override
    public BaseResult editDbms(DbmsSaveDto dto) {
        Date oldFnshDttm = new Date();
        Date bgnDttm = new Date(oldFnshDttm.getTime() + 1);
        DbmsM obj = this.getObjById(dto.getCvDbmsId(), dto.getRecBgnDttm());
        this.updateFnlYn(oldFnshDttm, dto.getCvDbmsId());
        obj = dtoToObj(obj,dto);
        String oldPwd = PasswordUtils.byte2Hex(obj.getUserPwdEncd());
        if (!oldPwd.equals(dto.getUserPwdEncd())){
            obj.setUserPwdEncd(PasswordUtils.getSHA256Bytes(dto.getUserPwdEncd()));
        }
        obj.setRecBgnDttm(bgnDttm);
        obj.setRecFnshDttm(Constants.FNSHDTTM);
        obj.setFnlYn(Constants.FnlYn.YES);
        obj.setCretCvUserId(UserUtils.getLoginUser().getUserId());
        boolean flag = this.save(obj);
        if (flag) {
            return BaseResult.successMsg("编辑成功!");
        }
        return BaseResult.failedMsg("编辑失败!");
    }

    /**
     * 删除DBMS
     * @param dbmsId
     * @return
     */
    @Override
    public boolean delDbms(String dbmsId, String recBgnDttm) {
        Date oldFnshDttm = new Date();
        Date newBgnDttm = new Date(oldFnshDttm.getTime() + 1);
        DbmsM oldObj = this.getObjById(dbmsId, recBgnDttm);
        this.updateFnlYn(oldFnshDttm, dbmsId);
        oldObj.setRecFnshDttm(newBgnDttm);
        oldObj.setRecBgnDttm(newBgnDttm);
        oldObj.setFnlYn(Constants.FnlYn.YES);
        oldObj.setCretCvUserId(UserUtils.getLoginUser().getUserId());
        boolean flag = this.save(oldObj);
        if (flag) {
            // 删除schm
            schmMService.delByDbms(dbmsId, oldFnshDttm, newBgnDttm);
            // 删除tab
            tabMService.delByDbms(dbmsId, oldFnshDttm, newBgnDttm);
            // 删除fild
            fildMService.delByDbms(dbmsId, oldFnshDttm, newBgnDttm);
            // 删除sys_schm_r
            sysSchmRService.delByDbms(dbmsId, oldFnshDttm, newBgnDttm);
            return true;
        }
        return false;
    }


    private DbmsM dtoToObj(DbmsM obj, DbmsSaveDto dto) {
        obj.setDbmsNm(dto.getDbmsNm());
        obj.setDbmsIp(dto.getDbmsIp());
        obj.setDbmsPortVl(dto.getDbmsPortVl());
        obj.setCvDbmsBnacId(dto.getCvDbmsBnacId());
        obj.setCvSortSrno(dto.getCvSortSrno());
        obj.setDbmsExplTxt(dto.getDbmsExplTxt());
        return obj;
    }


    private boolean updateFnlYn(Date oldFnshDttm, String dbmsId) {
        UpdateWrapper<DbmsM> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().set(DbmsM::getRecFnshDttm, oldFnshDttm)
                .set(DbmsM::getFnlYn, Constants.FnlYn.NO);
        updateWrapper.lambda().eq(DbmsM::getCvDbmsId, dbmsId)
                .eq(DbmsM::getRecFnshDttm, Constants.FNSHDTTM);
        return this.update(updateWrapper);
    }

   /* private DbmsM getObjById(String dbmsId, String recBgnDttm) {
        QueryWrapper<DbmsM> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(DbmsM::getCvDbmsId, dbmsId)
                .eq(DbmsM::getRecFnshDttm, Constants.FNSHDTTM);
        return this.getOne(queryWrapper);
    }*/

    private DbmsM getObjById(String dbmsId, String recBgnDttm) {
        return dbmsMMapper.getObjById(dbmsId, recBgnDttm);
    }

 }
