package com.codemanage.code.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.codemanage.code.dto.request.CdVlMSaveDto;
import com.codemanage.code.dto.request.CdvlMDelDto;
import com.codemanage.code.dto.request.CdvlMDto;
import com.codemanage.code.dto.request.CdvlMUpdateDto;
import com.codemanage.code.entity.CdM;
import com.codemanage.code.entity.CdPptyM;
import com.codemanage.code.entity.CdPtyvM;
import com.codemanage.code.entity.CdvlM;
import com.codemanage.code.service.ICdMService;
import com.codemanage.code.service.ICdPptyMService;
import com.codemanage.code.service.ICdPtyvMService;
import com.codemanage.code.service.ICdvlMService;
import com.codemanage.common.constants.Constants;
import com.codemanage.common.entity.BaseResult;
import com.codemanage.common.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * <p>
 * CVCD_编码值_主表 前端控制器
 * </p>
 *
 * @author hyh
 * @since 2022-06-07
 */
@RestController
@RequestMapping("/code/cdvlM")
@Api(tags = "编码值API")
@Slf4j
public class CdvlMController {

    @Autowired
    private ICdMService cdMService;

    @Autowired
    private ICdvlMService cdvlMService;

    @Autowired
    private ICdPptyMService pptyMService;

    @Autowired
    private ICdPtyvMService ptyvMService;

    /**
     * 分页查询编码值
     *
     * @param dto
     * @return
     */
    @PostMapping("/getCdvlMByPage")
    @ApiOperation(value = "分页查询编码", notes = "分页查询编码")
    public BaseResult getCdvlMByPage(@RequestBody CdvlMDto dto) {
        try {
            if (dto.getCurrentPage() != null && dto.getPageSize() != null) {
                IPage<CdvlM> page = cdvlMService.getCdvlMByPage(dto);
                return BaseResult.successMsg("查询成功!", page);
            } else {
                return BaseResult.failedMsg("缺少分页参数！");
            }
        } catch (Exception e) {
            return BaseResult.failedMsg("查询异常,请联系管理员!", e.getMessage());
        }
    }

    @PostMapping("/addCdvlM")
    @ApiOperation(value = "添加编码值", notes = "添加编码值")
    public BaseResult addPtyvM(@RequestBody CdVlMSaveDto dto) {
        try {
            QueryWrapper<CdM> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda()
                    .eq(CdM::getCvCdId, dto.getCvCdId())
                    .eq(CdM::getRecFnshDttm, Constants.FNSHDTTM);
            CdM cdM = this.cdMService.getOne(queryWrapper);
            int countOfCdvlM = (int) this.cdvlMService.countByCvCdId(dto.getCvCdId());

            CdvlM cdvlM = new CdvlM();
            cdvlM.setCvCdId(cdM.getCvCdId());
            cdvlM.setCvCdvlId(UUID.randomUUID().toString());
            cdvlM.setRecBgnDttm(new Date());
            cdvlM.setRecFnshDttm(Constants.FNSHDTTM);
            cdvlM.setCvCdSphrId(cdM.getCvCdSphrId());
            cdvlM.setCdvlNm(cdM.getCdLgcNm());
            cdvlM.setCdVl(String.valueOf(countOfCdvlM + 1));
            cdvlM.setCdvlExplTxt(cdM.getCdExplTxt());
            cdvlM.setFnlYn("Y");
            cdvlM.setCvSortSrno(countOfCdvlM + 1);
            cdvlM.setCretCvUserId(UserUtils.getLoginUser().getUserId());

//            List<CdvlM> cdvlMList = new ArrayList<>();
            List<CdPtyvM> cdPtyvMList = new ArrayList<>();

            for (Map.Entry<String, String> keyValue : dto.getValues().entrySet()) {
                CdPptyM pptyM = this.pptyMService.getObjById(dto.getCvCdId(), keyValue.getKey());

                CdPtyvM cdPtyvM = new CdPtyvM();
                cdPtyvM.setCvCdvlId(cdvlM.getCvCdvlId());
                cdPtyvM.setCvCdId(dto.getCvCdId());
                cdPtyvM.setCvCdPptyId(keyValue.getKey());
                cdPtyvM.setRecBgnDttm(new Date());
                cdPtyvM.setRecFnshDttm(Constants.FNSHDTTM);

                cdPtyvM.setCdPptyVl(keyValue.getValue());
                cdPtyvM.setCdPtyvExplTxt(cdvlM.getCdvlExplTxt());
                cdPtyvM.setCvCdSphrId(pptyM.getCvCdSphrId());
                cdPtyvM.setFnlYn("Y");
                cdPtyvM.setSortSrno(String.valueOf(pptyM.getCvSortSrno()));
                cdPtyvM.setCretCvUserId(cdvlM.getCretCvUserId());

                cdPtyvMList.add(cdPtyvM);
            }

            if (this.cdvlMService.save(cdvlM) && this.ptyvMService.saveBatch(cdPtyvMList)) {
                return BaseResult.successMsg("添加成功!", true);
            }
            return BaseResult.failedMsg("未能添加编码值，保存失败");
        } catch (Exception e) {
            log.error("添加编码值异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("未能添加编码值", e.getMessage());
        }
    }

    @PostMapping("/editCdvlM")
    @ApiOperation(value = "修改编码值", notes = "修改编码值")
    public BaseResult editCdvlM(@RequestBody CdvlMUpdateDto dto) {
        try {
            CdvlM cdvlM = this.cdvlMService.getObjById(dto.getCvCdvlId());
            List<CdPtyvM> cdPtyvMList = this.ptyvMService.getListByCdvlM(cdvlM.getCvCdId(), cdvlM.getCvCdvlId());
            boolean updateCdvlM = false;

            for (Map.Entry<String, String> keyValue : dto.getValues().entrySet()) {
                CdPptyM pptyM = this.pptyMService.getObjById(cdvlM.getCvCdId(), keyValue.getKey());
                if (pptyM == null) {
                    continue;
                }

                Optional<CdPtyvM> optionalCdPtyvM = cdPtyvMList.stream().filter(item -> item.getCvCdPptyId().equals(keyValue.getKey())).findFirst();

                if (optionalCdPtyvM.isPresent()) {
                    CdPtyvM item = optionalCdPtyvM.get();
                    if (item.getCdPptyVl().equals(keyValue.getValue())) {
                        continue;
                    }

                    ptyvMService.update(item, keyValue.getValue());
                } else {
                    CdPtyvM cdPtyvM = new CdPtyvM();
                    cdPtyvM.setCvCdvlId(cdvlM.getCvCdvlId());
                    cdPtyvM.setCvCdId(cdvlM.getCvCdId());
                    cdPtyvM.setCvCdPptyId(keyValue.getKey());
                    cdPtyvM.setRecBgnDttm(new Date());
                    cdPtyvM.setRecFnshDttm(Constants.FNSHDTTM);

                    cdPtyvM.setCdPptyVl(keyValue.getValue());
                    cdPtyvM.setCdPtyvExplTxt(cdvlM.getCdvlExplTxt());
                    cdPtyvM.setCvCdSphrId(pptyM.getCvCdSphrId());
                    cdPtyvM.setFnlYn("Y");
                    cdPtyvM.setSortSrno(String.valueOf(pptyM.getCvSortSrno()));
                    cdPtyvM.setCretCvUserId(UserUtils.getLoginUser().getUserId());
                    ptyvMService.save(cdPtyvM);
                }

                if (pptyM.getCvPptyDstsCd() != null) {
                    if (pptyM.getCvPptyDstsCd().equals("0100") || pptyM.getCvPptyDstsCd().equals("0200")) {
                        updateCdvlM = true;
                    }
                }
            }

            if (updateCdvlM) {
                cdvlMService.update(cdvlM, "");
            }

            return BaseResult.successMsg("修改编码值成功");
        } catch (Exception e) {
            log.error("修改编码值异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("未能修改编码值", e.getMessage());
        }
    }

    /**
     * 批量删除编码值
     *
     * @param delDto
     * @return
     */
    @PostMapping("/delBatchCdvlM")
    @ApiOperation(value = "批量删除编码值", notes = "批量删除编码值")
    public BaseResult delBatchCdvlM(@RequestBody CdvlMDelDto delDto) {
        if (CollectionUtils.isEmpty(delDto.getCvCdvlIdList())) {
            return BaseResult.successMsg("删除成功!", true);
        }

        try {
            if (CollectionUtils.isNotEmpty(delDto.getCvCdvlIdList())) {
                boolean flag = cdvlMService.delBatchCdvlM(delDto);
                return BaseResult.successMsg("删除成功!", flag);
            } else {
                return BaseResult.failedMsg("缺少参数!", false);
            }
        } catch (Exception e) {
            log.error("批量删除编码值异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("删除异常,请联系管理员!", e.getMessage());
        }
    }

}
