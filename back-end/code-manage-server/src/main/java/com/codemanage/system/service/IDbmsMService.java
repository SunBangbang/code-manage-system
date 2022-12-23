package com.codemanage.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.codemanage.common.entity.BaseResult;
import com.codemanage.system.dto.request.DbmsQueryDto;
import com.codemanage.system.dto.request.DbmsSaveDto;
import com.codemanage.system.dto.response.DbmsMVo;
import com.codemanage.system.dto.response.DbmsTreeDto;
import com.codemanage.system.entity.DbmsM;

import java.util.List;

/**
 * <p>
 * CVSO_数据库管理系统_主表 服务类
 * </p>
 *
 * @author hyh
 * @since 2022-06-16
 */
public interface IDbmsMService extends IService<DbmsM> {

    /**
     * 查询所有DBMS
     * @return
     */
    List<DbmsMVo> getAllDbms();

    /**
     * 查询状态为Y的DBMS
     * @return
     */
    List<DbmsMVo> getDbmsList();

    /**
     * 根据id查询DBMS
     * @param dbmsId
     * @param recBgnDttm
     * @return
     */
    DbmsMVo getDbmsById(String dbmsId, String recBgnDttm);

    /**
     * 查询DBMS树结构
     * @param dto
     * @return
     */
    List<DbmsTreeDto> getDbmsTree(DbmsQueryDto dto);

    /**
     * 校验DBMS名称
     * @param dbmsNm
     * @return
     */
    boolean checkDdmsNm(String dbmsNm);

    /**
     * 获取排序序号
     * @return
     */
    Integer getSortSrno();

    /**
     * 添加DBMS
     * @param dto
     * @return
     */
    BaseResult addDbms(DbmsSaveDto dto);

    /**
     * 编辑DBMS
     * @param dto
     * @return
     */
    BaseResult editDbms(DbmsSaveDto dto);

    /**
     * 删除DBMS
     * @param dbmsId
     * @return
     */
    boolean delDbms(String dbmsId, String recBgnDttm);
}
