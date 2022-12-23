package com.codemanage.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codemanage.common.constants.Constants;
import com.codemanage.common.entity.BaseResult;
import com.codemanage.common.util.UserUtils;
import com.codemanage.system.dto.request.DbmsQueryDto;
import com.codemanage.system.dto.request.SchmMQueryDto;
import com.codemanage.system.dto.request.SchmMSaveDto;
import com.codemanage.system.dto.response.DbmsTreeDto;
import com.codemanage.system.dto.response.SchmMVo;
import com.codemanage.system.entity.SchmM;
import com.codemanage.system.mapper.SchmMMapper;
import com.codemanage.system.service.IDbFildMService;
import com.codemanage.system.service.IDbTabMService;
import com.codemanage.system.service.ISchmMService;
import com.codemanage.system.service.ISysSchmRService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * CVSO_数据库模式_主表 服务实现类
 * </p>
 *
 * @author hyh
 * @since 2022-06-16
 */
@Service
public class SchmMServiceImpl extends ServiceImpl<SchmMMapper, SchmM> implements ISchmMService {

    @Autowired
    private SchmMMapper schmMMapper;

    @Autowired
    private ISysSchmRService sysSchmRService;

    @Autowired
    @Lazy
    private IDbTabMService tabMService;

    @Autowired
    @Lazy
    private IDbFildMService fildMService;

    /**
     * 根据dbmsId查询Schm
     * @param dbmsId
     * @return
     */
    @Override
    public List<SchmMVo> getSchmByDbmsId(String dbmsId) {
        return schmMMapper.getSchmByDbmsId(dbmsId);
    }

    /**
     * 根据条件查询Schm
     * @param dto
     * @return
     */
    @Override
    public List<SchmMVo> getSchmByCondition(SchmMQueryDto dto) {
        return schmMMapper.getSchmByCondition(dto);
    }

    /**
     * 根据id查询模式
     * @param dto
     * @return
     */
    @Override
    public SchmMVo getSchmById(SchmMQueryDto dto) {
        return schmMMapper.getSchmById(dto);
    }

    /**
     * 校验schm名称
     * @param dto
     * @return
     */
    @Override
    public boolean checkSchmNm(SchmMQueryDto dto) {
        int count = schmMMapper.checkSchmNm(dto);
        if (count < 1) {
            return true;
        }
        return false;
    }

    @Override
    public List<DbmsTreeDto> getDbmsTreeList(DbmsQueryDto dto) {
        return schmMMapper.getDbmsTreeList(dto);
    }

    /**
     * 删除dbms时需删除的schm
     * @param dbmsId
     * @param oldFnshDttm
     * @param newBgnDttm
     * @return
     */
    @Override
    public boolean delByDbms(String dbmsId, Date oldFnshDttm, Date newBgnDttm) {
        List<SchmM> oldList = this.getByDbmsId(dbmsId);
        boolean flag = this.updateFnlYn(dbmsId, null, oldFnshDttm);
        if (flag) {
            String createUserId = UserUtils.getLoginUser().getUserId();
            for (SchmM obj : oldList) {
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
     * 获取排序序号
     * @param dbmsId
     * @return
     */
    @Override
    public Integer getSortSrno(String dbmsId) {
        return schmMMapper.getSortSrno(dbmsId);
    }

    @Override
    public BaseResult addSchm(SchmMSaveDto dto) {
        String schmId = schmMMapper.getSchmId();
        SchmM obj = new SchmM();
        obj.setCvDbmsId(dto.getCvDbmsId());
        obj.setCvSchmId(schmId);
        obj.setSchmNm(dto.getSchmNm());
        obj.setCvSortSrno(dto.getCvSortSrno());
        obj.setSchmExplTxt(dto.getSchmExplTxt());
        obj.setRecFnshDttm(Constants.FNSHDTTM);
        obj.setRecBgnDttm(new Date());
        obj.setCretCvUserId(UserUtils.getLoginUser().getUserId());
        boolean flag = this.save(obj);
        if (flag) {
            return BaseResult.successMsg("保存成功!");
        }
        return BaseResult.failedMsg("保存失败!");
    }

    /**
     * 编辑弹窗-查询详情
     * @param dto
     * @return
     */
    @Override
    public SchmMVo getSchmEditById(SchmMQueryDto dto) {
        return schmMMapper.getSchmEditById(dto);
    }

    @Override
    public BaseResult editSchm(SchmMSaveDto dto) {
        String dbmsId = dto.getCvDbmsId();
        String schmId = dto.getCvSchmId();
        if (StringUtils.isNotEmpty(dbmsId) && StringUtils.isNotEmpty(schmId)) {
            SchmM oldObj = this.getObjById(dbmsId, schmId);
            boolean checkFlag = this.checkIsSave(schmId, oldObj.getSchmNm(), dto.getSchmNm());
            if (checkFlag) {
                Date oldFnshDttm = new Date();
                Date bgnDttm = new Date(oldFnshDttm.getTime() + 1);
                this.updateFnlYn(dbmsId, schmId, oldFnshDttm);
                oldObj.setRecFnshDttm(Constants.FNSHDTTM);
                oldObj.setRecBgnDttm(bgnDttm);
                oldObj.setSchmNm(dto.getSchmNm());
                oldObj.setSchmExplTxt(dto.getSchmExplTxt());
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

    @Override
    public boolean delSchm(SchmMQueryDto dto) {
        String dbmsId = dto.getCvDbmsId();
        String schmId = dto.getCvSchmId();
        Date oldFnshDttm = new Date();
        Date newBgnDttm = new Date(oldFnshDttm.getTime() + 1);
        SchmM oldObj = this.getObjById(dbmsId, schmId);
        this.updateFnlYn(dbmsId, schmId, oldFnshDttm);
        oldObj.setRecFnshDttm(newBgnDttm);
        oldObj.setRecBgnDttm(newBgnDttm);
        oldObj.setFnlYn(Constants.FnlYn.YES);
        oldObj.setCretCvUserId(UserUtils.getLoginUser().getUserId());
        boolean flag = this.save(oldObj);
        if (flag) {
            // 删除tab
            tabMService.delBySchm(dbmsId, schmId, oldFnshDttm, newBgnDttm);
            // 删除fild
            fildMService.delBySchm(dbmsId, schmId, oldFnshDttm, newBgnDttm);
            // 删除sys_schm_r
            sysSchmRService.delBySchm(dbmsId, schmId, oldFnshDttm, newBgnDttm);
            return true;
        }
        return false;
    }


    private boolean checkIsSave(String schmId, String oldNm, String newNm) {
        if (schmId.length() < oldNm.length()) {
            return true;
        }
        return !oldNm.equals(schmId.substring(0, oldNm.length())) || oldNm.equals(newNm);
    }


    private boolean updateFnlYn(String dbmsId, String schmId, Date oldFnshDttm) {
        if (StringUtils.isEmpty(dbmsId) && StringUtils.isEmpty(schmId)) {
            return false;
        }
        UpdateWrapper<SchmM> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().set(SchmM::getRecFnshDttm, oldFnshDttm)
                .set(SchmM::getFnlYn, Constants.FnlYn.NO);
        updateWrapper.lambda().eq(SchmM::getRecFnshDttm, Constants.FNSHDTTM);
        if (StringUtils.isNotEmpty(dbmsId)) {
            updateWrapper.lambda().eq(SchmM::getCvDbmsId, dbmsId);
        }
        if (StringUtils.isNotEmpty(schmId)) {
            updateWrapper.lambda().eq(SchmM::getCvSchmId, schmId);
        }
        return this.update(updateWrapper);
    }


    private List<SchmM> getByDbmsId(String dbmsId) {
        QueryWrapper<SchmM> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SchmM::getCvDbmsId, dbmsId)
                .eq(SchmM::getRecFnshDttm, Constants.FNSHDTTM);
        return this.list(queryWrapper);
    }

    private SchmM getObjById(String dbmsId, String schmId) {
        QueryWrapper<SchmM> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SchmM::getCvDbmsId, dbmsId)
                .eq(SchmM::getCvSchmId, schmId)
                .eq(SchmM::getRecFnshDttm, Constants.FNSHDTTM);
        return this.getOne(queryWrapper);
    }
}
