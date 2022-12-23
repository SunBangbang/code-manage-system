package com.codemanage.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.codemanage.common.constants.Constants;
import com.codemanage.common.util.UserUtils;
import com.codemanage.system.entity.AuthObjBasiM;
import com.codemanage.system.entity.UserRoleR;
import com.codemanage.system.mapper.UserRoleRMapper;
import com.codemanage.system.service.IUserRoleRService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * CVSO_用户角色_关系表 服务实现类
 * </p>
 *
 * @author hyh
 * @since 2022-07-18
 */
@Service
public class UserRoleRServiceImpl extends ServiceImpl<UserRoleRMapper, UserRoleR> implements IUserRoleRService {

    /**
     * 查询角色关联的菜单
     * @param roleId
     * @return
     */
    @Override
    public List<UserRoleR> getByRoleId(String roleId) {
        QueryWrapper<UserRoleR> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserRoleR::getCvUserId, roleId)
                .eq(UserRoleR::getCvUserDstsCd, Constants.UserDstsCd.ROLE)
                .eq(UserRoleR::getRecFnshDttm, Constants.FNSHDTTM);
        return this.list(queryWrapper);
    }


    /**
     * 删除角色和菜单关联关系
     * @param roleId
     * @param delAuthIds
     * @param oldFnshDttm
     * @param newBgnDttm
     * @return
     */
    @Override
    public boolean delRoleAuth(String roleId, List<String> delAuthIds, Date oldFnshDttm, Date newBgnDttm) {
        // 1. 先更新记录
        UpdateWrapper<UserRoleR> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().set(UserRoleR::getRecFnshDttm, oldFnshDttm)
                .set(UserRoleR::getFnlYn, Constants.FnlYn.NO);
        updateWrapper.lambda().eq(UserRoleR::getCvUserId, roleId)
                .eq(UserRoleR::getRecFnshDttm, Constants.FNSHDTTM)
                .eq(UserRoleR::getCvUserDstsCd, Constants.UserDstsCd.ROLE)
                .in(UserRoleR::getCvAuthObjId, delAuthIds);
        boolean flag = this.update(updateWrapper);
        if (flag) {
            // 2.插入数据记录最终删除者信息
            QueryWrapper<UserRoleR> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(UserRoleR::getCvUserId, roleId)
                    .eq(UserRoleR::getCvUserDstsCd, Constants.UserDstsCd.ROLE)
                    .eq(UserRoleR::getRecFnshDttm, oldFnshDttm)
                    .in(UserRoleR::getCvAuthObjId, delAuthIds);
            List<UserRoleR> list = this.list(queryWrapper);
            for (UserRoleR roleR : list) {
                roleR.setRecFnshDttm(newBgnDttm);
                roleR.setRecBgnDttm(newBgnDttm);
                roleR.setFnlYn(Constants.FnlYn.YES);
                roleR.setCretCvUserId(UserUtils.getLoginUser().getUserId());
            }
            return this.saveBatch(list);
        }
        return false;
    }

    /**
     * 添加角色和菜单关联关系
     * @param roleId
     * @param addAuthList
     * @param newBgnDttm
     * @return
     */
    @Override
    public boolean addRoleAuth(String roleId, List<AuthObjBasiM> addAuthList, Date newBgnDttm) {
        List<UserRoleR> addList = new ArrayList<>();
        for (AuthObjBasiM authObjBasiM : addAuthList) {
            UserRoleR userRoleR = new UserRoleR();
            userRoleR.setCvUserId(roleId);
            userRoleR.setCvAuthObjId(authObjBasiM.getCvAuthObjId());
            userRoleR.setRecFnshDttm(Constants.FNSHDTTM);
            userRoleR.setRecBgnDttm(newBgnDttm);
            userRoleR.setCvUserDstsCd(Constants.UserDstsCd.ROLE);
            userRoleR.setCvAuthObjDstsCd(authObjBasiM.getCvAuthObjDstsCd());
            userRoleR.setCretCvUserId(UserUtils.getLoginUser().getUserId());
            addList.add(userRoleR);
        }
        return this.saveBatch(addList);
    }

    @Override
    public List<String> getAuthIdByRoleIds(List<String> roleIdList) {
        QueryWrapper<UserRoleR> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(UserRoleR::getCvUserId, roleIdList)
                .eq(UserRoleR::getCvUserDstsCd, Constants.UserDstsCd.ROLE)
                .eq(UserRoleR::getRecFnshDttm, Constants.FNSHDTTM);
        List<UserRoleR> list = this.list(queryWrapper);
        Set<String> setList = new HashSet<>();
        for (UserRoleR userRoleR : list) {
            setList.add(userRoleR.getCvAuthObjId());
        }
        List<String> idList = new ArrayList<>(setList);
        return idList;
    }
}
