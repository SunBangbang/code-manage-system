package com.codemanage.code.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codemanage.code.dto.request.CdPptyMQueryDto;
import com.codemanage.code.dto.response.CdPtyvMTempVo;
import com.codemanage.code.entity.CdPtyvM;
import com.codemanage.code.mapper.CdPtyvMMapper;
import com.codemanage.code.service.ICdPtyvMService;
import com.codemanage.common.constants.Constants;
import com.codemanage.common.util.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * CVCD_编码属性值_主表 服务实现类
 * </p>
 *
 * @author hyh
 * @since 2022-08-02
 */
@Service
@Slf4j
public class CdPtyvMServiceImpl extends ServiceImpl<CdPtyvMMapper, CdPtyvM> implements ICdPtyvMService {

    @Autowired
    private CdPtyvMMapper ptyvMMapper;

    /**
     * 查询编码属性值
     *
     * @param dto
     * @return
     */
    @Override
    public List<Map<String, String>> getPtyvList(CdPptyMQueryDto dto) {
        List<Map<String, String>> maps = new ArrayList<>();
        List<CdPtyvMTempVo> tempVos = ptyvMMapper.getTempVoList(dto);
        if (CollectionUtils.isNotEmpty(tempVos)) {
            Map<String, List<CdPtyvMTempVo>> map = tempVos.stream().collect(Collectors.groupingBy(CdPtyvMTempVo::getCvCdvlId));
            for (String key : map.keySet()) {
                List<CdPtyvMTempVo> vos = map.get(key);
                Map<String, String> voMap = new HashMap<>(vos.size());
                for (CdPtyvMTempVo vo : vos) {
                    voMap.put(vo.getCdPptyNm(), vo.getCdPptyVl());
                }
                maps.add(voMap);
            }
        }
        return maps;
    }

    @Override
    public List<Map<String, String>> getDataListMap(int count, CdPptyMQueryDto dto) {
        StringBuffer tableSqlSb = new StringBuffer();
        tableSqlSb.append(" select cv_cdvl_id");
        for (int i = 1; i < count + 1; i++) {
            tableSqlSb.append(" ,string_agg(case when rank_no = " + i + " then cd_ppty_vl end,'') attr" + i);
            tableSqlSb.append(" ,string_agg(case when rank_no = " + i + " then cv_cd_ppty_id end,'') attrid" + i);
        }
        tableSqlSb.append(" from (")
                .append(" select rank() over (partition by b.cv_cdvl_id order by a.cv_sort_srno, a.cv_cd_ppty_id, a.rec_bgn_dttm) rank_no, ")
                .append(" a.cv_cd_id, a.cv_cd_ppty_id, a.cv_cd_sphr_id, a.cd_ppty_nm, a.pk_yn, a.null_yn, a.data_tp_nm,a.rec_bgn_dttm,")
                .append(" a.data_len, a.cv_sort_srno, b.cv_cdvl_id, b.cd_vl, b.cdvl_nm, b.cdvl_expl_txt, b.sort_srno, c.cd_ppty_vl")
                .append(" from cvcd_cd_ppty_m a join cvcd_cdvl_m b on a.cv_cd_id = b.cv_cd_id ")
                .append(" and a.rec_fnsh_dttm = '9999-12-31 00:00:00.000000' ")
                .append(" and b.rec_fnsh_dttm = '9999-12-31 00:00:00.000000' ")
                .append(" and a.cv_cd_id = '" + dto.getCvCdId() + "' ")
                .append(" left join cvcd_cd_ptyv_m c on a.cv_cd_id = c.cv_cd_id ")
                .append(" and a.cv_cd_ppty_id = c.cv_cd_ppty_id ")
                .append(" and b.cv_cdvl_id = c.cv_cdvl_id ")
                .append(" and c.rec_fnsh_dttm = '9999-12-31 00:00:00.000000' ")
                .append(" ) a group by cv_cdvl_id");
                //.append(" group by cv_cdvl_id, a.rec_bgn_dttm");
        String dataSql = tableSqlSb.toString();
        log.info("编码值数据SQL: {}", dataSql);
        List<Map<String, String>> dataList = ptyvMMapper.getDataListMap(dataSql);
        return dataList;
    }

    public List<CdPtyvM> getListByCdvlM(String cvCdId, String cvCdvlId) {
        QueryWrapper<CdPtyvM> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(CdPtyvM::getCvCdId, cvCdId)
                .eq(CdPtyvM::getCvCdvlId, cvCdvlId)
                .eq(CdPtyvM::getRecFnshDttm, Constants.FNSHDTTM);
        return this.list(queryWrapper);
    }

    /**
     * 更新老的过期，并重新 insert
     * @param item
     * @param newValue
     */
    public void update(CdPtyvM item, String newValue) {
        QueryWrapper<CdPtyvM> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(CdPtyvM::getCvCdId, item.getCvCdId())
                .eq(CdPtyvM::getCvCdvlId, item.getCvCdvlId())
                .eq(CdPtyvM::getCvCdPptyId, item.getCvCdPptyId())
                .eq(CdPtyvM::getRecFnshDttm, item.getRecFnshDttm())
                .eq(CdPtyvM::getRecBgnDttm, item.getRecBgnDttm());

        item.setRecFnshDttm(new Date());
        item.setFnlYn("N");
        this.update(item, queryWrapper);

        item.setCdPptyVl(newValue);
        item.setFnlYn("Y");
        item.setRecBgnDttm(new Date(item.getRecFnshDttm().getTime() + 1));
        item.setRecFnshDttm(Constants.FNSHDTTM);
        item.setCretCvUserId(UserUtils.getLoginUser().getUserId());
        this.save(item);
    }
}
