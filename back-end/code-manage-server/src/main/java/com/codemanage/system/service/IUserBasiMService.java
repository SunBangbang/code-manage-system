package com.codemanage.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codemanage.common.entity.BaseResult;
import com.codemanage.system.dto.request.DeptSaveDto;
import com.codemanage.system.dto.request.RoleQueryDto;
import com.codemanage.system.dto.request.RoleSaveDto;
import com.codemanage.system.dto.request.UserBasiMDto;
import com.codemanage.system.dto.response.DeptTreeDto;
import com.codemanage.system.dto.response.RoleVo;
import com.codemanage.system.dto.response.UserBasiMVo;
import com.codemanage.system.entity.UserBasiM;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * CVSO_用户基础_主表 服务类
 * </p>
 *
 * @author hyh
 * @since 2022-05-31
 */
public interface IUserBasiMService extends IService<UserBasiM> {


    /**
     * 查询部门和用户树
     * @return
     */
    List<DeptTreeDto> getDeptAndUserTree();
/**
     * 查询部门树
     * @return
     */
    List<DeptTreeDto> getDeptTree();

    /**
     * 根据部门id查询部门下的用户
     * @param map
     * @return
     */
    List<UserBasiMVo> getUserListByDept(Map<String, String> map);

    /**
     * 添加用户
     * @param dto
     * @return
     */
    BaseResult addDept(DeptSaveDto dto);

    /**
     * 编辑用户
     * @param dto
     * @return
     */
    BaseResult editDept(DeptSaveDto dto);


    /**
     * 删除部门
     * @param id
     * @return
     */
    boolean delDept(String id);


    /**
     * 查询用户列表
     * @param dto
     * @return
     */
    List<UserBasiMVo> getUserBasiMList(UserBasiMDto dto);


    /**
     * 查询用户Vo
     * @param id
     * @return
     */
    UserBasiMVo getVoById(String id);

    /**
     * 检查登录id是否唯一
     * @param lgnId
     * @return
     */
    boolean checkLgnId(String lgnId);

    /**
     * 添加用户
     * @param dto
     * @return
     */
    BaseResult addUserBasiM(UserBasiMDto dto);

    /**
     * 编辑用户
     * @param dto
     * @return
     */
    BaseResult editUserBasiM(UserBasiMDto dto);

    /**
     * 删除用户
     * @param id
     * @return
     */
    boolean delUser(String id);


    /**
     * 用户绑定角色
     * @param dto
     * @return
     */
    BaseResult addUserRole(UserBasiMDto dto);

    /**
     * 初始化密码
     * @param id
     * @return
     */
    boolean initPassword(String id);


    /**
     * 解锁用户
     * @param id
     * @return
     */
    boolean unlockUser(String id);

    /**
     * 检查用户id是否唯一
     * @param userId
     * @return
     */
    boolean checkUserId(String userId);


    /**
     * 根据登录id查询用户
     * @param lgnId
     * @return
     */
    UserBasiM getByLgnId(String lgnId);

    /**
     * 根据id查询用户
     * @param id
     * @param fnshDttm
     * @return
     */
    UserBasiM getObjById(String id, Date fnshDttm);

    /**
     * 更新用户锁定状态编码
     * @param lgnId
     * @param code
     * @return
     */
    boolean updateCvBnacLckStCd(String lgnId, String code);

    /**
     * 查询当前部门下级的所有用户信息
     * @return
     */
    List<UserBasiMVo> getSubordinateUserByDeptId(String deptId);

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
    boolean checkRoleName(String roleName);

    /**
     * 添加角色
     * @param dto
     * @return
     */
    BaseResult addRole(RoleSaveDto dto);

    /**
     * 编辑角色
     * @param dto
     * @return
     */
    BaseResult editRole(RoleSaveDto dto);

    /**
     * 删除角色
     * @param id
     * @return
     */
    boolean delRole(String id);

    /**
     * 分页查询角色下的用户
     * @param dto
     * @return
     */
    Page<UserBasiMVo> getUserPageByRole(RoleQueryDto dto);

    /**
     * 新增角色和用户关系
     * @param roleId
     * @param userId
     * @return
     */
    @Deprecated
    boolean addRoleUserRelation(String roleId, String userId);

    /**
     * 查询用户已绑定角色
     * @param userId
     * @return
     */
    List<RoleVo> getRoleByUserId(String userId);

    /**
     * 根据id查询角色
     * @param id
     * @return
     */
    RoleVo getRoleById(String id);

    /**
     *
     * @param dto
     * @return
     */
    boolean addBatchRoleUserRelation(RoleSaveDto dto);

    /**
     *
     * @param dto
     * @return
     */
    boolean delBatchRoleUserRelation(RoleSaveDto dto);

    /**
     * 查询用户菜单
     * @return
     */
    BaseResult getUserMenu();
}
