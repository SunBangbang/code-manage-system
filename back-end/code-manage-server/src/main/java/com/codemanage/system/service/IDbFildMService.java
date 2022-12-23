package com.codemanage.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.codemanage.common.entity.BaseResult;
import com.codemanage.system.dto.request.DbFildMQueryDto;
import com.codemanage.system.dto.request.DbFildMSaveDto;
import com.codemanage.system.dto.request.DbFildQueryDto;
import com.codemanage.system.dto.request.DbmsQueryDto;
import com.codemanage.system.dto.response.DbFildMVo;
import com.codemanage.system.dto.response.DbmsTreeDto;
import com.codemanage.system.entity.DbFildM;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * CVSO_数据库字段_主表 服务类
 * </p>
 *
 * @author hyh
 * @since 2022-07-19
 */
public interface IDbFildMService extends IService<DbFildM> {

    /**
     * 根据条件查询可用的专栏
     * @param dto
     * @return
     */
    List<DbFildMVo> getFildByCondition(DbFildMQueryDto dto);

    /**
     * 根据id查询专栏
     * @param dto
     * @return
     */
    DbFildMVo getFildById(DbFildMQueryDto dto);

    List<DbmsTreeDto> getDbmsTreeList(DbmsQueryDto dto);

    /**
     * 删除dbms时需删除的fild
     * @param dbmsId
     * @param oldFnshDttm
     * @param newBgnDttm
     * @return
     */
    boolean delByDbms(String dbmsId, Date oldFnshDttm, Date newBgnDttm);

    /**
     * 删除schm时需删除的fild
     * @param dbmsId
     * @param oldFnshDttm
     * @param newBgnDttm
     * @return
     */
    boolean delBySchm(String dbmsId, String schmId, Date oldFnshDttm, Date newBgnDttm);

    /**
     * 删除tab时需删除的fild
     * @param dbmsId
     * @param oldFnshDttm
     * @param newBgnDttm
     * @return
     */
    boolean delByTab(String dbmsId, String schmId, String tabId, Date oldFnshDttm, Date newBgnDttm);

    /**
     * 校验fild名称
     * @param dto
     * @return
     */
    boolean checkFildNm(DbFildMQueryDto dto);

    /**
     * 获取排序序号
     * @param dbmsId
     * @param schmId
     * @param tabId
     * @return
     */
    Integer getSortSrno(String dbmsId, String schmId, String tabId);

    /**
     * 添加Fild
     * @param dto
     * @return
     */
    BaseResult addFild(DbFildMSaveDto dto);

    /**
     * 编辑弹窗查询详情
     * @param dto
     * @return
     */
    DbFildMVo getFildEditById(DbFildMQueryDto dto);

    /**
     * 编辑Fild
     * @param dto
     * @return
     */
    BaseResult editFild(DbFildMSaveDto dto);

    /**
     * 删除Fild
     * @param dto
     * @return
     */
    boolean delFild(DbFildMQueryDto dto);

    List<DbFildMVo> getFildByCodeAttrColumn(DbFildQueryDto dto);
}
