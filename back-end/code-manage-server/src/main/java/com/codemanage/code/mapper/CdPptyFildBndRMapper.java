package com.codemanage.code.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.codemanage.code.dto.request.CdPptyMQueryDto;
import com.codemanage.code.dto.request.CodeAttrColumnRQueryDto;
import com.codemanage.code.dto.response.CdPptyFildBndRVo;
import com.codemanage.code.dto.response.CodeAttrColumnRelationVo;
import com.codemanage.code.entity.CdPptyFildBndR;
import com.codemanage.code.entity.CdPptyM;
import com.codemanage.system.dto.request.DbFildMQueryDto;
import com.codemanage.system.entity.DbFildM;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * CVCD_编码属性字段绑定_关系表 Mapper 接口
 * </p>
 *
 * @author hyh
 * @since 2022-08-17
 */
@Mapper
public interface CdPptyFildBndRMapper extends BaseMapper<CdPptyFildBndR> {

    List<CodeAttrColumnRelationVo> getCodeAttrColumnRelation(@Param("dto") CodeAttrColumnRQueryDto dto);

    List<CdPptyFildBndRVo> getPptyFildBndR(@Param("dto") CodeAttrColumnRQueryDto dto);

    List<CdPptyM> getMappingLeftData(@Param("dto") CdPptyMQueryDto dto);

    List<DbFildM> getMappingRightData(@Param("dto") DbFildMQueryDto dto);
}
