package com.codemanage.code.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.codemanage.code.dto.request.CdvlMDto;
import com.codemanage.code.entity.CdvlM;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * CVCD_编码值_主表 Mapper 接口
 * </p>
 *
 * @author hyh
 * @since 2022-06-07
 */
@Mapper
public interface CdvlMMapper extends BaseMapper<CdvlM> {

    /**
     * 分页查询编码值
     * @param page
     * @param dto
     * @return
     */
    IPage<CdvlM> listCdvlM(IPage<CdvlM> page, @Param("dto") CdvlMDto dto);
}
