package com.codemanage.system.service;

import com.codemanage.common.entity.BaseResult;
import com.codemanage.system.dto.request.AuthObjSaveDto;
import com.codemanage.system.dto.response.AuthTreeDto;
import com.codemanage.system.entity.AuthObjBasiM;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * CVSO_权限对象基础_主表 服务类
 * </p>
 *
 * @author hyh
 * @since 2022-07-16
 */
public interface IAuthObjBasiMService extends IService<AuthObjBasiM> {

    /**
     * 查询菜单树
     * @param name
     * @return
     */
    List<AuthTreeDto> getAuthObjTree(String name);

    /**
     * 查询类型为Folder的菜单树
     * @return
     */
    List<AuthTreeDto> getAuthFolderTree();

    /**
     * 检查屏幕id是否唯一
     * @param uiId
     * @return
     */
    boolean checkUiId(String uiId);

    /**
     * 检查菜单名称是否唯一
     * @param name
     * @return
     */
    boolean checkAuthName(String name);

    /**
     * 添加菜单
     * @param dto
     * @return
     */
    BaseResult addAuthObj(AuthObjSaveDto dto);

    /**
     * 根据id查询详情
     * @param id
     * @param fnshDttm
     * @return
     */
    AuthObjBasiM getObjById(String id, Date fnshDttm);

    /**
     * 修改菜单
     * @param dto
     * @return
     */
    BaseResult editAuthObj(AuthObjSaveDto dto);

    /**
     * 删除菜单
     * @param id
     * @return
     */
    boolean delAuthObj(String id);

    /**
     * 查询角色的菜单树
     * @param roleId
     * @return
     */
    List<AuthTreeDto> getAuthObjTreeByRoleId(String roleId);

    /**
     * 添加角色和菜单关系
     * @param roleId
     * @param newAuthIds
     * @return
     */
    BaseResult addRoleAuthRelation(String roleId, List<String> newAuthIds);

    List<AuthTreeDto> getAuthObjTreeByRoleIds(List<String> roleIdList);
}
