package com.codemanage.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.codemanage.common.entity.BaseResult;
import com.codemanage.system.dto.request.DbTabMQueryDto;
import com.codemanage.system.dto.request.DbTabMSaveDto;
import com.codemanage.system.dto.request.DbmsQueryDto;
import com.codemanage.system.dto.response.DbTabMVo;
import com.codemanage.system.dto.response.DbmsTreeDto;
import com.codemanage.system.entity.DbTabM;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * CVSO_数据库表_主表 服务类
 * </p>
 *
 * @author hyh
 * @since 2022-06-16
 */
public interface IDbTabMService extends IService<DbTabM> {

    /**
     * 根据条件查询可用的平台
     * @param dto
     * @return
     */
    List<DbTabMVo> getTableByCondition(DbTabMQueryDto dto);

    /**
     * 根据id查询平台
     * @param dto
     * @return
     */
    DbTabMVo getTableById(DbTabMQueryDto dto);

    List<DbmsTreeDto> getDbmsTreeList(DbmsQueryDto dto);

    /**
     * 删除dbms时需删除的tab
     * @param dbmsId
     * @param oldFnshDttm
     * @param newBgnDttm
     * @return
     */
    boolean delByDbms(String dbmsId, Date oldFnshDttm, Date newBgnDttm);

    /**
     * 删除schm时需删除的tab
     * @param dbmsId
     * @param schmId
     * @param oldFnshDttm
     * @param newBgnDttm
     * @return
     */
    boolean delBySchm(String dbmsId, String schmId, Date oldFnshDttm, Date newBgnDttm);

    /**
     * 校验table名称
     * @param dto
     * @return
     */
    boolean checkTabNm(DbTabMQueryDto dto);

    /**
     * 获取排序序号
     * @param dbmsId
     * @param schmId
     * @return
     */
    Integer getSortSrno(String dbmsId, String schmId);

    /**
     * 添加table
     * @param dto
     * @return
     */
    BaseResult addTab(DbTabMSaveDto dto);

    /**
     * 编辑弹窗查询详情
     * @param dto
     * @return
     */
    DbTabMVo getTabEditById(DbTabMQueryDto dto);

    /**
     * 编辑table
     * @param dto
     * @return
     */
    BaseResult editTab(DbTabMSaveDto dto);

    /**
     * 删除tab
     * @param dto
     * @return
     */
    boolean delTab(DbTabMQueryDto dto);
}
