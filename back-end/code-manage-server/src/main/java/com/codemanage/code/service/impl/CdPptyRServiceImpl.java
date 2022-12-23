package com.codemanage.code.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codemanage.code.dto.request.CdPptyMQueryDto;
import com.codemanage.code.dto.request.CdPptyRAutoSaveDto;
import com.codemanage.code.dto.request.CdPptyRQueryDto;
import com.codemanage.code.dto.request.CdPptyRSaveDto;
import com.codemanage.code.dto.response.CdPptyMVo;
import com.codemanage.code.dto.response.CdPptyRVo;
import com.codemanage.code.entity.CdM;
import com.codemanage.code.entity.CdPptyM;
import com.codemanage.code.entity.CdPptyR;
import com.codemanage.code.entity.CdR;
import com.codemanage.code.mapper.CdPptyRMapper;
import com.codemanage.code.service.ICdMService;
import com.codemanage.code.service.ICdPptyMService;
import com.codemanage.code.service.ICdPptyRService;
import com.codemanage.code.service.ICdRService;
import com.codemanage.common.constants.Constants;
import com.codemanage.common.entity.BaseResult;
import com.codemanage.common.util.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * CVCD_编码属性_关系表 服务实现类
 * </p>
 *
 * @author hyh
 * @since 2022-06-07
 */
@Slf4j
@Service
public class CdPptyRServiceImpl extends ServiceImpl<CdPptyRMapper, CdPptyR> implements ICdPptyRService {

    @Autowired
    private ICdRService cdRService;

    @Autowired
    private ICdPptyMService pptyMService;

    @Autowired
    private CdPptyRMapper pptyRMapper;

    /**
     * 查询映射关系
     *
     * @param dto
     * @return
     */
    @Override
    public List<CdPptyRVo> getPptyR(CdPptyRQueryDto dto) {
        return pptyRMapper.getPptyR(dto);
    }

    /**
     * 映射
     *
     * @param dto
     * @return
     */
    @Override
    public BaseResult addPptyR(CdPptyRSaveDto dto) {
        Date newBgnDttm = new Date();
        CdPptyR obj = this.dtoToObj(dto, newBgnDttm);

        if (dto.getLeftValue().equals("=100")) {
            obj.setRltsExplTxt("=100");
            obj.setHlvCvCdPptyId("0");
        }
        else if(dto.getRightValue().equals("=100")) {
            obj.setRltsExplTxt("=100");
            obj.setLlvCvCdPptyId("0");
        }

        CdR cdR = this.cdRService.getById(dto.getHlvCvCdId(), dto.getLlvCvCdId());
        if (cdR.getCvCdRltsCd().equals("0100")) {
            obj.setCvPptyRltsDstsCd("0300");
        }

        QueryWrapper<CdPptyR> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(CdPptyR::getHlvCvCdId, obj.getHlvCvCdId())
                .eq(CdPptyR::getHlvCvCdPptyId, obj.getHlvCvCdPptyId())
                .eq(CdPptyR::getLlvCvCdId, obj.getLlvCvCdId())
                .eq(CdPptyR::getLlvCvCdPptyId, obj.getLlvCvCdPptyId())
                .eq(CdPptyR::getRecFnshDttm, Constants.FNSHDTTM);

        if (pptyRMapper.selectCount(queryWrapper) == 0) {
            pptyRMapper.insert(obj);
        }

        return BaseResult.successMsg("映射成功!");
    }

    /**
     * 自动映射
     *
     * @param dto
     * @return
     */
    @Override
    public BaseResult addBatchPptyR(CdPptyRAutoSaveDto dto) {
        CdPptyMQueryDto leftQueryDto = new CdPptyMQueryDto();
        leftQueryDto.setCvCdId(dto.getHlvCvCdId());
        CdPptyMQueryDto rightQueryDto = new CdPptyMQueryDto();
        rightQueryDto.setCvCdId(dto.getLlvCvCdId());

        CdR cdR = this.cdRService.getById(dto.getHlvCvCdId(), dto.getLlvCvCdId());
        List<CdPptyMVo> leftList = this.pptyMService.getCdPptyMList(leftQueryDto);
        List<CdPptyMVo> rightList = this.pptyMService.getCdPptyMList(rightQueryDto);

        int maxSize = Math.max(leftList.size(), rightList.size());
        List<CdPptyR> listToSave = new ArrayList<>(maxSize);

        if (dto.getCodeOrder().equals("1")) {
            for (int i = 0; i < maxSize; i++) {
                CdPptyMVo left = i + 1 > leftList.size() ? null : leftList.get(i);
                CdPptyMVo right = i + 1 > rightList.size() ? null : rightList.get(i);
                if (left == null || right == null) {
                    continue;
                }

                CdPptyR item = new CdPptyR();
                item.setHlvCvCdId(left.getCvCdId());
                item.setHlvCvCdPptyId(left.getCvCdPptyId());
                item.setLlvCvCdId(right.getCvCdId());
                item.setLlvCvCdPptyId(right.getCvCdPptyId());
                item.setFnlYn("Y");
                item.setRecBgnDttm(new Date());
                item.setRecFnshDttm(Constants.FNSHDTTM);
                item.setCretCvUserId(UserUtils.getLoginUser().getUserId());
                item.setCvPptyRltsDstsCd("0100");
                item.setSortSrno(String.valueOf(left.getCvSortSrno()));

                if (cdR.getCvCdRltsCd().equals("0100") && dto.isEquals100()) {
                    item.setCvPptyRltsDstsCd("0300");
                }
                listToSave.add(item);
            }
        } else {
            // 按名称
            List<CdPptyMVo> forList = leftList.size() > rightList.size() ? leftList : rightList;
            List<CdPptyMVo> findList = leftList.size() > rightList.size() ? rightList : leftList;

            forList.forEach(forItem -> {
                Optional<CdPptyMVo> foundItem = findList.stream()
                        .filter(findItem -> findItem.getCdPptyNm().equals(forItem.getCdPptyNm()))
                        .findFirst();

                if (foundItem.isPresent()) {
                    CdPptyR item = new CdPptyR();

                    if (forList == leftList) {
                        item.setHlvCvCdId(forItem.getCvCdId());
                        item.setHlvCvCdPptyId(forItem.getCvCdPptyId());
                        item.setLlvCvCdId(foundItem.get().getCvCdId());
                        item.setLlvCvCdPptyId(foundItem.get().getCvCdPptyId());
                    } else {
                        item.setHlvCvCdId(foundItem.get().getCvCdId());
                        item.setHlvCvCdPptyId(foundItem.get().getCvCdPptyId());
                        item.setLlvCvCdId(forItem.getCvCdId());
                        item.setLlvCvCdPptyId(forItem.getCvCdPptyId());
                    }

                    item.setFnlYn("Y");
                    item.setRecBgnDttm(new Date());
                    item.setRecFnshDttm(Constants.FNSHDTTM);
                    item.setCretCvUserId(UserUtils.getLoginUser().getUserId());
                    item.setCvPptyRltsDstsCd("0100");
                    item.setSortSrno(String.valueOf(foundItem.get().getCvSortSrno()));
//                    if (cdR.getCvCdRltsCd().equals("0100") && dto.isEquals100()) {
//                        item.setCvPptyRltsDstsCd("0300");
//                    }
                    listToSave.add(item);
                }
            });
        }

        for (CdPptyR item : listToSave) {
            QueryWrapper<CdPptyR> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda()
                    .eq(CdPptyR::getHlvCvCdId, item.getHlvCvCdId())
                    .eq(CdPptyR::getHlvCvCdPptyId, item.getHlvCvCdPptyId())
                    .eq(CdPptyR::getLlvCvCdId, item.getLlvCvCdId())
                    .eq(CdPptyR::getLlvCvCdPptyId, item.getLlvCvCdPptyId())
                    .eq(CdPptyR::getRecFnshDttm, Constants.FNSHDTTM);

            if (pptyRMapper.selectCount(queryWrapper) == 0) {
                pptyRMapper.insert(item);
            }
        }

        return BaseResult.successMsg("自动映射成功!");
    }

    /**
     * 删除映射
     *
     * @param dtoList
     * @return
     */
    @Override
    public BaseResult delBatchPptyR(List<CdPptyRSaveDto> dtoList) {
        dtoList.forEach(dto->{
            QueryWrapper<CdPptyR> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda()
                    .eq(CdPptyR::getHlvCvCdId, dto.getHlvCvCdId())
                    .eq(CdPptyR::getHlvCvCdPptyId, dto.getHlvCvCdPptyId())
                    .eq(CdPptyR::getLlvCvCdId, dto.getLlvCvCdId())
                    .eq(CdPptyR::getLlvCvCdPptyId, dto.getLlvCvCdPptyId())
                    .eq(CdPptyR::getRecFnshDttm, Constants.FNSHDTTM);

            CdPptyR obj = this.getOne(queryWrapper);
            obj.setFnlYn("N");
            obj.setRecFnshDttm(new Date());
            this.update(obj, queryWrapper);

            obj.setRecBgnDttm(new Date(obj.getRecFnshDttm().getTime()+1));
            obj.setRecFnshDttm(obj.getRecBgnDttm());
            obj.setFnlYn("Y");
            obj.setCretCvUserId(UserUtils.getLoginUser().getUserId());
            this.save(obj);
        });

        return BaseResult.successMsg("删除成功!");

//        if (CollectionUtils.isNotEmpty(dtoList)) {
//            Date oldFnshDttm = new Date();
//            Date newBgnDttm = new Date(oldFnshDttm.getTime() + 1);
//            String llvCvCdId = dtoList.get(0).getLlvCvCdId();
//            String hlvCvCdId = dtoList.get(0).getHlvCvCdId();
//            List<String> llvCvCdPptyIdList = dtoList.stream().map(CdPptyRSaveDto::getLlvCvCdPptyId).collect(Collectors.toList());
//            List<String> hlvCvCdPptyIdList = dtoList.stream().map(CdPptyRSaveDto::getHlvCvCdPptyId).collect(Collectors.toList());
//            List<CdPptyR> oldList = this.getList(llvCvCdId, hlvCvCdId, llvCvCdPptyIdList, hlvCvCdPptyIdList);
//            boolean flag = updateFnlYn(llvCvCdId, hlvCvCdId, llvCvCdPptyIdList, hlvCvCdPptyIdList, oldFnshDttm);
//            if (flag) {
//                String createUserId = UserUtils.getLoginUser().getUserId();
//                for (CdPptyR obj : oldList) {
//                    obj.setRecBgnDttm(newBgnDttm);
//                    obj.setRecFnshDttm(newBgnDttm);
//                    obj.setCretCvUserId(createUserId);
//                }
//                this.saveBatch(oldList);
//                return BaseResult.successMsg("删除成功!");
//            }
//            return BaseResult.failedMsg("删除失败!");
//        }
//        return BaseResult.failedMsg("请选择要删除的数据!");
    }

    @Override
    public int getCount(CdPptyRQueryDto dto) {
        return pptyRMapper.getCount(dto);
    }

    /**
     * 编码值关系表头
     *
     * @param count
     * @param dto
     * @return
     */
    @Override
    public Map<String, String> getTableMap(int count, CdPptyRQueryDto dto) {
        StringBuffer tableSqlSb = new StringBuffer();
        tableSqlSb.append("select ");
        for (int i = 1; i < count + 1; i++) {
            tableSqlSb.append("string_agg(case when b.rank_no = ").append(i).append(" then concat(b.cd_lgc_nm, '.', b.cd_ppty_nm) end,'') attr").append(i);
            if (i < count) {
                tableSqlSb.append(",");
            }
        }

        tableSqlSb.append(" from\n" +
                "(\n" +
                "select rank() over (order by a.srno, a.rec_bgn_dttm, a.ppty_m_rec_bgn_dttm) rank_no, a.*\n" +
                "from\n" +
                "(\n" +
                "select\n" +
                "    COALESCE(hp.cd_ppty_nm, '=100') as cd_ppty_nm,\n" +
                "    pr.rec_bgn_dttm, COALESCE(hp.rec_bgn_dttm, '9999-12-31 00:00:00.000000') as ppty_m_rec_bgn_dttm,\n" +
                "    (select cd_lgc_nm from cvcd_cd_m where cv_cd_id = pr.hlv_cv_cd_id and rec_fnsh_dttm = '9999-12-31 00:00:00.000000') as cd_lgc_nm,\n" +
                "    1 as srno\n" +
                "from\n" +
                "    cvcd_cd_ppty_r pr\n" +
                "    left join cvcd_cd_ppty_m hp on pr.hlv_cv_cd_ppty_id = hp.cv_cd_ppty_id and hp.cv_cd_id = pr.hlv_cv_cd_id and hp.rec_fnsh_dttm = '9999-12-31 00:00:00.000000'\n" +
                "where\n" +
                "    pr.hlv_cv_cd_id = '"+ dto.getHlvCvCdId() +"' and\n" +
                "    pr.llv_cv_cd_id = '"+ dto.getLlvCvCdId() +"' and\n" +
                "    pr.rec_fnsh_dttm = '9999-12-31 00:00:00.000000'\n" +
                "union all\n" +
                "select\n" +
                "    COALESCE(lp.cd_ppty_nm, '=100') as cd_ppty_nm,\n" +
                "    pr.rec_bgn_dttm, COALESCE(lp.rec_bgn_dttm, '9999-12-31 00:00:00.000000') as ppty_m_rec_bgn_dttm,\n" +
                "    (select cd_lgc_nm from cvcd_cd_m where cv_cd_id = pr.llv_cv_cd_id and rec_fnsh_dttm = '9999-12-31 00:00:00.000000') as cd_lgc_nm,\n" +
                "    2 as srno\n" +
                "from\n" +
                "    cvcd_cd_ppty_r pr\n" +
                "        left join cvcd_cd_ppty_m lp on pr.llv_cv_cd_ppty_id = lp.cv_cd_ppty_id and lp.cv_cd_id = pr.llv_cv_cd_id and lp.rec_fnsh_dttm = '9999-12-31 00:00:00.000000'\n" +
                "where\n" +
                "    pr.hlv_cv_cd_id = '"+ dto.getHlvCvCdId() +"' and\n" +
                "    pr.llv_cv_cd_id = '"+ dto.getLlvCvCdId() +"' and\n" +
                "    pr.rec_fnsh_dttm = '9999-12-31 00:00:00.000000'\n" +
                ") a\n" +
                ") b");

        String tableSql = tableSqlSb.toString();
        log.info("编码值关系表头SQL: {}", tableSql);
        Map<String, String> tableMap = pptyRMapper.getTableMap(tableSql);
        return tableMap;
    }

    /**
     * 编码值关系数据
     *
     * @param count
     * @param dto
     * @return
     */
    @Override
    public List<Map<String, String>> getDataListMap(int count, CdPptyRQueryDto dto) {
        StringBuffer tableSqlSb = new StringBuffer();
        tableSqlSb.append(" select hlv_cv_cd_id, hlv_cv_cdvl_id, llv_cv_cd_id, llv_cv_cdvl_id ");
        for (int i = 1; i < count + 1; i++) {
            tableSqlSb.append(" ,string_agg(case when rank_no = " + i + " then cd_ppty_vl end,'') attr" + i);
        }
        tableSqlSb.append(" from (")
                .append(" select rank() over (partition by a.hlv_cv_cdvl_id order by  sort_srno,srno,cv_sort_srno ) rank_no ,*")
                .append(" from ( select a.cv_cd_id, a.cv_cd_ppty_id, a.cv_cd_sphr_id, a.cd_ppty_nm, a.pk_yn, a.null_yn, a.data_tp_nm, ")
                .append(" a.data_len, a.cv_sort_srno, b.cv_cdvl_id, b.cd_vl, b.cdvl_nm, b.cdvl_expl_txt, b.sort_srno, c.cd_ppty_vl, ")
                .append(" d.hlv_cv_cd_id, d.hlv_cv_cdvl_id, d.llv_cv_cd_id, d.llv_cv_cdvl_id, d.cv_cdvl_rlts_dsts_cd, d.rlts_expl_txt, 1 as srno ")
                .append(" from cvcd_cd_ppty_m a join cvcd_cdvl_m b on a.cv_cd_id = b.cv_cd_id ")
                .append(" and a.rec_fnsh_dttm = '9999-12-31 00:00:00.000000' ")
                .append(" and b.rec_fnsh_dttm = '9999-12-31 00:00:00.000000' ")
                .append(" left join cvcd_cd_ptyv_m c on a.cv_cd_id = c.cv_cd_id ")
                .append(" and a.cv_cd_ppty_id = c.cv_cd_ppty_id ")
                .append(" and b.cv_cdvl_id = c.cv_cdvl_id ")
                .append(" and c.rec_fnsh_dttm = '9999-12-31 00:00:00.000000' ")
                .append(" join cvcd_cdvl_r d on d.hlv_cv_cd_id = b.cv_cd_id ")
                .append(" and d.hlv_cv_cdvl_id = b.cv_cdvl_id  ")
                .append(" and d.rec_fnsh_dttm = '9999-12-31 00:00:00.000000' ")
                .append(" and d.hlv_cv_cd_id =  '" + dto.getHlvCvCdId() + "' ")
                .append(" join cvcd_cd_ppty_r e on e.hlv_cv_cd_id = a.cv_cd_id ")
                .append(" and e.hlv_cv_cd_ppty_id = a.cv_cd_ppty_id   ")
                .append(" and e.rec_fnsh_dttm = '9999-12-31 00:00:00.000000' ")
                .append(" union all ")
                .append(" select a.cv_cd_id, a.cv_cd_ppty_id, a.cv_cd_sphr_id, a.cd_ppty_nm, a.pk_yn, a.null_yn, a.data_tp_nm,  ")
                .append(" a.data_len, a.cv_sort_srno, b.cv_cdvl_id, b.cd_vl, b.cdvl_nm, b.cdvl_expl_txt, b.sort_srno, c.cd_ppty_vl, ")
                .append(" d.hlv_cv_cd_id, d.hlv_cv_cdvl_id, d.llv_cv_cd_id, d.llv_cv_cdvl_id, d.cv_cdvl_rlts_dsts_cd, d.rlts_expl_txt, 2 as srno ")
                .append(" from cvcd_cd_ppty_m a join cvcd_cdvl_m b on a.cv_cd_id = b.cv_cd_id ")
                .append(" and a.rec_fnsh_dttm = '9999-12-31 00:00:00.000000' ")
                .append(" and b.rec_fnsh_dttm = '9999-12-31 00:00:00.000000' ")
                .append(" left join cvcd_cd_ptyv_m c on a.cv_cd_id = c.cv_cd_id ")
                .append(" and a.cv_cd_ppty_id = c.cv_cd_ppty_id ")
                .append(" and b.cv_cdvl_id = c.cv_cdvl_id ")
                .append(" and c.rec_fnsh_dttm = '9999-12-31 00:00:00.000000' ")
                .append(" join cvcd_cdvl_r d on d.llv_cv_cd_id = b.cv_cd_id ")
                .append(" and d.llv_cv_cdvl_id = b.cv_cdvl_id  ")
                .append(" and d.rec_fnsh_dttm = '9999-12-31 00:00:00.000000' ")
                .append(" and d.llv_cv_cd_id =  '" + dto.getLlvCvCdId() + "' ")
                .append(" join cvcd_cd_ppty_r e on e.llv_cv_cd_id = a.cv_cd_id ")
                .append(" and e.llv_cv_cd_ppty_id = a.cv_cd_ppty_id   ")
                .append(" and e.rec_fnsh_dttm = '9999-12-31 00:00:00.000000' ) a ")
                .append(" ) b ")
                .append(" group by hlv_cv_cd_id, hlv_cv_cdvl_id, llv_cv_cd_id, llv_cv_cdvl_id;");
        String dataSql = tableSqlSb.toString();
        log.info("编码值关系数据SQL: {}", dataSql);
        List<Map<String, String>> dataList = pptyRMapper.getDataListMap(dataSql);
        return dataList;
    }

    @Override
    public boolean delByPptyM(String cvCdId, String cvCdPptyId, Date oldFnshDttm, Date newBgnDttm) {
        QueryWrapper<CdPptyR> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(CdPptyR::getRecFnshDttm, Constants.FNSHDTTM)
                .and(q -> q.eq(CdPptyR::getHlvCvCdId, cvCdId).or().eq(CdPptyR::getLlvCvCdId, cvCdId))
                .and(q -> q.eq(CdPptyR::getHlvCvCdPptyId, cvCdPptyId).or().eq(CdPptyR::getLlvCvCdPptyId, cvCdPptyId));
        List<CdPptyR> oldList = this.list(queryWrapper);
        if (CollectionUtils.isNotEmpty(oldList)) {
            UpdateWrapper<CdPptyR> updateWrapper = new UpdateWrapper<>();
            updateWrapper.lambda().set(CdPptyR::getFnlYn, Constants.FnlYn.NO)
                    .set(CdPptyR::getRecFnshDttm, oldFnshDttm);
            updateWrapper.lambda().eq(CdPptyR::getRecFnshDttm, Constants.FNSHDTTM)
                    .and(q -> q.eq(CdPptyR::getHlvCvCdId, cvCdId).or().eq(CdPptyR::getLlvCvCdId, cvCdId))
                    .and(q -> q.eq(CdPptyR::getHlvCvCdPptyId, cvCdPptyId).or().eq(CdPptyR::getLlvCvCdPptyId, cvCdPptyId));
            boolean flag = this.update(updateWrapper);
            if (flag) {
                String userId = UserUtils.getLoginUser().getUserId();
                for (CdPptyR oldObj : oldList) {
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

    private boolean updateFnlYn(String llvCvCdId, String hlvCvCdId, List<String> llvCvCdPptyIdList,
                                List<String> hlvCvCdPptyIdList, Date oldFnshDttm) {
        UpdateWrapper<CdPptyR> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().set(CdPptyR::getFnlYn, Constants.FnlYn.NO)
                .set(CdPptyR::getRecFnshDttm, oldFnshDttm);
        updateWrapper.lambda().eq(CdPptyR::getLlvCvCdId, llvCvCdId)
                .in(CdPptyR::getLlvCvCdPptyId, llvCvCdPptyIdList)
                .eq(CdPptyR::getHlvCvCdId, hlvCvCdId)
                .in(CdPptyR::getHlvCvCdPptyId, hlvCvCdPptyIdList)
                .eq(CdPptyR::getRecFnshDttm, Constants.FNSHDTTM);
        return this.update(updateWrapper);
    }

    private List<CdPptyR> getList(String llvCvCdId, String hlvCvCdId, List<String> llvCvCdPptyIdList, List<String> hlvCvCdPptyIdList) {
        QueryWrapper<CdPptyR> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(CdPptyR::getLlvCvCdId, llvCvCdId)
                .in(CdPptyR::getLlvCvCdPptyId, llvCvCdPptyIdList)
                .eq(CdPptyR::getHlvCvCdId, hlvCvCdId)
                .in(CdPptyR::getHlvCvCdPptyId, hlvCvCdPptyIdList)
                .eq(CdPptyR::getRecFnshDttm, Constants.FNSHDTTM)
                .eq(CdPptyR::getFnlYn, Constants.FnlYn.YES);
        return this.list(queryWrapper);
    }

    private CdPptyR dtoToObj(CdPptyRSaveDto dto, Date newBgnDttm) {
        CdPptyR obj = new CdPptyR();
        BeanUtils.copyProperties(dto, obj);
        obj.setRecBgnDttm(newBgnDttm);
        obj.setRecFnshDttm(Constants.FNSHDTTM);
        obj.setFnlYn(Constants.FnlYn.YES);
        obj.setCretCvUserId(UserUtils.getLoginUser().getUserId());
        return obj;
    }

    private List<CdPptyR> dtoListToObjList(List<CdPptyRSaveDto> dtoList) {
        List<CdPptyR> objList = new ArrayList<>();
        Date newBgnDttm = new Date();
        for (CdPptyRSaveDto dto : dtoList) {
            CdPptyR obj = this.dtoToObj(dto, newBgnDttm);
            objList.add(obj);
        }
        return objList;
    }
}
