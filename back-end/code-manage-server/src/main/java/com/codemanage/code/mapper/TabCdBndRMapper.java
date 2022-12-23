package com.codemanage.code.mapper;

import com.codemanage.code.dto.request.CdPptyMQueryDto;
import com.codemanage.code.dto.response.CodeTableRelationVo;
import com.codemanage.code.entity.TabCdBndR;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * CVCD_表编码绑定_关系表 Mapper 接口
 * </p>
 *
 * @author hyh
 * @since 2022-08-17
 */
@Mapper
public interface TabCdBndRMapper extends BaseMapper<TabCdBndR> {


    List<CodeTableRelationVo> getCodeTableRelation(@Param("dto") CdPptyMQueryDto dto);
}
