package com.codemanage.code.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.codemanage.code.dto.request.CdPptyRQueryDto;
import com.codemanage.code.dto.response.CdPptyRVo;
import com.codemanage.code.entity.CdPptyR;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * CVCD_编码属性_关系表 Mapper 接口
 * </p>
 *
 * @author hyh
 * @since 2022-06-07
 */
@Mapper
public interface CdPptyRMapper extends BaseMapper<CdPptyR> {

    int getCount(@Param("dto") CdPptyRQueryDto dto);

    Map<String, String> getTableMap(@Param("sql") String sql);

    List<Map<String, String>> getDataListMap(@Param("sql") String dataSql);

    List<CdPptyRVo> getPptyR(@Param("dto") CdPptyRQueryDto dto);
}
