package com.codemanage.code.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.codemanage.code.dto.request.CdRSaveDto;
import com.codemanage.code.entity.CdR;
import com.codemanage.common.entity.BaseResult;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * CVCD_编码_关系表 服务类
 * </p>
 *
 * @author hyh
 * @since 2022-06-07
 */
public interface ICdRService extends IService<CdR> {

    /**
     * 保存关联关系
     * @param hlvCvCdId
     * @param cvCdId
     * @param newBgnDttm
     * @return
     */
    boolean saveCdR(String hlvCvCdId, String cvCdId, Date newBgnDttm);

    /**
     * 查询上级编码id
     * @param llvCvCdId
     * @return
     */
    CdR getByLlbCvCdId(String llvCvCdId);

    /**
     * 删除关联关系
     * @param oldCdR
     * @return
     */
    boolean delCdR(CdR oldCdR, Date oldFnshDttm, Date newBgnDttm);

    /**
     * 更新关联关系
     * @param oldCdR
     * @param hlvCvCdId
     * @param oldFnshDttm
     * @param newBgnDttm
     * @return
     */
    boolean updateCdR(CdR oldCdR, String hlvCvCdId, Date oldFnshDttm, Date newBgnDttm);


    /**
     * 添加编码编码关系
     * @param dto
     * @return
     */
    BaseResult addCdR(CdRSaveDto dto);

    /**
     * 删除编码编码关系
     * @param dtoList
     * @return
     */
    BaseResult delCdR(List<CdRSaveDto> dtoList);

    CdR getById(String hlvCvCdId, String llvCvCdId);
}
