package com.codemanage.code.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.codemanage.code.dto.request.CdPptyMQueryDto;
import com.codemanage.code.dto.response.CdPtyvMTempVo;
import com.codemanage.code.entity.CdPtyvM;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * CVCD_编码属性值_主表 Mapper 接口
 * </p>
 *
 * @author hyh
 * @since 2022-08-02
 */
@Mapper
public interface CdPtyvMMapper extends BaseMapper<CdPtyvM> {

    @Deprecated
    List<CdPtyvMTempVo> getTempVoList(@Param("dto") CdPptyMQueryDto dto);

    List<Map<String, String>> getDataListMap(@Param("sql")String sql);
}
