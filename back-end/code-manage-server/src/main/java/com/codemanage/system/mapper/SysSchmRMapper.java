package com.codemanage.system.mapper;

import com.codemanage.system.entity.SysSchmR;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * CVSO_系统数据库模式_关系表 Mapper 接口
 * </p>
 *
 * @author hyh
 * @since 2022-06-16
 */
@Mapper
public interface SysSchmRMapper extends BaseMapper<SysSchmR> {

    /**
     * 获取排序序号
     * @param sysId
     * @return
     */
    Integer getSortSrno(@Param("sysId") String sysId);
}
