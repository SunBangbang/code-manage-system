package com.codemanage.system.service;

import com.codemanage.system.entity.AuthObjBasiM;
import com.codemanage.system.entity.UserRoleR;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * CVSO_用户角色_关系表 服务类
 * </p>
 *
 * @author hyh
 * @since 2022-07-18
 */
public interface IUserRoleRService extends IService<UserRoleR> {

    /**
     * 查询角色关联的菜单
     * @param roleId
     * @return
     */
    List<UserRoleR> getByRoleId(String roleId);

    /**
     * 删除角色和菜单关联关系
     * @param roleId
     * @param delAuthIds
     * @param oldFnshDttm
     * @param newBgnDttm
     * @return
     */
    boolean delRoleAuth(String roleId, List<String> delAuthIds, Date oldFnshDttm, Date newBgnDttm);

    /**
     * 添加角色和菜单关联关系
     * @param roleId
     * @param addAuthList
     * @param newBgnDttm
     * @return
     */
    boolean addRoleAuth(String roleId, List<AuthObjBasiM> addAuthList, Date newBgnDttm);

    /**
     * 查询角色绑定的菜单id
     * @param roleIdList
     * @return
     */
    List<String> getAuthIdByRoleIds(List<String> roleIdList);
}
