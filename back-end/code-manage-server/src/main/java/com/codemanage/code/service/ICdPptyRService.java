package com.codemanage.code.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.codemanage.code.dto.request.CdPptyRAutoSaveDto;
import com.codemanage.code.dto.request.CdPptyRQueryDto;
import com.codemanage.code.dto.request.CdPptyRSaveDto;
import com.codemanage.code.dto.response.CdPptyRVo;
import com.codemanage.code.entity.CdPptyR;
import com.codemanage.common.entity.BaseResult;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * CVCD_编码属性_关系表 服务类
 * </p>
 *
 * @author hyh
 * @since 2022-06-07
 */
public interface ICdPptyRService extends IService<CdPptyR> {

    /**
     * 查询映射关系
     * @param dto
     * @return
     */
    List<CdPptyRVo> getPptyR(CdPptyRQueryDto dto);

    /**
     * 添加映射
     * @param dto
     * @return
     */
    BaseResult addPptyR(CdPptyRSaveDto dto);

    /**
     * 自动映射
     * @param dto
     * @return
     */
    BaseResult addBatchPptyR(CdPptyRAutoSaveDto dto);

    /**
     * 删除映射
     * @param dtoList
     * @return
     */
    BaseResult delBatchPptyR(List<CdPptyRSaveDto> dtoList);

    /**
     * 查询编码关系数量
     * @param dto
     * @return
     */
    int getCount(CdPptyRQueryDto dto);

    /**
     * 编码值关系表头
     * @param count
     * @param dto
     * @return
     */
    Map<String, String> getTableMap(int count, CdPptyRQueryDto dto);

    /**
     * 编码值关系数据
     * @param count
     * @param dto
     * @return
     */
    List<Map<String, String>> getDataListMap(int count, CdPptyRQueryDto dto);

    /**
     * 删除编码属性时删除编码属性的关联关系
     * @param cvCdId
     * @param cvCdPptyId
     * @param oldFnshDttm
     * @param newBgnDttm
     * @return
     */
    boolean delByPptyM(String cvCdId, String cvCdPptyId, Date oldFnshDttm, Date newBgnDttm);
}
