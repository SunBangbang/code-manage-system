package com.codemanage.code.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codemanage.code.dto.request.CdPptyMDelDto;
import com.codemanage.code.dto.request.CdPptyMQueryDto;
import com.codemanage.code.dto.request.CdPptyMSaveDto;
import com.codemanage.code.dto.response.CdPptyMVo;
import com.codemanage.code.entity.CdPptyM;
import com.codemanage.code.mapper.CdPptyMMapper;
import com.codemanage.code.service.ICdPptyFildBndRService;
import com.codemanage.code.service.ICdPptyMService;
import com.codemanage.code.service.ICdPptyRService;
import com.codemanage.common.constants.Constants;
import com.codemanage.common.entity.BaseResult;
import com.codemanage.common.util.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 * CVCD_编码属性_主表 服务实现类
 * </p>
 *
 * @author hyh
 * @since 2022-06-07
 */
@Service
@Slf4j
public class CdPptyMServiceImpl extends ServiceImpl<CdPptyMMapper, CdPptyM> implements ICdPptyMService {

    @Autowired
    private CdPptyMMapper pptyMMapper;

    @Lazy
    @Autowired
    private ICdPptyRService pptyRService;

    @Lazy
    @Autowired
    private ICdPptyFildBndRService pptyFildBndRService;

    /**
     * 查询编码属性列表
     * @param dto
     * @return
     */
    @Override
    public List<CdPptyMVo> getCdPptyMList(CdPptyMQueryDto dto) {
        return pptyMMapper.getCdPptyMList(dto);
    }

    /**
     * 添加编码属性
     * @param dto
     * @return
     */
    @Override
    public BaseResult addCdPptyM(CdPptyMSaveDto dto) {
        CdPptyM obj = new CdPptyM();
        this.dtoToObj(dto, obj);
        obj.setCvSortSrno(dto.getCvSortSrno());
        obj.setCvCdPptyId(UUID.randomUUID().toString());
        obj.setRecBgnDttm(new Date());
        obj.setRecFnshDttm(Constants.FNSHDTTM);
        obj.setCretCvUserId(UserUtils.getLoginUser().getUserId());
        boolean flag = this.save(obj);
        if (flag) {
            return BaseResult.successMsg("添加成功！");
        }
        return BaseResult.failedMsg("添加失败！");
    }

    /**
     * 编辑编码属性
     * @param dto
     * @return
     */
    @Override
    public BaseResult editCdPptyM(CdPptyMSaveDto dto) {
        Date oldFnshDttm = new Date();
        Date newBgnDttm = new Date(oldFnshDttm.getTime() + 1);
        CdPptyM obj = this.getObjById(dto.getCvCdId(), dto.getCvCdPptyId());
        this.dtoToObj(dto, obj);
        this.updateFnlYn(dto.getCvCdId(), dto.getCvCdPptyId(), oldFnshDttm);
        obj.setRecBgnDttm(newBgnDttm);
        obj.setRecFnshDttm(Constants.FNSHDTTM);
        obj.setFnlYn(Constants.FnlYn.YES);
        obj.setCretCvUserId(UserUtils.getLoginUser().getUserId());
        obj.setCvSortSrno(dto.getCvSortSrno());
        boolean flag = this.save(obj);
        if (flag) {
            return BaseResult.successMsg("编辑成功！");
        }
        return BaseResult.failedMsg("编辑失败！");
    }

    public boolean delCdPptyM(String cvCdId, String cvCdPptyId, Date oldFnshDttm, Date newBgnDttm) {

        CdPptyM oldObj = this.getObjById(cvCdId, cvCdPptyId);
        boolean flag = this.updateFnlYn(cvCdId, cvCdPptyId ,oldFnshDttm);
        if (flag) {
            oldObj.setRecFnshDttm(newBgnDttm);
            oldObj.setRecBgnDttm(newBgnDttm);
            oldObj.setFnlYn(Constants.FnlYn.YES);
            oldObj.setCretCvUserId(UserUtils.getLoginUser().getUserId());
            this.save(oldObj);
            // delete cvcd_cd_ppty_r
            pptyRService.delByPptyM(cvCdId, cvCdPptyId, oldFnshDttm, newBgnDttm);
            // delete cvcd_cd_ppty_fild_bnd_r
            pptyFildBndRService.delByPptyM(cvCdId, cvCdPptyId, oldFnshDttm, newBgnDttm);
            // TODO delete cvcd_cd_ptyv_m

        }

        return false;
    }

    @Override
    public boolean delBatchCdPptyM(CdPptyMDelDto delDto) {
        Date oldFnshDttm = new Date();
        Date newBgnDttm = new Date(oldFnshDttm.getTime() + 1);
        List<String> ids = delDto.getCvCdPptyIdList();
        for (String id : ids) {
            this.delCdPptyM(delDto.getCvCdId(), id, oldFnshDttm, newBgnDttm);
        }
        return true;
    }

    /**
     * 删除编码属性
     * @param id
     * @return
     */
    @Override
    public boolean delCdPptyMById(String id) {
        return this.removeById(id);
    }

    /**
     * 批量删除编码属性
     * @param ids
     * @return
     */
    @Override
    public boolean delBatchCdPptyMByIds(List<String> ids) {
        return this.removeBatchByIds(ids);
    }

    @Override
    public Map<String, String> getTableMap(int count, CdPptyMQueryDto dto) {
        StringBuffer tableSqlSb = new StringBuffer();
//        tableSqlSb.append(" select  cv_cd_id");
        tableSqlSb.append(" select");
        for (int i = 1; i < count + 1; i++) {
//            tableSqlSb.append(" ,string_agg(case when rank_no = " + i + " then cd_ppty_nm end,'') attr" + i);
            tableSqlSb.append(" string_agg(case when rank_no = " + i + " then cd_ppty_nm end,'') attr" + i + ",");
            tableSqlSb.append(" string_agg(case when rank_no = " + i + " then cv_cd_ppty_id end,'') attrId" + i);
            if (i < count) {
                tableSqlSb.append(",");
            }
        }
        tableSqlSb.append(" from (")
                .append(" select rank() over (order by cv_sort_srno,cv_cd_ppty_id) rank_no, * ")
                .append(" from cvcd_cd_ppty_m where  cv_cd_id = '" + dto.getCvCdId() + "' ")
                .append(" and rec_fnsh_dttm = '9999-12-31 00:00:00.000000' ")
                .append(" ) a ")
                .append(" group by cv_cd_id ");

        String tableSql = tableSqlSb.toString();
        log.info("编码值表头SQL: {}", tableSql);
        Map<String, String> tableMap = pptyMMapper.getTableMap(tableSql);
        return tableMap;
    }

    @Override
    public List<CdPptyM> getListByCdId(String cvCdId) {
        QueryWrapper<CdPptyM> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(CdPptyM::getCvCdId, cvCdId)
                .eq(CdPptyM::getRecFnshDttm, Constants.FNSHDTTM);
        return this.list(queryWrapper);
    }

    public CdPptyM getObjById(String cvcdId, String pptyId) {
        QueryWrapper<CdPptyM> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(CdPptyM::getCvCdPptyId, pptyId)
                .eq(CdPptyM::getCvCdId, cvcdId)
                .eq(CdPptyM::getRecFnshDttm, Constants.FNSHDTTM);
        return this.getOne(queryWrapper);
    }

    private boolean updateFnlYn(String cvcdId, String pptyId, Date oldFnshDttm) {
        UpdateWrapper<CdPptyM> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().set(CdPptyM::getRecFnshDttm, oldFnshDttm)
                .set(CdPptyM::getFnlYn, Constants.FnlYn.NO);
        updateWrapper.lambda().eq(CdPptyM::getCvCdPptyId, pptyId)
                .eq(CdPptyM::getCvCdId, cvcdId)
                .eq(CdPptyM::getRecFnshDttm, Constants.FNSHDTTM);
        return this.update(updateWrapper);
    }

    private CdPptyM dtoToObj(CdPptyMSaveDto dto, CdPptyM obj) {
        obj.setCvCdId(dto.getCvCdId());
        obj.setCvCdSphrId(dto.getCvCdSphrId());
        obj.setCdPptyNm(dto.getCdPptyNm());
        obj.setPkYn(dto.getPkYn());
        obj.setNullYn(dto.getNullYn());
        obj.setDataTpNm(dto.getDataTpNm());
        obj.setDataLen(dto.getDataLen());
        obj.setCvSortSrno(dto.getCvSortSrno());
        return obj;
    }

}
