package com.codemanage.code.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.codemanage.code.dto.request.*;
import com.codemanage.code.dto.response.*;
import com.codemanage.code.entity.CdM;
import com.codemanage.common.entity.BaseResult;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * CVCD_编码_主表 服务类
 * </p>
 *
 * @author hyh
 * @since 2022-06-02
 */
public interface ICdMService extends IService<CdM> {

    /**
     * 分页查询编码
     * @param dto
     * @return
     */
    IPage<CdMVo> getCdMByPage(CdMQueryDto dto);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    CdMVo getCdMById(String id);

    /**
     * 添加编码
     * @param dto
     * @return
     */
    BaseResult addCdM(CdMSaveDto dto);

    /**
     * 编辑编码
     * @param dto
     * @return
     */
    BaseResult editCdM(CdMSaveDto dto);

    boolean delBatchCdM(List<String> ids);

    /**
     * 查询编码属性列表
     * @param dto
     * @return
     */
    List<CdPptyMVo> getCdPptyMList(CdPptyMQueryDto dto);

    /**
     * 查询编码属性值
     * @param dto
     * @return
     */
    @Deprecated
    List<Map<String, String>> getPtyvList(CdPptyMQueryDto dto);

    Map<String, Object> getCdPtyvList(CdPptyMQueryDto dto);

    /**
     * 查询编码编码关系
     * @param dto
     * @return
     */
    List<CodeCodeRelationVo> getCodeCodeRelation(CdPptyMQueryDto dto);

    /**
     * 查询编码属性关系
     * @param dto
     * @return
     */
    List<CodeAttributeRelationVo> getCodeAttributeRelation(CdPptyMQueryDto dto);

    /**
     * 查询编码值关系
     * @param dto
     * @return
     */
    Map<String, Object> getCodeValueRelation(CdPptyRQueryDto dto);

    /**
     * 查询编码表关系
     * @param dto
     * @return
     */
    List<CodeTableRelationVo> getCodeTableRelation(CdPptyMQueryDto dto);

    /**
     * 查询编码属性列关系
     * @param dto
     * @return
     */
    List<CodeAttrColumnRelationVo> getCodeAttrColumnRelation(CodeAttrColumnRQueryDto dto);

}
