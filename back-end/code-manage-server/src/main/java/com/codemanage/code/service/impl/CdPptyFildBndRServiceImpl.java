package com.codemanage.code.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codemanage.code.dto.request.CdPptyFildBndRSaveDto;
import com.codemanage.code.dto.request.CdPptyMQueryDto;
import com.codemanage.code.dto.request.CodeAttrColumnRQueryDto;
import com.codemanage.code.dto.response.CdPptyFildBndRVo;
import com.codemanage.code.dto.response.CdPptyMVo;
import com.codemanage.code.dto.response.CodeAttrColumnRelationVo;
import com.codemanage.code.entity.CdPptyFildBndR;
import com.codemanage.code.entity.CdPptyM;
import com.codemanage.code.mapper.CdPptyFildBndRMapper;
import com.codemanage.code.service.ICdPptyFildBndRService;
import com.codemanage.common.constants.Constants;
import com.codemanage.common.entity.BaseResult;
import com.codemanage.common.util.UserUtils;
import com.codemanage.system.dto.request.DbFildMQueryDto;
import com.codemanage.system.entity.DbFildM;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * CVCD_编码属性字段绑定_关系表 服务实现类
 * </p>
 *
 * @author hyh
 * @since 2022-08-17
 */
@Service
public class CdPptyFildBndRServiceImpl extends ServiceImpl<CdPptyFildBndRMapper, CdPptyFildBndR> implements ICdPptyFildBndRService {

    /**
     * 查询编码属性列关系
     *
     * @param dto
     * @return
     */
    @Override
    public List<CodeAttrColumnRelationVo> getCodeAttrColumnRelation(CodeAttrColumnRQueryDto dto) {
        return baseMapper.getCodeAttrColumnRelation(dto);
    }

    @Override
    public List<CdPptyFildBndRVo> getPptyFildBndR(CodeAttrColumnRQueryDto dto) {
        return baseMapper.getPptyFildBndR(dto);
    }

    @Override
    public BaseResult addPptyFildBndR(CdPptyFildBndRSaveDto dto) {
        Date newBgnDttm = new Date();
        CdPptyFildBndR obj = this.dtoToObj(dto, newBgnDttm);
        obj.setCvSortSrno(String.valueOf(dto.getCvSortSrno()));

        if (dto.getLeftValue().equals("=100")) {
            obj.setRltsExplTxt("=100");
            obj.setCvCdPptyId("=100");
        } else if (dto.getRightValue().equals("=100")) {
            obj.setRltsExplTxt("=100");
            obj.setCvDbFildId("=100");
        }

        QueryWrapper<CdPptyFildBndR> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(CdPptyFildBndR::getCvCdId, obj.getCvCdId())
                .eq(CdPptyFildBndR::getCvCdPptyId, obj.getCvCdPptyId())
                .eq(CdPptyFildBndR::getCvDbmsId, obj.getCvDbmsId())
                .eq(CdPptyFildBndR::getCvDbFildId, obj.getCvDbFildId())
                .eq(CdPptyFildBndR::getCvDbTabId, obj.getCvDbTabId())
                .eq(CdPptyFildBndR::getRecFnshDttm, Constants.FNSHDTTM);

        if (this.count(queryWrapper) == 0)
            this.save(obj);
        return BaseResult.successMsg("映射成功!");
    }

    @Override
    public BaseResult addBatchPptyFildBndR(CdPptyFildBndRSaveDto dto) {
        CdPptyMQueryDto leftQuery = new CdPptyMQueryDto();
        leftQuery.setCvCdId(dto.getCvCdId());
        List<CdPptyM> leftList = this.getMappingLeftData(leftQuery);

        DbFildMQueryDto rightQuery = new DbFildMQueryDto();
        rightQuery.setCvSchmId(dto.getCvSchmId());
        rightQuery.setCvDbTabId(dto.getCvDbTabId());
        rightQuery.setCvDbmsId(dto.getCvDbmsId());
        List<DbFildM> rightList = this.getMappingRightData(rightQuery);

        int maxSize = Math.max(leftList.size(), rightList.size());
        if (dto.getCodeOrder() == 1) {
            for (int i = 0; i < maxSize; i++) {
                CdPptyM left = i + 1 > leftList.size() ? null : leftList.get(i);
                DbFildM right = i + 1 > rightList.size() ? null : rightList.get(i);
                if (left == null || right == null) {
                    continue;
                }

                CdPptyFildBndRSaveDto addDto = new CdPptyFildBndRSaveDto();
                addDto.setCvCdId(left.getCvCdId());
                addDto.setCvCdPptyId(left.getCvCdPptyId());
                addDto.setCvDbmsId(right.getCvDbmsId());
                addDto.setCvSchmId(right.getCvSchmId());
                addDto.setCvDbTabId(right.getCvDbTabId());
                addDto.setCvDbFildId(right.getCvDbFildId());
                addDto.setLeftValue("");
                addDto.setRightValue("");

                this.addPptyFildBndR(addDto);
            }
        } else {
            leftList.forEach(forItem -> {
                Optional<DbFildM> foundItem = rightList.stream()
                        .filter(findItem -> findItem.getDbFildNm().equals(forItem.getCdPptyNm()))
                        .findFirst();

                if(!foundItem.isPresent()) {
                    return;
                }

                CdPptyFildBndRSaveDto addDto = new CdPptyFildBndRSaveDto();
                addDto.setCvCdId(forItem.getCvCdId());
                addDto.setCvCdPptyId(forItem.getCvCdPptyId());
                addDto.setCvDbmsId(foundItem.get().getCvDbmsId());
                addDto.setCvSchmId(foundItem.get().getCvSchmId());
                addDto.setCvDbTabId(foundItem.get().getCvDbTabId());
                addDto.setCvDbFildId(foundItem.get().getCvDbFildId());
                addDto.setLeftValue("");
                addDto.setRightValue("");

                this.addPptyFildBndR(addDto);
            });
        }

        return BaseResult.successMsg("自动映射成功!");
    }

    @Override
    public BaseResult delBatchPptyFildBndR(List<CdPptyFildBndRSaveDto> dtoList) {
        if (CollectionUtils.isNotEmpty(dtoList)) {
            Date oldFnshDttm = new Date();
            Date newBgnDttm = new Date(oldFnshDttm.getTime() + 1);
            for (CdPptyFildBndRSaveDto dto : dtoList) {
                this.delPptyFildBndR(dto, oldFnshDttm, newBgnDttm);
            }
        }
        return BaseResult.successMsg("删除数据成功!");
    }

    @Override
    public boolean delByPptyM(String cvCdId, String cvCdPptyId, Date oldFnshDttm, Date newBgnDttm) {
        QueryWrapper<CdPptyFildBndR> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(CdPptyFildBndR::getCvCdId, cvCdId)
                .eq(CdPptyFildBndR::getCvCdPptyId, cvCdPptyId)
                .eq(CdPptyFildBndR::getRecFnshDttm, Constants.FNSHDTTM);
        List<CdPptyFildBndR> oldList = this.list(queryWrapper);
        if (CollectionUtils.isNotEmpty(oldList)) {
            UpdateWrapper<CdPptyFildBndR> updateWrapper = new UpdateWrapper<>();
            updateWrapper.lambda().set(CdPptyFildBndR::getFnlYn, Constants.FnlYn.NO)
                    .set(CdPptyFildBndR::getRecFnshDttm, oldFnshDttm);
            updateWrapper.lambda().eq(CdPptyFildBndR::getCvCdId, cvCdId)
                    .eq(CdPptyFildBndR::getCvCdPptyId, cvCdPptyId)
                    .eq(CdPptyFildBndR::getRecFnshDttm, Constants.FNSHDTTM);
            boolean flag = this.update(updateWrapper);
            if (flag) {
                String userId = UserUtils.getLoginUser().getUserId();
                for (CdPptyFildBndR oldObj : oldList) {
                    oldObj.setRecBgnDttm(newBgnDttm);
                    oldObj.setRecFnshDttm(newBgnDttm);
                    oldObj.setFnlYn(Constants.FnlYn.YES);
                    oldObj.setCretCvUserId(userId);
                }
                return this.saveBatch(oldList);
            }
        }
        return false;
    }


    private boolean delPptyFildBndR(CdPptyFildBndRSaveDto dto, Date oldFnshDttm, Date newBgnDttm) {
        CdPptyFildBndR oldObj = this.getObj(dto);
        boolean flag = this.updateFnlYn(dto, oldFnshDttm);
        if (flag) {
            oldObj.setRecBgnDttm(newBgnDttm);
            oldObj.setRecFnshDttm(newBgnDttm);
            oldObj.setFnlYn(Constants.FnlYn.YES);
            oldObj.setCretCvUserId(UserUtils.getLoginUser().getUserId());
            return this.save(oldObj);
        }
        return false;
    }

    private boolean updateFnlYn(CdPptyFildBndRSaveDto dto, Date oldFnshDttm) {
        UpdateWrapper<CdPptyFildBndR> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().set(CdPptyFildBndR::getFnlYn, Constants.FnlYn.NO)
                .set(CdPptyFildBndR::getRecFnshDttm, oldFnshDttm);
        updateWrapper.lambda().eq(CdPptyFildBndR::getCvCdId, dto.getCvCdId())
                .eq(CdPptyFildBndR::getCvCdPptyId, dto.getCvCdPptyId())
                .eq(CdPptyFildBndR::getCvDbmsId, dto.getCvDbmsId())
                .eq(CdPptyFildBndR::getCvSchmId, dto.getCvSchmId())
                .eq(CdPptyFildBndR::getCvDbTabId, dto.getCvDbTabId())
                .eq(CdPptyFildBndR::getCvDbFildId, dto.getCvDbFildId())
                .eq(CdPptyFildBndR::getRecFnshDttm, Constants.FNSHDTTM);
        return this.update(updateWrapper);
    }

    private CdPptyFildBndR getObj(CdPptyFildBndRSaveDto dto) {
        QueryWrapper<CdPptyFildBndR> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(CdPptyFildBndR::getCvCdId, dto.getCvCdId())
                .eq(CdPptyFildBndR::getCvCdPptyId, dto.getCvCdPptyId())
                .eq(CdPptyFildBndR::getCvDbmsId, dto.getCvDbmsId())
                .eq(CdPptyFildBndR::getCvSchmId, dto.getCvSchmId())
                .eq(CdPptyFildBndR::getCvDbTabId, dto.getCvDbTabId())
                .eq(CdPptyFildBndR::getCvDbFildId, dto.getCvDbFildId())
                .eq(CdPptyFildBndR::getRecFnshDttm, Constants.FNSHDTTM);
        return this.getOne(queryWrapper);
    }

    private List<CdPptyFildBndR> dtoListToObjList(List<CdPptyFildBndRSaveDto> dtoList) {
        List<CdPptyFildBndR> objList = new ArrayList<>();
        Date newBgnDttm = new Date();
        for (CdPptyFildBndRSaveDto dto : dtoList) {
            CdPptyFildBndR obj = this.dtoToObj(dto, newBgnDttm);
            objList.add(obj);
        }
        return objList;
    }


    private CdPptyFildBndR dtoToObj(CdPptyFildBndRSaveDto dto, Date newBgnDttm) {
        CdPptyFildBndR obj = new CdPptyFildBndR();
        BeanUtils.copyProperties(dto, obj);
        obj.setRecBgnDttm(newBgnDttm);
        obj.setRecFnshDttm(Constants.FNSHDTTM);
        obj.setFnlYn(Constants.FnlYn.YES);
        obj.setCretCvUserId(UserUtils.getLoginUser().getUserId());
        return obj;
    }

    public List<CdPptyM> getMappingLeftData(CdPptyMQueryDto dto) {
        return this.baseMapper.getMappingLeftData(dto);
    }

    public List<DbFildM> getMappingRightData(DbFildMQueryDto dto) {
        return this.baseMapper.getMappingRightData(dto);
    }
}
