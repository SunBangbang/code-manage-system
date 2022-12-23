package com.codemanage.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.codemanage.system.dto.request.DbmsQueryDto;
import com.codemanage.system.dto.request.SchmMQueryDto;
import com.codemanage.system.dto.response.DbmsTreeDto;
import com.codemanage.system.dto.response.SchmMVo;
import com.codemanage.system.entity.SchmM;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * CVSO_数据库模式_主表 Mapper 接口
 * </p>
 *
 * @author hyh
 * @since 2022-06-16
 */
@Mapper
public interface SchmMMapper extends BaseMapper<SchmM> {

    /**
     * 根据dbmsId查询Schm
     * @param dbmsId
     * @return
     */
    List<SchmMVo> getSchmByDbmsId(@Param("dbmsId") String dbmsId);

    /**
     * 根据条件查询Schm
     * @param dto
     * @return
     */
    List<SchmMVo> getSchmByCondition(@Param("dto") SchmMQueryDto dto);

    /**
     * 根据id查询模式
     * @param dto
     * @return
     */
    SchmMVo getSchmById(@Param("dto") SchmMQueryDto dto);

    /**
     * 校验schm名称
     * @param dto
     * @return
     */
    int checkSchmNm(@Param("dto") SchmMQueryDto dto);

    List<DbmsTreeDto> getDbmsTreeList(@Param("dto") DbmsQueryDto dto);

    /**
     * 获取排序序号
     * @param dbmsId
     * @return
     */
    Integer getSortSrno(@Param("dbmsId") String dbmsId);

    /**
     * 获取schmId
     * @return
     */
    String getSchmId();

    /**
     * 编辑弹窗-查询详情
     * @param dto
     * @return
     */
    SchmMVo getSchmEditById(@Param("dto") SchmMQueryDto dto);
}
