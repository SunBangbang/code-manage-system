package com.codemanage.system.mapper;

import com.codemanage.system.entity.RoleR;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * CVSO_角色_关系表 Mapper 接口
 * </p>
 *
 * @author hyh
 * @since 2022-07-09
 */
@Mapper
public interface RoleRMapper extends BaseMapper<RoleR> {

    /**
     * 部门删除-插入关系数据记录最终删除者信息
     * @param oldFnshDttm
     * @param newBgnDttm
     * @param idList
     * @param createUserId
     * @return
     */
    @Deprecated
    boolean insertByDeptDelete(@Param("oldFnshDttm") String oldFnshDttm, @Param("newBgnDttm")String newBgnDttm,
                               @Param("idList") List<String> idList, @Param("createUserId") String createUserId);

    /**
     * 角色删除-插入关系数据记录最终删除者信息
     * @param oldFnshDttm
     * @param newBgnDttm
     * @param id
     * @param createUserId
     * @return
     */
    @Deprecated
    boolean insertByRoleDelete(@Param("oldFnshDttm") String oldFnshDttm, @Param("newBgnDttm")String newBgnDttm,
                               @Param("id")String id, @Param("createUserId") String createUserId);

    /**
     * 查询用户绑定的角色id
     * @param userId
     * @return
     */
    List<String> getRoleIdsByUserId(@Param("userId") String userId);

    /**
     * 查询角色绑定的用户id
     * @param roleId
     * @return
     */
    List<String> getUserIdsByRoleId(@Param("roleId") String roleId);

    /**
     * 删除用户和角色关联关系
     * @param userId
     * @param roleIds
     * @param oldFnshDttm
     * @param newBgnDttm
     * @param createUserId
     * @return
     */
    @Deprecated
    boolean insertByUserRoleDelete(@Param("userId") String userId, @Param("roleIds") List<String> roleIds,
                                   @Param("oldFnshDttm") String oldFnshDttm, @Param("newBgnDttm") String newBgnDttm,

                                   @Param("createUserId") String createUserId);

    /**
     * 插入用户删除数据
     * @param userId
     * @param oldFnshDttm
     * @param newBgnDttm
     * @param createUserId
     * @return
     */
    @Deprecated
    boolean insertByUserDelete(@Param("userId") String userId, @Param("oldFnshDttm") String oldFnshDttm,
                               @Param("newBgnDttm") String newBgnDttm, @Param("createUserId") String createUserId);
}
