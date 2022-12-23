package com.codemanage.code.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codemanage.code.dto.request.*;
import com.codemanage.code.dto.response.*;
import com.codemanage.code.entity.CdM;
import com.codemanage.code.entity.CdPptyM;
import com.codemanage.code.entity.CdR;
import com.codemanage.code.mapper.CdMMapper;
import com.codemanage.code.service.*;
import com.codemanage.common.constants.Constants;
import com.codemanage.common.entity.BaseResult;
import com.codemanage.common.util.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * CVCD_编码_主表 服务实现类
 * </p>
 *
 * @author hyh
 * @since 2022-06-02
 */
@Service
public class CdMServiceImpl extends ServiceImpl<CdMMapper, CdM> implements ICdMService {

    @Autowired
    private CdMMapper cdMMapper;

    @Lazy
    @Autowired
    private ICdRService cdRService;

    @Lazy
    @Autowired
    private ICdPptyMService pptyMService;

    @Lazy
    @Autowired
    private ICdPtyvMService ptyvMService;

    @Lazy
    @Autowired
    private ICdPptyRService pptyRService;

    @Lazy
    @Autowired
    private ITabCdBndRService tabCdBndRService;

    @Lazy
    @Autowired
    private ICdPptyFildBndRService pptyFildBndRService;

    /**
     * 分页查询编码
     *
     * @param dto
     * @return
     */
    @Override
    public IPage<CdMVo> getCdMByPage(CdMQueryDto dto) {
        IPage<CdMVo> page = new Page<>(dto.getCurrentPage(), dto.getPageSize());
        page = cdMMapper.listCdM(page, dto);
        return page;
    }

    /**
     * 根据id查询编码
     *
     * @param id
     * @return
     */
    @Override
    public CdMVo getCdMById(String id) {
        return cdMMapper.getCdMById(id);
    }

    /**
     * 添加编码
     *
     * @param dto
     * @return
     */
    @Override
    public BaseResult addCdM(CdMSaveDto dto) {
        Date newBgnDttm = new Date();
        CdM obj = new CdM();
        this.dtoToObj(obj, dto);
        String cvCdId = UUID.randomUUID().toString();
        obj.setCvCdId(cvCdId);
        obj.setRecBgnDttm(newBgnDttm);
        obj.setRecFnshDttm(Constants.FNSHDTTM);
        obj.setCretCvUserId(UserUtils.getLoginUser().getUserId());
        obj.setHlvCvCdId(dto.getHlvCvCdId());
        this.save(obj);
        if (StringUtils.isNotEmpty(dto.getHlvCvCdId())) {
            // 如果有上级编码，则保存关联关系
            if (StringUtils.isNotEmpty(dto.getHlvCvCdId())) {
                cdRService.saveCdR(dto.getHlvCvCdId(), cvCdId, newBgnDttm);
            }
        }
        return BaseResult.successMsg("添加成功！", obj);
    }


    /**
     * 编辑编码
     *
     * @param dto
     * @return
     */
    @Override
    public BaseResult editCdM(CdMSaveDto dto) {
        Date oldFnshDttm = new Date();
        Date newBgnDttm = new Date(oldFnshDttm.getTime() + 1);
        CdM oldObj = getObjById(dto.getCvCdId());
        this.updateFnlYn(dto.getCvCdId(), oldFnshDttm);
        this.dtoToObj(oldObj, dto);
        oldObj.setRecBgnDttm(newBgnDttm);
        oldObj.setRecFnshDttm(Constants.FNSHDTTM);
        oldObj.setFnlYn(Constants.FnlYn.YES);
        oldObj.setCretCvUserId(UserUtils.getLoginUser().getUserId());
        oldObj.setHlvCvCdId(dto.getHlvCvCdId());
        boolean flag = this.save(oldObj);
        if (flag) {
            // 如果上级编码修改，则修改关联关系
            CdR oldCdR = cdRService.getByLlbCvCdId(oldObj.getCvCdId());
            String hlvCvCdId = dto.getHlvCvCdId();
            if (oldCdR == null && StringUtils.isNotEmpty(hlvCvCdId)) {
                cdRService.saveCdR(dto.getHlvCvCdId(), oldObj.getCvCdId(), newBgnDttm);
            }
            if (oldCdR != null) {
                if (StringUtils.isEmpty(hlvCvCdId)) {
                    cdRService.delCdR(oldCdR, oldFnshDttm, newBgnDttm);
                } else if (!hlvCvCdId.equals(oldCdR.getHlvCvCdId())) {
                    cdRService.updateCdR(oldCdR, hlvCvCdId, oldFnshDttm, newBgnDttm);
                }
            }
            return BaseResult.successMsg("编辑成功！", oldObj);
        }
        return BaseResult.failedMsg("编辑失败！");
    }

    public boolean delCdM(String id, Date oldFnshDttm, Date newBgnDttm) {
        CdM oldObj = this.getObjById(id);
        boolean flag = this.updateFnlYn(id, oldFnshDttm);
        if (flag) {
            oldObj.setRecFnshDttm(newBgnDttm);
            oldObj.setRecBgnDttm(newBgnDttm);
            oldObj.setFnlYn(Constants.FnlYn.YES);
            oldObj.setCretCvUserId(UserUtils.getLoginUser().getUserId());
            return this.save(oldObj);
        }
        return false;
    }

    @Override
    public boolean delBatchCdM(List<String> ids) {
        Date oldFnshDttm = new Date();
        Date newBgnDttm = new Date(oldFnshDttm.getTime() + 1);
        for (String id : ids) {
            this.delCdM(id, oldFnshDttm, newBgnDttm);
        }
        return true;
    }

    /**
     * 查询编码属性列表
     *
     * @param dto
     * @return
     */
    @Override
    public List<CdPptyMVo> getCdPptyMList(CdPptyMQueryDto dto) {
        return pptyMService.getCdPptyMList(dto);
    }

    /**
     * 查询编码属性值
     *
     * @param dto
     * @return
     */
    @Override
    public List<Map<String, String>> getPtyvList(CdPptyMQueryDto dto) {
        return ptyvMService.getPtyvList(dto);
    }

    @Override
    public Map<String, Object> getCdPtyvList(CdPptyMQueryDto dto) {
        Map<String, Object> map = new HashMap<>();
        List<CdPptyM> list = pptyMService.getListByCdId(dto.getCvCdId());
        if (CollectionUtils.isNotEmpty(list)) {
            int count = list.size();
            Map<String, String> tableMap = pptyMService.getTableMap(count, dto);
            List<Map<String, String>> dataMap = ptyvMService.getDataListMap(count, dto);

            Map<String, String> tableAttrMap = new LinkedHashMap<>(count);
            Map<String, String> tableAttrIdMap = new LinkedHashMap<>(count);

            for (Map.Entry<String, String> entry : tableMap.entrySet()) {
                if (entry.getKey().startsWith("attrid")) {
                    tableAttrIdMap.put(entry.getKey().replace("attrid", "attr"), entry.getValue());
                } else {
                    tableAttrMap.put(entry.getKey(), entry.getValue());
                }
            }

            map.put("table", tableAttrMap);
            map.put("tableId", tableAttrIdMap);
            map.put("data", dataMap);
        }

        return map;
    }

    /**
     * 查询编码编码关系
     *
     * @param dto
     * @return
     */
    @Override
    public List<CodeCodeRelationVo> getCodeCodeRelation(CdPptyMQueryDto dto) {
        return cdMMapper.getCodeCodeRelation(dto);
    }

    @Override
    public List<CodeAttributeRelationVo> getCodeAttributeRelation(CdPptyMQueryDto dto) {
        return cdMMapper.getCodeAttributeRelation(dto);
    }


    @Override
    public Map<String, Object> getCodeValueRelation(CdPptyRQueryDto dto) {
        Map<String, Object> map = new HashMap<>();
        int count = pptyRService.getCount(dto);
        if (count > 0) {
            Map<String, String> tableMap = pptyRService.getTableMap(count, dto);
            List<Map<String, String>> dataMap = pptyRService.getDataListMap(count, dto);
            map.put("table", tableMap);
            map.put("data", dataMap);
        }

        return map;
    }

    /**
     * 查询编码表关系
     *
     * @param dto
     * @return
     */
    @Override
    public List<CodeTableRelationVo> getCodeTableRelation(CdPptyMQueryDto dto) {

        return tabCdBndRService.getCodeTableRelation(dto);
    }

    /**
     * 查询编码属性列关系
     *
     * @param dto
     * @return
     */
    @Override
    public List<CodeAttrColumnRelationVo> getCodeAttrColumnRelation(CodeAttrColumnRQueryDto dto) {
        return pptyFildBndRService.getCodeAttrColumnRelation(dto);
    }

    private boolean updateFnlYn(String cvCdId, Date oldFnshDttm) {
        UpdateWrapper<CdM> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().set(CdM::getRecFnshDttm, oldFnshDttm)
                .set(CdM::getFnlYn, Constants.FnlYn.NO);
        updateWrapper.lambda().eq(CdM::getCvCdId, cvCdId)
                .eq(CdM::getRecFnshDttm, Constants.FNSHDTTM);
        return this.update(updateWrapper);
    }


    private CdM getObjById(String cvCdId) {
        QueryWrapper<CdM> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(CdM::getCvCdId, cvCdId)
                .eq(CdM::getRecFnshDttm, Constants.FNSHDTTM);
        return this.getOne(queryWrapper);
    }

    private void dtoToObj(CdM obj, CdMSaveDto dto) {
        obj.setCdLgcNm(dto.getCdLgcNm());
        obj.setCvCdTpCd(dto.getCvCdTpCd());
        obj.setCdvlSaveYn(dto.getCdvlSaveYn());
        obj.setBlngCvSysId(dto.getBlngCvSysId());
        obj.setCvCdSphrId(dto.getCvCdSphrId());
        obj.setCvCdCatId(dto.getCvCdCatId());
        obj.setCvRefId(dto.getCvRefId());
        obj.setCdPhysNm(dto.getCdPhysNm());
        obj.setDataTpNm(dto.getDataTpNm());
        obj.setDataLen(dto.getDataLen());
        obj.setCdExplTxt(dto.getCdExplTxt());
        obj.setCvCdStCd(dto.getCvCdStCd());
    }
}
