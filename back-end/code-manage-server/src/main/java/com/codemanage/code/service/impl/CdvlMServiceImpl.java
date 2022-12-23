package com.codemanage.code.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codemanage.code.dto.request.CdvlMDelDto;
import com.codemanage.code.dto.request.CdvlMDto;
import com.codemanage.code.entity.CdPtyvM;
import com.codemanage.code.entity.CdvlM;
import com.codemanage.code.entity.CdvlR;
import com.codemanage.code.mapper.CdvlMMapper;
import com.codemanage.code.service.ICdPtyvMService;
import com.codemanage.code.service.ICdvlMService;
import com.codemanage.code.service.ICdvlRService;
import com.codemanage.common.constants.Constants;
import com.codemanage.common.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * CVCD_编码值_主表 服务实现类
 * </p>
 *
 * @author hyh
 * @since 2022-06-07
 */
@Service
public class CdvlMServiceImpl extends ServiceImpl<CdvlMMapper, CdvlM> implements ICdvlMService {

    @Autowired
    private CdvlMMapper cdvlMMapper;

    @Lazy
    @Autowired
    private ICdvlRService cdvlRService;

    @Autowired
    private ICdPtyvMService ptyvMService;

    /**
     * 分页查询编码值
     *
     * @param dto
     * @return
     */
    @Override
    public IPage<CdvlM> getCdvlMByPage(CdvlMDto dto) {
        IPage<CdvlM> page = new Page<>(dto.getCurrentPage(), dto.getPageSize());
        page = cdvlMMapper.listCdvlM(page, dto);
        return page;
    }

    @Override
    public boolean delBatchCdvlM(CdvlMDelDto delDto) {
        for (String cvCdvlId : delDto.getCvCdvlIdList()) {
            CdvlM cdvlM = this.getObjById(cvCdvlId);
            if(cdvlM == null) {
                continue;
            }
            List<CdPtyvM> cdPtyvMList = this.ptyvMService.getListByCdvlM(cdvlM.getCvCdId(), cdvlM.getCvCdvlId());

            // cvcd_cdvl_m
            {
                QueryWrapper<CdvlM> queryWrapper = new QueryWrapper<>();
                queryWrapper.lambda()
                        .eq(CdvlM::getCvCdvlId, cvCdvlId)
                        .eq(CdvlM::getRecFnshDttm, Constants.FNSHDTTM);

                cdvlM.setFnlYn("N");
                cdvlM.setRecFnshDttm(new Date());
                this.update(cdvlM, queryWrapper);

                cdvlM.setRecBgnDttm(new Date(cdvlM.getRecFnshDttm().getTime() + 1));
                cdvlM.setRecFnshDttm(cdvlM.getRecBgnDttm());
                cdvlM.setCretCvUserId(UserUtils.getLoginUser().getUserId());
                this.save(cdvlM);
            }
            // cvcd_cdvl_r
            {
                QueryWrapper<CdvlR> queryWrapper = new QueryWrapper<>();
                queryWrapper.lambda()
                        .and(a -> a.eq(CdvlR::getHlvCvCdvlId, cvCdvlId).or().eq(CdvlR::getLlvCvCdvlId, cvCdvlId))
                        .and(a -> a.eq(CdvlR::getHlvCvCdId, delDto.getCvCdId()).or().eq(CdvlR::getLlvCvCdId, delDto.getCvCdId()))
                        .eq(CdvlR::getRecFnshDttm, Constants.FNSHDTTM);

                for (CdvlR item : cdvlRService.list(queryWrapper)) {
                    QueryWrapper<CdvlR> updateWrapper = new QueryWrapper<>();
                    updateWrapper.lambda()
                            .eq(CdvlR::getHlvCvCdvlId, item.getHlvCvCdvlId())
                            .eq(CdvlR::getHlvCvCdId, item.getHlvCvCdId())
                            .eq(CdvlR::getLlvCvCdvlId, item.getLlvCvCdvlId())
                            .eq(CdvlR::getLlvCvCdId, item.getLlvCvCdId())
                            .eq(CdvlR::getRecFnshDttm, Constants.FNSHDTTM);

                    item.setFnlYn("N");
                    item.setRecFnshDttm(new Date());
                    this.cdvlRService.update(item, updateWrapper);

                    item.setRecBgnDttm(new Date(item.getRecFnshDttm().getTime() + 1));
                    item.setRecFnshDttm(item.getRecBgnDttm());
                    item.setCretCvUserId(UserUtils.getLoginUser().getUserId());
                    this.cdvlRService.save(item);
                }
            }
            // cvcd_cd_ptyv_m
            {
                for (CdPtyvM item : cdPtyvMList) {
                    QueryWrapper<CdPtyvM> updateWrapper = new QueryWrapper<>();
                    updateWrapper.lambda()
                            .eq(CdPtyvM::getCvCdId, item.getCvCdId())
                            .eq(CdPtyvM::getCvCdvlId, item.getCvCdvlId())
                            .eq(CdPtyvM::getCvCdPptyId, item.getCvCdPptyId())
                            .eq(CdPtyvM::getRecBgnDttm, item.getRecBgnDttm());

                    item.setFnlYn("N");
                    item.setRecFnshDttm(new Date());
                    ptyvMService.update(item, updateWrapper);

                    item.setRecBgnDttm(new Date(item.getRecFnshDttm().getTime() + 1));
                    item.setRecFnshDttm(item.getRecBgnDttm());
                    item.setCretCvUserId(UserUtils.getLoginUser().getUserId());
                    ptyvMService.save(item);
                }
            }
        }

        return true;
    }


    private List<CdvlM> getByIdList(String cvCdId, List<String> cvCdvlIdList) {
        QueryWrapper<CdvlM> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(CdvlM::getCvCdId, cvCdId)
                .in(CdvlM::getCvCdvlId, cvCdvlIdList)
                .eq(CdvlM::getRecFnshDttm, Constants.FNSHDTTM);
        return this.list(queryWrapper);
    }

    private boolean updateFnlYn(String cvCdId, List<String> cvCdvlIdList, Date oldFnshDttm) {
        UpdateWrapper<CdvlM> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().set(CdvlM::getFnlYn, Constants.FnlYn.NO)
                .set(CdvlM::getRecFnshDttm, oldFnshDttm);
        updateWrapper.lambda().eq(CdvlM::getCvCdId, cvCdId)
                .in(CdvlM::getCvCdvlId, cvCdvlIdList)
                .eq(CdvlM::getRecFnshDttm, Constants.FNSHDTTM);
        return this.update(updateWrapper);
    }

    @Override
    public long countByCvCdId(String cvCdId) {
        QueryWrapper<CdvlM> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(CdvlM::getCvCdId, cvCdId);
        return this.count(queryWrapper);
    }

    public CdvlM getObjById(String cvCdvlId) {
        QueryWrapper<CdvlM> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(CdvlM::getCvCdvlId, cvCdvlId)
                .eq(CdvlM::getRecFnshDttm, Constants.FNSHDTTM);
        return this.getOne(queryWrapper);
    }

    public void update(CdvlM item, String newValue) {
        QueryWrapper<CdvlM> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(CdvlM::getCvCdId, item.getCvCdId())
                .eq(CdvlM::getCvCdvlId, item.getCvCdvlId())
                .eq(CdvlM::getRecFnshDttm, item.getRecFnshDttm())
                .eq(CdvlM::getRecBgnDttm, item.getRecBgnDttm());

        item.setFnlYn("N");
        item.setRecFnshDttm(new Date());
        this.update(item, queryWrapper);

        item.setFnlYn("Y");
        item.setRecBgnDttm(new Date(item.getRecFnshDttm().getTime() + 1));
        item.setRecFnshDttm(Constants.FNSHDTTM);
        item.setCdVl(newValue);
        item.setCretCvUserId(UserUtils.getLoginUser().getUserId());
        this.save(item);
    }
}
