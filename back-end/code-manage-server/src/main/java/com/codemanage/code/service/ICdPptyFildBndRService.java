package com.codemanage.code.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.codemanage.code.dto.request.CdPptyFildBndRSaveDto;
import com.codemanage.code.dto.request.CdPptyMQueryDto;
import com.codemanage.code.dto.request.CodeAttrColumnRQueryDto;
import com.codemanage.code.dto.response.CdPptyFildBndRVo;
import com.codemanage.code.dto.response.CodeAttrColumnRelationVo;
import com.codemanage.code.entity.CdPptyFildBndR;
import com.codemanage.code.entity.CdPptyM;
import com.codemanage.common.entity.BaseResult;
import com.codemanage.system.dto.request.DbFildMQueryDto;
import com.codemanage.system.entity.DbFildM;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * CVCD_编码属性字段绑定_关系表 服务类
 * </p>
 *
 * @author hyh
 * @since 2022-08-17
 */
public interface ICdPptyFildBndRService extends IService<CdPptyFildBndR> {

    /**
     * 查询编码属性列关系
     *
     * @param dto
     * @return
     */
    List<CodeAttrColumnRelationVo> getCodeAttrColumnRelation(CodeAttrColumnRQueryDto dto);


    /**
     * 查询映射关系
     *
     * @param dto
     * @return
     */
    List<CdPptyFildBndRVo> getPptyFildBndR(CodeAttrColumnRQueryDto dto);

    /**
     * 添加映射
     *
     * @param dto
     * @return
     */
    BaseResult addPptyFildBndR(CdPptyFildBndRSaveDto dto);

    /**
     * 自动映射
     *
     * @param dtoList
     * @return
     */
    BaseResult addBatchPptyFildBndR(CdPptyFildBndRSaveDto dtoList);

    /**
     * 删除映射
     *
     * @param dtoList
     * @return
     */
    BaseResult delBatchPptyFildBndR(List<CdPptyFildBndRSaveDto> dtoList);

    /**
     * 删除编码属性时删除编码属性与字段关联关系
     *
     * @param cvCdId
     * @param cvCdPptyId
     * @param oldFnshDttm
     * @param newBgnDttm
     * @return
     */
    boolean delByPptyM(String cvCdId, String cvCdPptyId, Date oldFnshDttm, Date newBgnDttm);

    List<CdPptyM> getMappingLeftData(CdPptyMQueryDto dto);

    List<DbFildM> getMappingRightData(DbFildMQueryDto dto);
}
