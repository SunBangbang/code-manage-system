package com.codemanage.code.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.codemanage.code.dto.request.CdvlRQueryDto;
import com.codemanage.code.dto.response.CdvlRVo;
import com.codemanage.code.entity.CdvlR;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * CVCD_编码值_关系表 Mapper 接口
 * </p>
 *
 * @author hyh
 * @since 2022-08-16
 */
@Mapper
public interface CdvlRMapper extends BaseMapper<CdvlR> {

    List<CdvlRVo> getCdvlR(@Param("dto") CdvlRQueryDto dto);
}
