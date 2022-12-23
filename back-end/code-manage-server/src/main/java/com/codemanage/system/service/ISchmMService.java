package com.codemanage.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.codemanage.common.entity.BaseResult;
import com.codemanage.system.dto.request.DbmsQueryDto;
import com.codemanage.system.dto.request.SchmMQueryDto;
import com.codemanage.system.dto.request.SchmMSaveDto;
import com.codemanage.system.dto.response.DbmsTreeDto;
import com.codemanage.system.dto.response.SchmMVo;
import com.codemanage.system.entity.SchmM;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * CVSO_数据库模式_主表 服务类
 * </p>
 *
 * @author hyh
 * @since 2022-06-16
 */
public interface ISchmMService extends IService<SchmM> {

    /**
     * 根据dbmsId查询Schm
     * @param dbmsId
     * @return
     */
    List<SchmMVo> getSchmByDbmsId(String dbmsId);

    /**
     * 根据条件查询Schm
     * @param dto
     * @return
     */
    List<SchmMVo> getSchmByCondition(SchmMQueryDto dto);

    /**
     * 根据id查询模式
     * @param dto
     * @return
     */
    SchmMVo getSchmById(SchmMQueryDto dto);

    /**
     * 校验schm名称
     * @param dto
     * @return
     */
    boolean checkSchmNm(SchmMQueryDto dto);

    List<DbmsTreeDto> getDbmsTreeList(DbmsQueryDto dto);

    /**
     * 删除dbms时需删除的schm
     * @param dbmsId
     * @param oldFnshDttm
     * @param newBgnDttm
     * @return
     */
    boolean delByDbms(String dbmsId, Date oldFnshDttm, Date newBgnDttm);

    /**
     * 获取排序序号
     * @param dbmsId
     * @return
     */
    Integer getSortSrno(String dbmsId);

    /**
     * 添加schm
     * @param dto
     * @return
     */
    BaseResult addSchm(SchmMSaveDto dto);

    /**
     * 编辑弹窗-查询详情
     * @param dto
     * @return
     */
    SchmMVo getSchmEditById(SchmMQueryDto dto);

    /**
     * 编辑schm
     * @param dto
     * @return
     */
    BaseResult editSchm(SchmMSaveDto dto);

    /**
     * 删除schm
     * @param dto
     * @return
     */
    boolean delSchm(SchmMQueryDto dto);
}
