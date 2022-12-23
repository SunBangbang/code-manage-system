package com.codemanage.system.mapper;

import com.codemanage.system.dto.response.AuthTreeDto;
import com.codemanage.system.entity.AuthObjBasiM;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * CVSO_权限对象基础_主表 Mapper 接口
 * </p>
 *
 * @author hyh
 * @since 2022-07-16
 */
@Mapper
public interface AuthObjBasiMMapper extends BaseMapper<AuthObjBasiM> {

    /**
     * 查询菜单树
     * @param name
     * @return
     */
    List<AuthTreeDto> getAuthObjTree(@Param("name") String name, @Param("dstsCdList") List<String> dstsCdList);

    /**
     * 检查屏幕id是否唯一
     * @param uiId
     * @return
     */
    int checkUiId(@Param("uiId") String uiId);

    /**
     * 检查菜单名称是否唯一
     * @param name
     * @return
     */
    int checkAuthName(@Param("name") String name);

    /**
     * 查询子字节的id
     * @param id
     * @return
     */
    List<String> getChildIdsById(@Param("id") String id);

    /**
     * 查询使用中的菜单
     * @return
     */
    List<AuthTreeDto> getAuthObjList();
}
