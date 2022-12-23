package com.codemanage.system.mapper;

import com.codemanage.system.dto.request.SysMQueryDto;
import com.codemanage.system.dto.response.SysMVo;
import com.codemanage.system.entity.SysM;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * CVSO_系统_主表 Mapper 接口
 * </p>
 *
 * @author hyh
 * @since 2022-06-16
 */
@Mapper
public interface SysMMapper extends BaseMapper<SysM> {

    /**
     * 查询系统列表
     * @param dto
     * @return
     */
    List<SysMVo> getSysList(@Param("dto") SysMQueryDto dto);

    /**
     * 校验系统名称
     * @param sysNm
     * @return
     */
    int checkSysNm(@Param("sysNm") String sysNm);

    /**
     * 获取系统id
     * @return
     */
    String getSysId();

    /**
     * 获取可用排序序号
     * @return
     */
    Integer getSortSrno();

    /**
     * 根据id查询
     * @param dto
     * @return
     */
    SysMVo getSysMVoById(@Param("dto") SysMQueryDto dto);
}
