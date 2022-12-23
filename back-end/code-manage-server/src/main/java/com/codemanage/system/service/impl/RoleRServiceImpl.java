package com.codemanage.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.codemanage.common.constants.Constants;
import com.codemanage.common.util.DateUtils;
import com.codemanage.common.util.UserUtils;
import com.codemanage.system.entity.RoleR;
import com.codemanage.system.entity.UserBasiM;
import com.codemanage.system.mapper.RoleRMapper;
import com.codemanage.system.service.IRoleRService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * CVSO_角色_关系表 服务实现类
 * </p>
 *
 * @author hyh
 * @since 2022-07-09
 */
@Service
public class RoleRServiceImpl extends ServiceImpl<RoleRMapper, RoleR> implements IRoleRService {

    @Autowired
    private RoleRMapper roleRMapper;
    /**
     * 更新关系状态为不可以
     * @param hlvCvUserId
     * @param cvUserId
     * @param oldFnshDttm
     * @return
     */
    @Override
    public boolean updateFnlYn(String hlvCvUserId, String cvUserId, Date oldFnshDttm) {
        UpdateWrapper<RoleR> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().set(RoleR::getRecFnshDttm, oldFnshDttm)
                .set(RoleR::getFnlYn, Constants.FnlYn.NO);
        updateWrapper.lambda().eq(RoleR::getHlvCvUserId, hlvCvUserId)
                .eq(RoleR::getLlvCvUserId, cvUserId)
                .eq(RoleR::getRecFnshDttm, Constants.FNSHDTTM);
        return this.update(updateWrapper);
    }

    /**
     * 批量更新关系为不可用
     * @param oldFnshDttm
     * @param idList
     * @return
     */
    @Override
    public boolean updateBatchFnlYn(Date oldFnshDttm, List<String> idList) {
        if (CollectionUtils.isNotEmpty(idList)) {
            List<String> userDstsCdList = new ArrayList<>();
            userDstsCdList.add(Constants.UserDstsCd.USER);
            userDstsCdList.add(Constants.UserDstsCd.DEPT);
            UpdateWrapper<RoleR> updateWrapper = new UpdateWrapper<>();
            updateWrapper.lambda().set(RoleR::getRecFnshDttm, oldFnshDttm)
                    .set(RoleR::getFnlYn, Constants.FnlYn.NO);
            updateWrapper.lambda().eq(RoleR::getRecFnshDttm, Constants.FNSHDTTM)
                    .and(wrapper -> wrapper.in(RoleR::getLlvCvUserId, idList).
                            or().in(RoleR::getHlvCvUserId, idList))
                    .and(wrapper -> wrapper.in(RoleR::getLlvCvUserDstsCd, userDstsCdList)
                            .or().in(RoleR::getHlvCvUserDstsCd, userDstsCdList));
            return this.update(updateWrapper);
        }
        return false;
    }

    /**
     * 插入数据记录最终删除者信息
     * @param oldFnshDttm
     * @param newBgnDttm
     * @param idList
     * @return
     */
    @Override
    public boolean delDept(Date oldFnshDttm, Date newBgnDttm, List<String> idList) {
        List<String> userDstsCdList = new ArrayList<>();
        userDstsCdList.add(Constants.UserDstsCd.USER);
        userDstsCdList.add(Constants.UserDstsCd.DEPT);
        UpdateWrapper<RoleR> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().set(RoleR::getRecFnshDttm, oldFnshDttm)
                .set(RoleR::getFnlYn, Constants.FnlYn.NO);
        updateWrapper.lambda().eq(RoleR::getRecFnshDttm, Constants.FNSHDTTM)
                .and(wrapper -> wrapper.in(RoleR::getLlvCvUserId, idList).
                        or().in(RoleR::getHlvCvUserId, idList))
                .and(wrapper -> wrapper.in(RoleR::getLlvCvUserDstsCd, userDstsCdList)
                        .or().in(RoleR::getHlvCvUserDstsCd, userDstsCdList));
        boolean flag =  this.update(updateWrapper);
        if (flag) {
            QueryWrapper<RoleR> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(RoleR::getRecFnshDttm, oldFnshDttm)
                    .and(wrapper -> wrapper.in(RoleR::getLlvCvUserId, idList).
                            or().in(RoleR::getHlvCvUserId, idList))
                    .and(wrapper -> wrapper.in(RoleR::getLlvCvUserDstsCd, userDstsCdList)
                            .or().in(RoleR::getHlvCvUserDstsCd, userDstsCdList));
            List<RoleR> list = this.list(queryWrapper);
            for (RoleR roleR : list) {
                if (Constants.UserDstsCd.USER.equals(roleR.getLlvCvUserDstsCd())){
                    roleR.setHlvCvUserId("0");
                    roleR.setRecFnshDttm(Constants.FNSHDTTM);
                    roleR.setFnlYn(Constants.FnlYn.YES);
                }
                if (Constants.UserDstsCd.DEPT.equals(roleR.getLlvCvUserDstsCd())){
                    roleR.setRecFnshDttm(newBgnDttm);
                    roleR.setFnlYn(Constants.FnlYn.YES);
                }
                roleR.setRecBgnDttm(newBgnDttm);
                roleR.setCretCvUserId(UserUtils.getLoginUser().getUserId());
            }
            return this.saveBatch(list);
        }
       return false;
//        return roleRMapper.insertByDeptDelete(DateUtils.defaultFormatDate(oldFnshDttm), DateUtils.defaultFormatDate(newBgnDttm),
//                idList, UserUtils.getLoginUser().getUserId());

    }

    /**
     * 保存关联关系
     * @param hlvObj
     * @param obj
     * @param bgnDttm
     * @return
     */
    @Override
    public boolean saveRoleR(UserBasiM hlvObj, UserBasiM obj, Date bgnDttm) {
        if (hlvObj != null && obj != null) {
            RoleR roleR = new RoleR();
            roleR.setHlvCvUserId(hlvObj.getCvUserId());
            roleR.setLlvCvUserId(obj.getCvUserId());
            roleR.setRecFnshDttm(Constants.FNSHDTTM);
            roleR.setRecBgnDttm(bgnDttm);
            roleR.setHlvCvUserDstsCd(hlvObj.getCvUserDstsCd());
            roleR.setLlvCvUserDstsCd(obj.getCvUserDstsCd());
            roleR.setCretCvUserId(UserUtils.getLoginUser().getUserId());
            return this.save(roleR);
        }
        return false;
    }

    @Override
    public List<RoleR> getUserRoleByRoleId(String id) {
        QueryWrapper<RoleR> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(RoleR::getHlvCvUserDstsCd, Constants.UserDstsCd.ROLE)
                .eq(RoleR::getLlvCvUserDstsCd, Constants.UserDstsCd.USER)
                .eq(RoleR::getHlvCvUserId, id);
        return this.list(queryWrapper);
    }

//    @Override
//    public boolean updateRoleRelation(Date oldFnshDttm, String id) {
//        UpdateWrapper<RoleR> updateWrapper = new UpdateWrapper<>();
//        updateWrapper.lambda().set(RoleR::getRecFnshDttm, oldFnshDttm)
//                .set(RoleR::getFnlYn, Constants.FnlYn.NO);
//        updateWrapper.lambda().eq(RoleR::getHlvCvUserId, id)
//                .eq(RoleR::getRecFnshDttm, Constants.FNSHDTTM);
//        return this.update(updateWrapper);
//    }

//    @Override
//    public boolean insertByRoleDelete(Date oldFnshDttm, Date newBgnDttm, String id) {
//        return roleRMapper.insertByRoleDelete(DateUtils.defaultFormatDate(oldFnshDttm), DateUtils.defaultFormatDate(newBgnDttm),
//                id, UserUtils.getLoginUser().getUserId());
//    }

    @Override
    public boolean delRole(Date oldFnshDttm, Date newBgnDttm, String roleId) {
        UpdateWrapper<RoleR> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().set(RoleR::getRecFnshDttm, oldFnshDttm)
                .set(RoleR::getFnlYn, Constants.FnlYn.NO);
        updateWrapper.lambda().eq(RoleR::getHlvCvUserId, roleId)
                .eq(RoleR::getRecFnshDttm, Constants.FNSHDTTM);
        boolean flag = this.update(updateWrapper);
        if (flag) {
            QueryWrapper<RoleR> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(RoleR::getHlvCvUserId, roleId)
                    .eq(RoleR::getRecFnshDttm, oldFnshDttm);
            List<RoleR> list = this.list(queryWrapper);
            for (RoleR roleR : list) {
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
     * 查询用户绑定的角色id
     * @param userId
     * @return
     */
    @Override
    public List<String> getRoleIdsByUserId(String userId) {
        return roleRMapper.getRoleIdsByUserId(userId);
    }

    /**
     * 查询角色绑定的用户id
     * @param roleId
     * @return
     */
    @Override
    public List<String> getUserIdsByRoleId(String roleId) {
        return roleRMapper.getUserIdsByRoleId(roleId);
    }

    /**
     * 删除用户和角色关联关系
     * @param userId
     * @param delRoleIds
     * @param oldFnshDttm
     * @param newBgnDttm
     * @return
     */
    @Override
    public boolean delUserRole(String userId, List<String> delRoleIds, Date oldFnshDttm, Date newBgnDttm) {
        // 1. 先更新记录
        UpdateWrapper<RoleR> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().set(RoleR::getRecFnshDttm, oldFnshDttm)
                .set(RoleR::getFnlYn, Constants.FnlYn.NO);
        updateWrapper.lambda().eq(RoleR::getLlvCvUserId, userId)
                .eq(RoleR::getRecFnshDttm, Constants.FNSHDTTM)
                .eq(RoleR::getHlvCvUserDstsCd, Constants.UserDstsCd.ROLE)
                .in(RoleR::getHlvCvUserId, delRoleIds);
        boolean flag = this.update(updateWrapper);
        if (flag) {
            // 2.插入数据记录最终删除者信息
            QueryWrapper<RoleR> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(RoleR::getLlvCvUserId, userId)
                    .in(RoleR::getHlvCvUserId, delRoleIds)
                    .eq(RoleR::getRecFnshDttm, oldFnshDttm);
            List<RoleR> list = this.list(queryWrapper);
            for (RoleR roleR : list) {
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
     * 添加关联关系
     * @param userId
     * @param addRoleIds
     * @param newBgnDttm
     * @return
     */
    @Override
    public boolean addUserRole(String userId, List<String> addRoleIds, Date newBgnDttm) {
        List<RoleR> addList = new ArrayList<>();
        for (String roleId : addRoleIds) {
            RoleR roleR = new RoleR();
            roleR.setHlvCvUserId(roleId);
            roleR.setLlvCvUserId(userId);
            roleR.setRecFnshDttm(Constants.FNSHDTTM);
            roleR.setRecBgnDttm(newBgnDttm);
            roleR.setHlvCvUserDstsCd(Constants.UserDstsCd.ROLE);
            roleR.setLlvCvUserDstsCd(Constants.UserDstsCd.USER);
            roleR.setCretCvUserId(UserUtils.getLoginUser().getUserId());
            addList.add(roleR);
        }
        return this.saveBatch(addList);
    }

    /**
     * 添加用户和部门关联关系
     * @param userId
     * @param deptId
     * @param newBgnDttm
     * @return
     */
    @Override
    public boolean addUserDept(String userId, String deptId, Date newBgnDttm) {
        RoleR roleR = new RoleR();
        roleR.setHlvCvUserId(deptId);
        roleR.setLlvCvUserId(userId);
        roleR.setRecFnshDttm(Constants.FNSHDTTM);
        roleR.setRecBgnDttm(newBgnDttm);
        roleR.setHlvCvUserDstsCd(Constants.UserDstsCd.DEPT);
        roleR.setLlvCvUserDstsCd(Constants.UserDstsCd.USER);
        roleR.setCretCvUserId(UserUtils.getLoginUser().getUserId());
        return this.save(roleR);
    }


    @Override
    public boolean delUser(String id, Date oldFnshDttm, Date newBgnDttm) {
        // 1. 更新用户关联信息为不可用
        UpdateWrapper<RoleR> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().set(RoleR::getRecFnshDttm, oldFnshDttm)
                .set(RoleR::getFnlYn, Constants.FnlYn.NO);
        updateWrapper.lambda().eq(RoleR::getLlvCvUserId, id)
                .eq(RoleR::getRecFnshDttm, Constants.FNSHDTTM);
        boolean flag = this.update(updateWrapper);
        if (flag) {
            // 2.插入数据记录最终删除者信息
            QueryWrapper<RoleR> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(RoleR::getLlvCvUserId, id)
                    .eq(RoleR::getRecFnshDttm, oldFnshDttm);
            List<RoleR> list = this.list(queryWrapper);
            for (RoleR roleR : list) {
                roleR.setRecFnshDttm(newBgnDttm);
                roleR.setRecBgnDttm(newBgnDttm);
                roleR.setFnlYn(Constants.FnlYn.YES);
                roleR.setCretCvUserId(UserUtils.getLoginUser().getUserId());
            }
            return this.saveBatch(list);
           /* return roleRMapper.insertByUserDelete(id, DateUtils.defaultFormatDate(oldFnshDttm), DateUtils.defaultFormatDate(newBgnDttm),
                    UserUtils.getLoginUser().getUserId());*/
        }
        return false;
    }

    /**
     * 添加角色和用户关联关系
     * @param roleId
     * @param addUserIds
     * @param newBgnDttm
     * @return
     */
    @Override
    public boolean addRoleUserRelation(String roleId, List<String> addUserIds, Date newBgnDttm) {
        List<RoleR> addList = new ArrayList<>();
        for (String userId : addUserIds) {
            RoleR roleR = new RoleR();
            roleR.setHlvCvUserId(roleId);
            roleR.setLlvCvUserId(userId);
            roleR.setRecFnshDttm(Constants.FNSHDTTM);
            roleR.setRecBgnDttm(newBgnDttm);
            roleR.setHlvCvUserDstsCd(Constants.UserDstsCd.ROLE);
            roleR.setLlvCvUserDstsCd(Constants.UserDstsCd.USER);
            roleR.setCretCvUserId(UserUtils.getLoginUser().getUserId());
            addList.add(roleR);
        }
        return this.saveBatch(addList);
    }

    /**
     * 删除角色和用户关联关系
     * @param roleId
     * @param userIdList
     * @param oldFnshDttm
     * @param newBgnDttm
     * @return
     */
    @Override
    public boolean delRoleUserRelation(String roleId, List<String> userIdList, Date oldFnshDttm, Date newBgnDttm) {
        // 1. 先更新记录
        UpdateWrapper<RoleR> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().set(RoleR::getRecFnshDttm, oldFnshDttm)
                .set(RoleR::getFnlYn, Constants.FnlYn.NO);
        updateWrapper.lambda().eq(RoleR::getHlvCvUserId, roleId)
                .eq(RoleR::getRecFnshDttm, Constants.FNSHDTTM)
                .eq(RoleR::getLlvCvUserDstsCd, Constants.UserDstsCd.USER)
                .in(RoleR::getLlvCvUserId, userIdList);
        boolean flag = this.update(updateWrapper);
        if (flag) {
            // 2.插入数据记录最终删除者信息
            QueryWrapper<RoleR> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(RoleR::getHlvCvUserId, roleId)
                    .eq(RoleR::getLlvCvUserDstsCd, Constants.UserDstsCd.USER)
                    .eq(RoleR::getRecFnshDttm, oldFnshDttm)
                    .in(RoleR::getLlvCvUserId, userIdList);
            List<RoleR> list = this.list(queryWrapper);
            for (RoleR roleR : list) {
                roleR.setRecFnshDttm(newBgnDttm);
                roleR.setRecBgnDttm(newBgnDttm);
                roleR.setFnlYn(Constants.FnlYn.YES);
                roleR.setCretCvUserId(UserUtils.getLoginUser().getUserId());
            }
            return this.saveBatch(list);
        }
        return false;
    }
}
