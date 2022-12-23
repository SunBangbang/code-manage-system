package com.codemanage.code.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codemanage.code.dto.request.CdRSaveDto;
import com.codemanage.code.entity.CdR;
import com.codemanage.code.mapper.CdRMapper;
import com.codemanage.code.service.ICdRService;
import com.codemanage.common.constants.Constants;
import com.codemanage.common.entity.BaseResult;
import com.codemanage.common.util.UserUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * CVCD_编码_关系表 服务实现类
 * </p>
 *
 * @author hyh
 * @since 2022-06-07
 */
@Service
public class CdRServiceImpl extends ServiceImpl<CdRMapper, CdR> implements ICdRService {


    /**
     * 保存关联关系
     *
     * @param hlvCvCdId
     * @param cvCdId
     * @param newBgnDttm
     * @return
     */
    @Override
    public boolean saveCdR(String hlvCvCdId, String cvCdId, Date newBgnDttm) {
        CdR obj = new CdR();
        obj.setHlvCvCdId(hlvCvCdId);
        obj.setLlvCvCdId(cvCdId);
        obj.setCvCdRltsCd("0100");
        obj.setRecBgnDttm(newBgnDttm);
        obj.setRecFnshDttm(Constants.FNSHDTTM);
        obj.setCretCvUserId(UserUtils.getLoginUser().getUserId());
        return this.save(obj);
    }

    @Override
    public CdR getByLlbCvCdId(String llvCvCdId) {
        QueryWrapper<CdR> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(CdR::getLlvCvCdId, llvCvCdId)
                .eq(CdR::getRecFnshDttm, Constants.FNSHDTTM);
        return this.getOne(queryWrapper);
    }

    @Override
    public boolean delCdR(CdR oldCdR, Date oldFnshDttm, Date newBgnDttm) {
        boolean flag = this.updateFnlYn(oldCdR, oldFnshDttm);
        if (flag) {
            oldCdR.setRecBgnDttm(newBgnDttm);
            oldCdR.setRecFnshDttm(newBgnDttm);
            oldCdR.setFnlYn(Constants.FnlYn.YES);
            oldCdR.setCretCvUserId(UserUtils.getLoginUser().getUserId());
            return this.save(oldCdR);
        }
        return false;
    }

    @Override
    public boolean updateCdR(CdR oldCdR, String hlvCvCdId, Date oldFnshDttm, Date newBgnDttm) {
        boolean flag = this.updateFnlYn(oldCdR, oldFnshDttm);
        if (flag) {
            oldCdR.setHlvCvCdId(hlvCvCdId);
            oldCdR.setRecBgnDttm(newBgnDttm);
            oldCdR.setRecFnshDttm(Constants.FNSHDTTM);
            oldCdR.setFnlYn(Constants.FnlYn.YES);
            oldCdR.setCretCvUserId(UserUtils.getLoginUser().getUserId());
            return this.save(oldCdR);
        }
        return false;
    }

    @Override
    public BaseResult addCdR(CdRSaveDto dto) {
        QueryWrapper<CdR> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(CdR::getHlvCvCdId, dto.getHlvCvCdId())
                .eq(CdR::getLlvCvCdId, dto.getLlvCvCdId())
                .eq(CdR::getRecFnshDttm, Constants.FNSHDTTM);
        CdR obj = this.getOne(queryWrapper);
        if (obj == null) {
            obj = new CdR();
            BeanUtils.copyProperties(dto, obj);
            obj.setFnlYn(Constants.FnlYn.YES);
            obj.setRecBgnDttm(new Date());
            obj.setRecFnshDttm(Constants.FNSHDTTM);
            obj.setCretCvUserId(UserUtils.getLoginUser().getUserId());
            this.save(obj);
        }
        return BaseResult.successMsg("添加成功");
    }

    @Override
    public BaseResult delCdR(List<CdRSaveDto> dtoList) {
        for (CdRSaveDto dto : dtoList) {
            QueryWrapper<CdR> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda()
                    .eq(CdR::getHlvCvCdId, dto.getHlvCvCdId())
                    .eq(CdR::getLlvCvCdId, dto.getLlvCvCdId())
                    .eq(CdR::getRecFnshDttm, Constants.FNSHDTTM);

            CdR delCdR = this.getOne(queryWrapper);
            if (delCdR == null) {
                continue;
            }

            delCdR.setFnlYn("N");
            delCdR.setRecFnshDttm(new Date());
            this.update(delCdR, queryWrapper);

            delCdR.setFnlYn("Y");
            delCdR.setRecBgnDttm(new Date(delCdR.getRecFnshDttm().getTime() + 1));
            delCdR.setRecFnshDttm(delCdR.getRecBgnDttm());
            delCdR.setCretCvUserId(UserUtils.getLoginUser().getUserId());
            this.save(delCdR);
        }
        return BaseResult.successMsg("删除完成!");
    }

    private boolean updateFnlYn(CdR oldCdR, Date oldFnshDttm) {
        UpdateWrapper<CdR> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().set(CdR::getRecFnshDttm, oldFnshDttm)
                .set(CdR::getFnlYn, Constants.FnlYn.NO);
        updateWrapper.lambda().eq(CdR::getHlvCvCdId, oldCdR.getHlvCvCdId())
                .eq(CdR::getLlvCvCdId, oldCdR.getLlvCvCdId())
                .eq(CdR::getRecFnshDttm, Constants.FNSHDTTM);
        return this.update(updateWrapper);
    }

    public CdR getById(String hlvCvCdId, String llvCvCdId) {
        QueryWrapper<CdR> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(CdR::getHlvCvCdId, hlvCvCdId)
                .eq(CdR::getLlvCvCdId, llvCvCdId)
                .eq(CdR::getRecFnshDttm, Constants.FNSHDTTM);
        return this.getOne(queryWrapper);
    }
}
