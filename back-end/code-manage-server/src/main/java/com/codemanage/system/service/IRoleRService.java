package com.codemanage.system.service;

import com.codemanage.system.entity.RoleR;
import com.baomidou.mybatisplus.extension.service.IService;
import com.codemanage.system.entity.UserBasiM;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * CVSO_角色_关系表 服务类
 * </p>
 *
 * @author hyh
 * @since 2022-07-09
 */
public interface IRoleRService extends IService<RoleR> {

    /**
     * 更新关系状态为不可以
     * @param hlvCvUserId  上级id
     * @param cvUserId     下级id
     * @param oldFnshDttm
     * @return
     */
    boolean updateFnlYn(String hlvCvUserId, String cvUserId, Date oldFnshDttm);

    /**
     * 批量更新关系为不可用
     * @param oldFnshDttm
     * @param idList
     * @return
     */
    @Deprecated
    boolean updateBatchFnlYn(Date oldFnshDttm, List<String> idList);

    /**
     *  插入关系数据记录最终删除者信息
     * @param oldFnshDttm
     * @param newBgnDttm
     * @param idList
     * @return
     */
    boolean delDept(Date oldFnshDttm, Date newBgnDttm, List<String> idList);

    /**
     * 保存关联关系
     * @param hlvObj
     * @param obj
     * @param bgnDttm
     * @return
     */
    boolean saveRoleR(UserBasiM hlvObj, UserBasiM obj, Date bgnDttm);

    /**
     * 根据角色id查询角色和用户关联关系
     * @param id
     * @return
     */
    @Deprecated
    List<RoleR> getUserRoleByRoleId(String id);

    /**
     * 删除角色关联所有用户关系
     * @param oldFnshDttm
     * @param id
     * @return
     */
//    @Deprecated
//    boolean updateRoleRelation(Date oldFnshDttm, String id);

    /**
     * 插入数据记录最终删除者信息
     * @param oldFnshDttm
     * @param newBgnDttm
     * @param id
     * @return
     */
//    @Deprecated
//    boolean insertByRoleDelete(Date oldFnshDttm, Date newBgnDttm, String id);

    /**
     * 删除角色关联所有用户关系
     * @param oldFnshDttm
     * @param newBgnDttm
     * @param roleId
     * @return
     */
    boolean delRole(Date oldFnshDttm, Date newBgnDttm, String roleId);

    /**
     * 查询用户绑定的角色id
     * @param userId
     * @return
     */
    List<String> getRoleIdsByUserId(String userId);


    /**
     * 查询角色绑定的用户id
     * @param roleId
     * @return
     */
    List<String> getUserIdsByRoleId(String roleId);

    /**
     * 删除用户和角色关联关系
     * @param userId
     * @param delRoleIds
     * @param oldFnshDttm
     * @param newBgnDttm
     * @return
     */
    boolean delUserRole(String userId, List<String> delRoleIds, Date oldFnshDttm, Date newBgnDttm);

    /**
     * 添加用户和角色关联关系
     * @param userId
     * @param addRoleIds
     * @param newBgnDttm
     * @return
     */
    boolean addUserRole(String userId, List<String> addRoleIds, Date newBgnDttm);

    /**
     * 添加用户和部门关联关系
     * @param userId
     * @param deptId
     * @param newBgnDttm
     * @return
     */
    boolean addUserDept(String userId, String deptId, Date newBgnDttm);

    /**
     * 删除用户时关系表的操作
     * @param id
     * @param oldFnshDttm
     * @param newBgnDttm
     * @return
     */
    boolean delUser(String id, Date oldFnshDttm, Date newBgnDttm);

    /**
     * 添加角色和用户关联关系
     * @param roleId
     * @param addUserIds
     * @param newBgnDttm
     * @return
     */
    boolean addRoleUserRelation(String roleId, List<String> addUserIds, Date newBgnDttm);

    /**
     * 删除角色和用户关联关系
     * @param roleId
     * @param userIdList
     * @param oldFnshDttm
     * @param newBgnDttm
     * @return
     */
    boolean delRoleUserRelation(String roleId, List<String> userIdList, Date oldFnshDttm, Date newBgnDttm);
}
