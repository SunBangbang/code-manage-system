package com.codemanage.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.codemanage.system.dto.request.SysSchmRSaveDto;
import com.codemanage.system.entity.SysSchmR;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * CVSO_系统数据库模式_关系表 服务类
 * </p>
 *
 * @author hyh
 * @since 2022-06-16
 */
public interface ISysSchmRService extends IService<SysSchmR> {

    /**
     * 保存系统与模式关系
     * @param sysId
     * @param dbmsId
     * @param schmId
     * @param newBgnDttm
     * @return
     */
    boolean addObj(String sysId, String dbmsId, String schmId, Date newBgnDttm);

    /**
     * 更新系统与模式关系
     * @param cvSysId
     * @param schmList
     * @param oldFnshDttm
     * @param newBgnDttm
     * @return
     */
    boolean updateSysSchmR(String cvSysId, List<SysSchmRSaveDto> schmList, Date oldFnshDttm, Date newBgnDttm);

    /**
     * 删除系统与模式关系
     * @param sysId
     * @param oldFnshDttm
     * @param newBgnDttm
     * @return
     */
    boolean delSysSchmR(String sysId, Date oldFnshDttm, Date newBgnDttm);

    /**
     * 删除dbms时需删除的sysSchmR
     * @param dbmsId
     * @param oldFnshDttm
     * @param newBgnDttm
     * @return
     */
    boolean delByDbms(String dbmsId, Date oldFnshDttm, Date newBgnDttm);

    /**
     * 模式修改时修改系统与模式关联关系
     * @param dto
     * @param oldFnshDttm
     * @param bgnDttm
     * @return
     */
//    boolean editBySchm(SchmMSaveDto dto, Date oldFnshDttm, Date bgnDttm);

    /**
     * 删除schm时需删除的sysSchmR
     * @param dbmsId
     * @param oldFnshDttm
     * @param newBgnDttm
     * @return
     */
    boolean delBySchm(String dbmsId, String schmId, Date oldFnshDttm, Date newBgnDttm);
}
