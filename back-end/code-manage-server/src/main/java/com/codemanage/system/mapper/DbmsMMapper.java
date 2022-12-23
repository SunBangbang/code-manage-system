package com.codemanage.system.mapper;

import com.codemanage.system.dto.response.DbmsMVo;
import com.codemanage.system.dto.response.DbmsTreeDto;
import com.codemanage.system.entity.DbmsM;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * CVSO_数据库管理系统_主表 Mapper 接口
 * </p>
 *
 * @author hyh
 * @since 2022-06-16
 */
@Mapper
public interface DbmsMMapper extends BaseMapper<DbmsM> {

    /**
     * getAllDbms
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
     * @return
     */
    DbmsMVo getDbmsById(@Param("dbmsId") String dbmsId, @Param("recBgnDttm")String recBgnDttm);

    /**
     * 查询DBMS树结构
     * @return
     */
    @Deprecated
    List<DbmsTreeDto> getDbmsTree();


    List<DbmsTreeDto> getDbmsTreeList();

    /**
     * 校验DBMS名称
     * @param dbmsNm
     * @return
     */
    int checkDdmsNm(@Param("dbmsNm") String dbmsNm);

    /**
     * 获取排序序号
     * @return
     */
    Integer getSortSrno();

    /**
     * 获取dbmsId
     * @return
     */
    String getDbmsId();

    DbmsM getObjById(@Param("dbmsId") String dbmsId, @Param("recBgnDttm")String recBgnDttm);
}
