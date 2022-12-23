package com.codemanage.code.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.codemanage.code.dto.request.CdPptyMDelDto;
import com.codemanage.code.dto.request.CdPptyMQueryDto;
import com.codemanage.code.dto.request.CdPptyMSaveDto;
import com.codemanage.code.dto.response.CdPptyMVo;
import com.codemanage.code.entity.CdPptyM;
import com.codemanage.common.entity.BaseResult;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * CVCD_编码属性_主表 服务类
 * </p>
 *
 * @author hyh
 * @since 2022-06-07
 */
public interface ICdPptyMService extends IService<CdPptyM> {

    /**
     * 查询编码属性列表
     * @param dto
     * @return
     */
    List<CdPptyMVo> getCdPptyMList(CdPptyMQueryDto dto);

    /**
     * 添加编码属性
     * @param dto
     * @return
     */
    BaseResult addCdPptyM(CdPptyMSaveDto dto);

    /**
     * 编辑编码属性
     * @param dto
     * @return
     */
    BaseResult editCdPptyM(CdPptyMSaveDto dto);

    /**
     * 批量删除编码属性
     * @param delDto
     * @return
     */
    boolean delBatchCdPptyM(CdPptyMDelDto delDto);

    /**
     * 删除编码属性
     * @param id
     * @return
     */
    boolean delCdPptyMById(String id);

    /**
     * 批量删除编码属性
     * @param ids
     * @return
     */
    boolean delBatchCdPptyMByIds(List<String> ids);

    Map<String, String> getTableMap(int count, CdPptyMQueryDto dto);

    List<CdPptyM> getListByCdId(String cvCdId);

    CdPptyM getObjById(String cvcdId, String pptyId);

}
