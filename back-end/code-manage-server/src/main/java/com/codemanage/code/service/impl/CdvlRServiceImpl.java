package com.codemanage.code.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codemanage.code.dto.request.CdPptyMQueryDto;
import com.codemanage.code.dto.request.CdvlRAutoSaveDto;
import com.codemanage.code.dto.request.CdvlRQueryDto;
import com.codemanage.code.dto.request.CdvlRSaveDto;
import com.codemanage.code.dto.response.CdvlRVo;
import com.codemanage.code.entity.CdPptyM;
import com.codemanage.code.entity.CdR;
import com.codemanage.code.entity.CdvlR;
import com.codemanage.code.mapper.CdvlRMapper;
import com.codemanage.code.service.ICdMService;
import com.codemanage.code.service.ICdPtyvMService;
import com.codemanage.code.service.ICdRService;
import com.codemanage.code.service.ICdvlRService;
import com.codemanage.common.constants.Constants;
import com.codemanage.common.entity.BaseResult;
import com.codemanage.common.util.UserUtils;
import com.codemanage.system.entity.DbFildM;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * CVCD_编码值_关系表 服务实现类
 * </p>
 *
 * @author hyh
 * @since 2022-08-16
 */
@Service
public class CdvlRServiceImpl extends ServiceImpl<CdvlRMapper, CdvlR> implements ICdvlRService {

    @Autowired
    private ICdRService cdRService;

    @Autowired
    private ICdMService cdMService;

    @Autowired
    private ICdPtyvMService ptyvMService;

    @Autowired
    private CdvlRMapper cdvlRMapper;

    /**
     * 查询映射关系
     *
     * @param dto
     * @return
     */
    @Override
    public List<CdvlRVo> getCdvlR(CdvlRQueryDto dto) {
        return cdvlRMapper.getCdvlR(dto);
    }

    @Override
    public BaseResult addCdvlR(CdvlRSaveDto dto) {
        Date newBgnDttm = new Date();
        CdvlR obj = this.dtoToObj(dto, newBgnDttm);
        CdR cdR = this.cdRService.getById(dto.getHlvCvCdId(), dto.getLlvCvCdId());

        QueryWrapper<CdvlR> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(CdvlR::getHlvCvCdId, obj.getHlvCvCdId())
                .eq(CdvlR::getHlvCvCdvlId, obj.getHlvCvCdvlId())
                .eq(CdvlR::getLlvCvCdId, obj.getLlvCvCdId())
                .eq(CdvlR::getLlvCvCdvlId, obj.getLlvCvCdvlId())
                .eq(CdvlR::getRecFnshDttm, Constants.FNSHDTTM);

        if (dto.getLeftValue().equals("=100")) {
            obj.setRltsExplTxt("=100");
            obj.setHlvCvCdvlId("0");
        }
        else if(dto.getRightValue().equals("=100")) {
            obj.setRltsExplTxt("=100");
            obj.setLlvCvCdvlId("0");
        }

        obj.setSortSrno("1");
        obj.setCvCdvlRltsDstsCd("0100");
        if (cdR.getCvCdRltsCd().equals("0100")) {
            obj.setCvCdvlRltsDstsCd("0300");
        }

        if (this.count(queryWrapper) == 0) {
            this.save(obj);
        }
        return BaseResult.successMsg("映射成功!");
    }

    @Override
    public BaseResult addBatchCdvlR(CdvlRSaveDto dto) {
        CdPptyMQueryDto leftQuery = new CdPptyMQueryDto();
        leftQuery.setCvCdId(dto.getHlvCvCdId());
        Map<String, Object> leftMap = this.cdMService.getCdPtyvList(leftQuery);

        CdPptyMQueryDto rightQuery = new CdPptyMQueryDto();
        rightQuery.setCvCdId(dto.getLlvCvCdId());
        Map<String, Object> rightMap = this.cdMService.getCdPtyvList(rightQuery);

        List<Map<String, String>> leftData = (List<Map<String, String>>) leftMap.get("data");
        List<Map<String, String>> rightData = (List<Map<String, String>>) rightMap.get("data");

        if (dto.getCodeOrder() == 1) {
            int maxSize = Math.max(leftData.size(), rightData.size());
            for (int i = 0; i < maxSize; i++) {
                Map<String, String> left = i + 1 > leftData.size() ? null : leftData.get(i);
                Map<String, String> right = i + 1 > rightData.size() ? null : rightData.get(i);
                if (left == null || right == null) {
                    continue;
                }

                CdvlRSaveDto addDto = new CdvlRSaveDto();
                addDto.setHlvCvCdId(dto.getHlvCvCdId());
                addDto.setHlvCvCdvlId(left.get("cv_cdvl_id"));
                addDto.setLlvCvCdId(dto.getLlvCvCdId());
                addDto.setLlvCvCdvlId(right.get("cv_cdvl_id"));
                addDto.setLeftValue("");
                addDto.setRightValue("");

                this.addCdvlR(addDto);
            }
        } else {
            leftData.forEach(leftDataMap -> {
                rightData.forEach(rightDataMap -> {
                    List<Boolean> matches = new ArrayList<>();
                    leftDataMap.entrySet().forEach(leftDataMapEntry -> {
                        if (!leftDataMapEntry.getKey().startsWith("attr")) return;
                        if (leftDataMapEntry.getKey().startsWith("attrid")) return;

                        if (rightDataMap.get(leftDataMapEntry.getKey()).equals(leftDataMapEntry.getValue())) {
                            matches.add(true);
                        }
                    });

                    if (matches.stream().allMatch(item -> item)) {
                        CdvlRSaveDto addDto = new CdvlRSaveDto();
                        addDto.setHlvCvCdId(dto.getHlvCvCdId());
                        addDto.setHlvCvCdvlId(leftDataMap.get("cv_cdvl_id"));
                        addDto.setLlvCvCdId(dto.getLlvCvCdId());
                        addDto.setLlvCvCdvlId(rightDataMap.get("cv_cdvl_id"));
                        addDto.setLeftValue("");
                        addDto.setRightValue("");

                        this.addCdvlR(addDto);
                    }
                });
            });
        }

        return BaseResult.successMsg("自动映射成功!");
    }

    @Override
    public BaseResult delBatchCdvlR(List<CdvlRSaveDto> dtoList) {
        if (CollectionUtils.isNotEmpty(dtoList)) {
            Date oldFnshDttm = new Date();
            Date newBgnDttm = new Date(oldFnshDttm.getTime() + 1);
            String llvCvCdId = dtoList.get(0).getLlvCvCdId();
            String hlvCvCdId = dtoList.get(0).getHlvCvCdId();
            List<String> llvCvCdvlIdList = dtoList.stream().map(CdvlRSaveDto::getLlvCvCdvlId).collect(Collectors.toList());
            List<String> hlvCvCdvlIdList = dtoList.stream().map(CdvlRSaveDto::getHlvCvCdvlId).collect(Collectors.toList());
            List<CdvlR> oldList = this.getList(llvCvCdId, hlvCvCdId, llvCvCdvlIdList, hlvCvCdvlIdList);
            boolean flag = updateFnlYn(llvCvCdId, hlvCvCdId, llvCvCdvlIdList, hlvCvCdvlIdList, oldFnshDttm);
            if (flag) {
                String createUserId = UserUtils.getLoginUser().getUserId();
                for (CdvlR obj : oldList) {
                    obj.setRecBgnDttm(newBgnDttm);
                    obj.setRecFnshDttm(newBgnDttm);
                    obj.setCretCvUserId(createUserId);
                }
                this.saveBatch(oldList);
                return BaseResult.successMsg("删除成功!");
            }
            return BaseResult.failedMsg("删除失败!");
        }
        return BaseResult.failedMsg("请选择要删除的数据!");
    }

    @Override
    public boolean delByCdvlM(String cvCdId, List<String> cvCdvlIdList, Date oldFnshDttm, Date newBgnDttm) {
        QueryWrapper<CdvlR> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(CdvlR::getFnlYn, Constants.FnlYn.YES)
                .eq(CdvlR::getRecFnshDttm, Constants.FNSHDTTM)
                .and(q -> q.eq(CdvlR::getHlvCvCdId, cvCdId).or().eq(CdvlR::getLlvCvCdId, cvCdId))
                .and(q -> q.in(CdvlR::getLlvCvCdvlId, cvCdvlIdList).or().in(CdvlR::getHlvCvCdvlId, cvCdvlIdList));
        List<CdvlR> oldList = this.list(queryWrapper);

        UpdateWrapper<CdvlR> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().set(CdvlR::getFnlYn, Constants.FnlYn.NO)
                .set(CdvlR::getRecFnshDttm, oldFnshDttm);
        updateWrapper.lambda().eq(CdvlR::getFnlYn, Constants.FnlYn.YES)
                .eq(CdvlR::getRecFnshDttm, Constants.FNSHDTTM)
                .and(q -> q.eq(CdvlR::getHlvCvCdId, cvCdId).or().eq(CdvlR::getLlvCvCdId, cvCdId))
                .and(q -> q.in(CdvlR::getLlvCvCdvlId, cvCdvlIdList).or().in(CdvlR::getHlvCvCdvlId, cvCdvlIdList));
        boolean flag = this.update(updateWrapper);
        if (flag) {
            String userId = UserUtils.getLoginUser().getUserId();
            for (CdvlR oldObj : oldList) {
                oldObj.setRecFnshDttm(newBgnDttm);
                oldObj.setRecBgnDttm(newBgnDttm);
                oldObj.setCretCvUserId(userId);
            }
            return this.saveBatch(oldList);
        }

        return false;
    }


    private boolean updateFnlYn(String llvCvCdId, String hlvCvCdId, List<String> llvCvCdvlIdList,
                                List<String> hlvCvCdvlIdList, Date oldFnshDttm) {
        UpdateWrapper<CdvlR> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().set(CdvlR::getFnlYn, Constants.FnlYn.NO)
                .set(CdvlR::getRecFnshDttm, oldFnshDttm);
        updateWrapper.lambda().eq(CdvlR::getLlvCvCdId, llvCvCdId)
                .in(CdvlR::getLlvCvCdvlId, llvCvCdvlIdList)
                .eq(CdvlR::getHlvCvCdId, hlvCvCdId)
                .in(CdvlR::getHlvCvCdvlId, hlvCvCdvlIdList)
                .eq(CdvlR::getRecFnshDttm, Constants.FNSHDTTM);
        return this.update(updateWrapper);
    }

    private List<CdvlR> getList(String llvCvCdId, String hlvCvCdId, List<String> llvCvCdvlIdList, List<String> hlvCvCdvlIdList) {
        QueryWrapper<CdvlR> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(CdvlR::getLlvCvCdId, llvCvCdId)
                .in(CdvlR::getLlvCvCdvlId, llvCvCdvlIdList)
                .eq(CdvlR::getHlvCvCdId, hlvCvCdId)
                .in(CdvlR::getHlvCvCdvlId, hlvCvCdvlIdList)
                .eq(CdvlR::getRecFnshDttm, Constants.FNSHDTTM)
                .eq(CdvlR::getFnlYn, Constants.FnlYn.YES);
        return this.list(queryWrapper);
    }

    private CdvlR dtoToObj(CdvlRSaveDto dto, Date newBgnDttm) {
        CdvlR obj = new CdvlR();
        BeanUtils.copyProperties(dto, obj);
        obj.setRecBgnDttm(newBgnDttm);
        obj.setRecFnshDttm(Constants.FNSHDTTM);
        obj.setFnlYn(Constants.FnlYn.YES);
        obj.setCretCvUserId(UserUtils.getLoginUser().getUserId());
        return obj;
    }

    private List<CdvlR> dtoListToObjList(List<CdvlRSaveDto> dtoList) {
        List<CdvlR> objList = new ArrayList<>();
        Date newBgnDttm = new Date();
        for (CdvlRSaveDto dto : dtoList) {
            CdvlR obj = this.dtoToObj(dto, newBgnDttm);
            objList.add(obj);
        }
        return objList;
    }
}
