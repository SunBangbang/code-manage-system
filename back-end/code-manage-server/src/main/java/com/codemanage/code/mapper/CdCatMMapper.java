package com.codemanage.code.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.codemanage.code.dto.request.CdCatMQueryDto;
import com.codemanage.code.dto.response.CdCatMVo;
import com.codemanage.code.entity.CdCatM;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * CVCD_编码分类_主表 Mapper 接口
 * </p>
 *
 * @author hyh
 * @since 2022-06-02
 */
@Mapper
public interface CdCatMMapper extends BaseMapper<CdCatM> {

    /**
     * 查询编码分类列表
     * @param dto
     * @return
     */
    List<CdCatMVo> getCatMList(@Param("dto") CdCatMQueryDto dto);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    CdCatMVo getVoById(@Param("id") String id);

    /**
     * 查询当前节点的所有子节点id
     * @param id
     * @return
     */
    List<String> getChildIdList(@Param("id") String id);
}
