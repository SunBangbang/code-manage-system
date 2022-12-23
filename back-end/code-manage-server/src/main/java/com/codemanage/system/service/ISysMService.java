package com.codemanage.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.codemanage.common.entity.BaseResult;
import com.codemanage.system.dto.request.SysMQueryDto;
import com.codemanage.system.dto.request.SysMSaveDto;
import com.codemanage.system.dto.response.SysMVo;
import com.codemanage.system.entity.SysM;

import java.util.List;

/**
 * <p>
 * CVSO_系统_主表 服务类
 * </p>
 *
 * @author hyh
 * @since 2022-06-16
 */
public interface ISysMService extends IService<SysM> {

    /**
     * 查询系统列表
     * @param dto
     * @return
     */
    List<SysMVo> getSysList(SysMQueryDto dto);

    /**
     * 校验系统名称
     * @param sysNm
     * @return
     */
    boolean checkSysNm(String sysNm);

    /**
     * 获取排序序号
     * @return
     */
    Integer getSortSrno();

    /**
     * 添加系统
     * @param dto
     * @return
     */
    BaseResult addSysM(SysMSaveDto dto);

    /**
     * 根据id查询
     * @param dto
     * @return
     */
    SysMVo getSysMVoById(SysMQueryDto dto);

    /**
     * 编辑系统
     * @param dto
     * @return
     */
    BaseResult editSysM(SysMSaveDto dto);

    /**
     * 删除系统
     * @param sysId
     * @return
     */
    boolean delSysM(String sysId);

    List<SysM> getSysMList();
}
