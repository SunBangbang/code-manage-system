package com.codemanage.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.codemanage.system.dto.request.DbTabMQueryDto;
import com.codemanage.system.dto.request.DbmsQueryDto;
import com.codemanage.system.dto.response.DbTabMVo;
import com.codemanage.system.dto.response.DbmsTreeDto;
import com.codemanage.system.entity.DbTabM;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * CVSO_数据库表_主表 Mapper 接口
 * </p>
 *
 * @author hyh
 * @since 2022-06-16
 */
@Mapper
public interface DbTabMMapper extends BaseMapper<DbTabM> {

    /**
     * 根据条件查询可用的平台
     * @param dto
     * @return
     */
    List<DbTabMVo> getTableByCondition(@Param("dto") DbTabMQueryDto dto);

    /**
     * 根据id查询平台
     * @param dto
     * @return
     */
    DbTabMVo getTableById(@Param("dto") DbTabMQueryDto dto);

    List<DbmsTreeDto> getDbmsTreeList(@Param("dto") DbmsQueryDto dto);

    /**
     * 校验table名称
     * @param dto
     * @return
     */
    int checkTabNm(@Param("dto") DbTabMQueryDto dto);

    /**
     * 获取排序序号
     * @param dbmsId
     * @param schmId
     * @return
     */
    Integer getSortSrno(@Param("dbmsId") String dbmsId, @Param("schmId") String schmId);

    /**
     * 获取Id
     * @return
     */
    String getTabId();

    /**
     * 编辑弹窗查询详情
     * @param dto
     * @return
     */
    DbTabMVo getTabEditById(@Param("dto") DbTabMQueryDto dto);
}
