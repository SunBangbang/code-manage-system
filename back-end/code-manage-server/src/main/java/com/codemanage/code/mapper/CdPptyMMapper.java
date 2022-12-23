package com.codemanage.code.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.codemanage.code.dto.request.CdPptyMQueryDto;
import com.codemanage.code.dto.response.CdPptyMVo;
import com.codemanage.code.entity.CdPptyM;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * CVCD_编码属性_主表 Mapper 接口
 * </p>
 *
 * @author hyh
 * @since 2022-06-07
 */
@Mapper
public interface CdPptyMMapper extends BaseMapper<CdPptyM> {

    /**
     * 查询编码属性列表
     * @param dto
     * @return
     */
    List<CdPptyMVo> getCdPptyMList(@Param("dto") CdPptyMQueryDto dto);

    Map<String, String> getTableMap(@Param("sql") String sql);
}
