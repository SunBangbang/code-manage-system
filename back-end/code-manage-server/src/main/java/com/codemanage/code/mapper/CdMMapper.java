package com.codemanage.code.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.codemanage.code.dto.request.CdMQueryDto;
import com.codemanage.code.dto.request.CdPptyMQueryDto;
import com.codemanage.code.dto.response.CdMVo;
import com.codemanage.code.dto.response.CodeAttributeRelationVo;
import com.codemanage.code.dto.response.CodeCodeRelationVo;
import com.codemanage.code.entity.CdM;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * CVCD_编码_主表 Mapper 接口
 * </p>
 *
 * @author hyh
 * @since 2022-06-02
 */
@Mapper
public interface CdMMapper extends BaseMapper<CdM> {

    /**
     * 分页查询编码
     * @param page
     * @param dto
     * @return
     */
    IPage<CdMVo> listCdM(IPage<CdMVo> page, @Param("dto") CdMQueryDto dto);

    /**
     * 根据id查询编码
     * @param id
     * @return
     */
    CdMVo getCdMById(@Param("id") String id);

    /**
     * 查询编码编码关系
     * @param dto
     * @return
     */
    List<CodeCodeRelationVo> getCodeCodeRelation(@Param("dto") CdPptyMQueryDto dto);

    /**
     * 查询编码属性关系
     * @param dto
     * @return
     */
    List<CodeAttributeRelationVo> getCodeAttributeRelation(@Param("dto") CdPptyMQueryDto dto);
}
