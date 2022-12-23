package com.codemanage.system.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codemanage.system.dto.request.RoleQueryDto;
import com.codemanage.system.dto.request.UserBasiMDto;
import com.codemanage.system.dto.response.DeptTreeDto;
import com.codemanage.system.dto.response.RoleVo;
import com.codemanage.system.dto.response.UserBasiMVo;
import com.codemanage.system.entity.UserBasiM;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * CVSO_用户基础_主表 Mapper 接口
 * </p>
 *
 * @author hyh
 * @since 2022-06-02
 */
@Mapper
public interface UserBasiMMapper extends BaseMapper<UserBasiM> {


    /**
     * 查询部门树
     * @return
     */
    List<DeptTreeDto> getDeptAndUserTree();

    /**
     * 查询部门树
     * @return
     */
    List<DeptTreeDto> getDeptTree();

    /**
     * 查询部门下用户
     * @param map
     * @return
     */
    List<UserBasiMVo> getUserListByDept(@Param("dto") Map<String, String> map);

    /**
     * 查询当前节点及字节点（类型为部门和用户）
     * @param id
     * @return
     */
    List<DeptTreeDto> getDeptTreeListById(@Param("id") String id);

    /**
     * 插入数据记录最终删除者信息
     * @param oldFnshDttm
     * @param newBgnDttm
     * @param idList
     * @param createUserId
     * @return
     */
    @Deprecated
    boolean insertByDeptDelete(@Param("oldFnshDttm") String oldFnshDttm, @Param("newBgnDttm")String newBgnDttm,
                               @Param("idList")List<String> idList, @Param("createUserId") String createUserId);

    /**
     * 查询用户列表
     * @param dto
     * @return
     */
    List<UserBasiMVo> getUserBasiMList(@Param("dto") UserBasiMDto dto);

    /**
     * 查询用户Vo
     * @param id
     * @return
     */
    UserBasiMVo getVoById(@Param("id") String id);

    /**
     * 检查登录id是否唯一
     * @param lgnId
     * @return
     */
    int checkLgnId(@Param("lgnId") String lgnId);

    /**
     * 检查用户id是否唯一
     * @param userId
     * @return
     */
    int checkUserId(@Param("userId") String userId);

    /**
     * 查询当前部门下级的所有用户信息
     * @param deptId
     * @return
     */
    List<UserBasiMVo> getSubordinateUserByDeptId(@Param("deptId") String deptId);

    /**
     * 查询所有角色信息
     * @return
     */
    List<RoleVo> getAllRole();

    /**
     * 校验角色名称是否重复
     * @param roleName
     * @return
     */
    int checkRoleName(@Param("roleName") String roleName);

    /**
     * 插入数据记录最终删除者信息
     * @param oldFnshDttm
     * @param newBgnDttm
     * @param id
     * @return
     */
    @Deprecated
    boolean insertByRoleDelete(@Param("oldFnshDttm") String oldFnshDttm, @Param("newBgnDttm")String newBgnDttm,
                               @Param("id") String id, @Param("createUserId") String createUserId);


    /**
     * 查询角色下的用户
     * @param dto
     * @return
     */
    Page<UserBasiMVo> getUserPageByRole(Page<UserBasiMVo> page, @Param("dto") RoleQueryDto dto);

    /**
     *
     * @param userId
     * @return
     */
    List<RoleVo> getRoleByUserId(@Param("userId") String userId);

    RoleVo getRoleById(@Param("id")String id);
}
