package com.codemanage.code.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.codemanage.code.dto.request.CdSphrMQueryDto;
import com.codemanage.code.dto.response.CdSphrMVo;
import com.codemanage.code.entity.CdSphrM;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * CVCD_编码领域_主表 Mapper 接口
 * </p>
 *
 * @author hyh
 * @since 2022-06-02
 */
@Mapper
public interface CdSphrMMapper extends BaseMapper<CdSphrM> {

    List<CdSphrMVo> getSphrMList(@Param("dto") CdSphrMQueryDto dto);

    CdSphrMVo getVoById(@Param("id") String id);

    /**
     * 查询当前节点的所有子节点id
     * @param id
     * @return
     */
    List<String> getChildIdList(@Param("id") String id);
}
