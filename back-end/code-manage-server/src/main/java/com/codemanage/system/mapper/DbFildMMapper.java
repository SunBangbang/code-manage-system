package com.codemanage.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.codemanage.system.dto.request.DbFildMQueryDto;
import com.codemanage.system.dto.request.DbFildQueryDto;
import com.codemanage.system.dto.request.DbmsQueryDto;
import com.codemanage.system.dto.response.DbFildMVo;
import com.codemanage.system.dto.response.DbmsTreeDto;
import com.codemanage.system.entity.DbFildM;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * CVSO_数据库字段_主表 Mapper 接口
 * </p>
 *
 * @author hyh
 * @since 2022-07-19
 */
@Mapper
public interface DbFildMMapper extends BaseMapper<DbFildM> {

    /**
     * 根据条件查询可用的专栏
     * @param dto
     * @return
     */
    List<DbFildMVo> getFildByCondition(@Param("dto") DbFildMQueryDto dto);

    /**
     * 根据id查询专栏
     * @param dto
     * @return
     */
    DbFildMVo getFildById(@Param("dto") DbFildMQueryDto dto);

    List<DbmsTreeDto> getDbmsTreeList(@Param("dto") DbmsQueryDto dto);

    /**
     * 校验fild名称
     * @param dto
     * @return
     */
    int checkFildNm(@Param("dto") DbFildMQueryDto dto);

    /**
     * 获取排序序号
     * @param dbmsId
     * @param schmId
     * @param tabId
     * @return
     */
    Integer getSortSrno(@Param("dbmsId") String dbmsId, @Param("schmId") String schmId, @Param("tabId") String tabId);

    /**
     * 获取字段Id
     * @return
     */
    String getFildId();

    /**
     * 编辑弹窗查询详情
     * @param dto
     * @return
     */
    DbFildMVo getFildEditById(@Param("dto") DbFildMQueryDto dto);

    List<DbFildMVo> getFildByCodeAttrColumn(@Param("dto")DbFildQueryDto dto);
}
