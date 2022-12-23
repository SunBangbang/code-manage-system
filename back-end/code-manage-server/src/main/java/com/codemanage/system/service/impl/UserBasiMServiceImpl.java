package com.codemanage.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codemanage.common.constants.Constants;
import com.codemanage.common.entity.BaseResult;
import com.codemanage.common.entity.TreeDto;
import com.codemanage.common.util.DateUtils;
import com.codemanage.common.util.PasswordUtils;
import com.codemanage.common.util.TreeUtils;
import com.codemanage.common.util.UserUtils;
import com.codemanage.system.dto.request.DeptSaveDto;
import com.codemanage.system.dto.request.RoleQueryDto;
import com.codemanage.system.dto.request.RoleSaveDto;
import com.codemanage.system.dto.request.UserBasiMDto;
import com.codemanage.system.dto.response.AuthTreeDto;
import com.codemanage.system.dto.response.DeptTreeDto;
import com.codemanage.system.dto.response.RoleVo;
import com.codemanage.system.dto.response.UserBasiMVo;
import com.codemanage.system.entity.RoleR;
import com.codemanage.system.entity.UserBasiM;
import com.codemanage.system.mapper.UserBasiMMapper;
import com.codemanage.system.service.IAuthObjBasiMService;
import com.codemanage.system.service.IUserBasiMService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.catalina.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * CVSO_用户基础_主表 服务实现类
 * </p>
 *
 * @author hyh
 * @since 2022-06-02
 */
@Service
public class UserBasiMServiceImpl extends ServiceImpl<UserBasiMMapper, UserBasiM> implements IUserBasiMService {

    @Autowired
    private UserBasiMMapper userBasiMMapper;

    @Autowired
    private RoleRServiceImpl roleRService;

    @Autowired
    @Lazy
    private IAuthObjBasiMService authObjBasiMService;

    /**
     * 查询部门和用户树
     * @return
     */
    @Override
    public List<DeptTreeDto> getDeptAndUserTree() {
        return userBasiMMapper.getDeptAndUserTree();
    }

    /**
     * 查询部门树
     * @return
     */
    @Override
    public List<DeptTreeDto> getDeptTree() {
        return userBasiMMapper.getDeptTree();
    }

    /**
     * 根据部门id查询部门下的用户
     * @param map
     * @return
     */
    @Override
    public List<UserBasiMVo> getUserListByDept(Map<String, String> map) {
        return userBasiMMapper.getUserListByDept(map);
    }

    /**
     * 添加部门
     * @param dto
     * @return
     */
    @Override
    public BaseResult addDept(DeptSaveDto dto) {
        Date bgnDttm = new Date();
        UserBasiM obj = new UserBasiM();
        BeanUtils.copyProperties(dto, obj);
        obj.setCvUserId(UUID.randomUUID().toString());
        obj.setRecFnshDttm(Constants.FNSHDTTM);
        obj.setRecBgnDttm(bgnDttm);
        obj.setCretCvUserId(UserUtils.getLoginUser().getUserId());
        obj.setCvLangCd(Constants.LangCd.SIMPLIFIED_CHINESE);
        obj.setFnlYn(Constants.FnlYn.YES);
        if (StringUtils.isEmpty(dto.getHlvCvUserId())) {
            dto.setHlvCvUserId("0");
        }
        // TODO 公司id 公司总经理名称 待确定
        obj.setCvOwnCmpyId("1");
        obj.setCmpyGmNm("11");
        boolean flag = this.save(obj);
        if (flag) {
            UserBasiM hlvObj = this.getObjById(obj.getHlvCvUserId(), Constants.FNSHDTTM);
            if ("0".equals(obj.getHlvCvUserId())){
                hlvObj = new UserBasiM();
                hlvObj.setCvUserId("0");
                hlvObj.setCvUserDstsCd(Constants.UserDstsCd.DEPT);
            }
            roleRService.saveRoleR(hlvObj, obj, bgnDttm);
            return BaseResult.successMsg("添加部门成功！");
        }
        return BaseResult.failedMsg("添加部门失败！");
    }

    /**
     * 编辑部门
     * @param dto
     * @return
     */
    @Override
    public BaseResult editDept(DeptSaveDto dto) {
        Date oldFnshDttm = new Date();
        Date bgnDttm = new Date(oldFnshDttm.getTime() + 1);
        UserBasiM obj = this.getObjById(dto.getCvUserId(),Constants.FNSHDTTM);
        String oldDeptId = obj.getHlvCvUserId();
        String newDeptId = dto.getHlvCvUserId();
        // 1. 更新原来用户状态
        this.updateFnlYn(dto.getCvUserId(), oldFnshDttm);

        // 2. 新插入一条记录（id一样）
//        UserBasiM obj = new UserBasiM();
//        BeanUtils.copyProperties(dto, obj);
        obj.setRecFnshDttm(Constants.FNSHDTTM);
        obj.setRecBgnDttm(bgnDttm);
        obj.setUserNm(dto.getUserNm());
        obj.setHlvCvUserId(dto.getHlvCvUserId());
        obj.setUserExplTxt(dto.getUserExplTxt());
        obj.setCretCvUserId(UserUtils.getLoginUser().getUserId());
        boolean flag = this.save(obj);
        if (flag && !oldDeptId.equals(newDeptId)) {
            // 3. 更新关系数据
            roleRService.updateFnlYn(obj.getHlvCvUserId(), obj.getCvUserId(), oldFnshDttm);
            // 4. 插入关系数据
            UserBasiM hlvObj = this.getObjById(obj.getHlvCvUserId(), Constants.FNSHDTTM);
            if ("0".equals(obj.getHlvCvUserId())){
                hlvObj = new UserBasiM();
                hlvObj.setCvUserId("0");
                hlvObj.setCvUserDstsCd(Constants.UserDstsCd.DEPT);
            }
            roleRService.saveRoleR(hlvObj, obj, bgnDttm);
        }
        if (flag) {
            return BaseResult.successMsg("编辑部门成功！");
        }
        return BaseResult.failedMsg("编辑部门失败！");
    }

    /**
     * 删除部门
     * @param id
     * @return
     */
    @Override
    public boolean delDept(String id) {
        Date oldFnshDttm = new Date();
        Date newBgnDttm = new Date(oldFnshDttm.getTime() + 1);
        // 1. 查询选中节点及子节点数据（部门和用户）
        List<DeptTreeDto> deptList = userBasiMMapper.getDeptTreeListById(id);
        // 2. 查询到的数据更新为不可用状态
        List<String> idList = new ArrayList<>();
        for (DeptTreeDto deptTreeDto : deptList) {
            idList.add(deptTreeDto.getId());
        }
//        idList.add(id);
        this.updateBatchFnlYn(oldFnshDttm, idList);
        // 3. 插入数据记录最终删除者信息
        boolean flag = this.insertByDeptDelete(oldFnshDttm, newBgnDttm, idList);
        // 4. 更新部门和部门之间的关系数据
//        roleRService.updateBatchFnlYn(oldFnshDttm, idList);
        // 5. 插入部门关系数据
//        roleRService.insertByDeptDelete(oldFnshDttm, newBgnDttm, idList);
        roleRService.delDept(oldFnshDttm, newBgnDttm, idList);
        return flag;
    }

    private  boolean insertByDeptDelete(Date oldFnshDttm,Date newBgnDttm, List<String> idList) {
        List<String> userDstsCdList = new ArrayList<>();
        userDstsCdList.add(Constants.UserDstsCd.USER);
        userDstsCdList.add(Constants.UserDstsCd.DEPT);
        QueryWrapper<UserBasiM> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(UserBasiM::getCvUserId, idList)
                .eq(UserBasiM::getRecFnshDttm, oldFnshDttm)
                .in(UserBasiM::getCvUserDstsCd, userDstsCdList);

        List<UserBasiM> list = this.list(queryWrapper);
        for (UserBasiM userBasiM : list) {
            if (Constants.UserDstsCd.USER.equals(userBasiM.getCvUserDstsCd())) {
                userBasiM.setRecFnshDttm(Constants.FNSHDTTM);
                userBasiM.setHlvCvUserId("0");
                userBasiM.setFnlYn(Constants.FnlYn.YES);
            }
            if (Constants.UserDstsCd.DEPT.equals(userBasiM.getCvUserDstsCd())) {
                userBasiM.setRecFnshDttm(newBgnDttm);
                userBasiM.setFnlYn(Constants.FnlYn.YES);
            }
            userBasiM.setRecBgnDttm(newBgnDttm);
            userBasiM.setCretCvUserId(UserUtils.getLoginUser().getUserId());
        }
        return this.saveBatch(list);
    }
    /**
     * 查询用户列表
     * @param dto
     * @return
     */
    @Override
    public List<UserBasiMVo> getUserBasiMList(UserBasiMDto dto) {
        return userBasiMMapper.getUserBasiMList(dto);
    }

    /**
     * 查询用户Vo
     * @param id
     * @return
     */
    @Override
    public UserBasiMVo getVoById(String id) {
        return userBasiMMapper.getVoById(id);
    }

    /**
     * 检查登录id是否唯一
     * @param lgnId
     * @return
     */
    @Override
    public boolean checkLgnId(String lgnId) {
        int count = userBasiMMapper.checkLgnId(lgnId);
        if (count < 1) {
            return true;
        }
        return false;
    }

    /**
     * 添加用户
     * @param dto
     * @return
     */
    @Override
    public BaseResult addUserBasiM(UserBasiMDto dto) {
        Date bgnDttm = new Date();
        UserBasiM obj = dto.convertToUserBasiM();
        obj.setCvUserId(UUID.randomUUID().toString());
        obj.setRecFnshDttm(Constants.FNSHDTTM);
        obj.setRecBgnDttm(bgnDttm);
        obj.setCretCvUserId(UserUtils.getLoginUser().getUserId());
        obj.setCvBnacLckStCd(Constants.UserBnacLckStCd.UNLOCK);
        obj.setFnlYn(Constants.FnlYn.YES);
        obj.setLgnPwdEncd(PasswordUtils.getSHA256Str(dto.getLgnPwdEncd()));
        obj.setCvLangCd(Constants.LangCd.SIMPLIFIED_CHINESE);
        // TODO 公司id 公司总经理名称 待确定
        obj.setCvOwnCmpyId("1");
        obj.setCmpyGmNm("11");
        boolean flag = this.save(obj);
        if (flag) {
            // 插入部门和用户关联关系
            UserBasiM hlvObj = this.getObjById(obj.getHlvCvUserId(), Constants.FNSHDTTM);
            if ("0".equals(obj.getHlvCvUserId())){
                hlvObj = new UserBasiM();
                hlvObj.setCvUserId("0");
                hlvObj.setCvUserDstsCd(Constants.UserDstsCd.DEPT);
            }
            roleRService.saveRoleR(hlvObj, obj, bgnDttm);
            return BaseResult.successMsg("保存用户成功!");
        }
        return BaseResult.failedMsg("保存用户失败!");
    }

    /**
     * 编辑用户
     * @param dto
     * @return
     */
    @Override
    public BaseResult editUserBasiM(UserBasiMDto dto) {
        Date oldFnshDttm = new Date();
        Date newBgnDttm = new Date(oldFnshDttm.getTime() + 1);
        UserBasiM obj = this.getObjById(dto.getCvUserId(), Constants.FNSHDTTM);
        // 1. 将用户更新为不可用
        this.updateFnlYn(dto.getCvUserId(), oldFnshDttm);
        // 2. 插入用户信息
        String oldDeptId = obj.getHlvCvUserId();
        String newDeptId = dto.getHlvCvUserId();

        obj.setRecFnshDttm(Constants.FNSHDTTM);
        obj.setRecBgnDttm(newBgnDttm);
        obj.setCvLgnId(dto.getCvLgnId());
        obj.setUserNm(dto.getUserNm());
        // 密码修改过
        if (!dto.getLgnPwdEncd().equals(obj.getLgnPwdEncd())) {
            obj.setLgnPwdEncd(PasswordUtils.getSHA256Str(dto.getLgnPwdEncd()));
        }
        obj.setMlbxUrl(dto.getMlbxUrl());
        obj.setHlvCvUserId(dto.getHlvCvUserId());
        obj.setBnacExprDt(dto.getBnacExprDt());
        obj.setUserExplTxt(dto.getUserExplTxt());
        boolean flag = this.save(obj);
        // 3.更新用户和部门关系
        if (flag && !oldDeptId.equals(newDeptId)) {
            roleRService.updateFnlYn(oldDeptId,obj.getCvUserId(), oldFnshDttm);
            roleRService.addUserDept(obj.getCvUserId(), newDeptId, newBgnDttm);
        }
        if (flag) {
            return BaseResult.successMsg("编辑成功!");
        }
        return BaseResult.failedMsg("编辑失败!");
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @Override
    public boolean delUser(String id) {
        Date oldFnshDttm = new Date();
        Date newBgnDttm = new Date(oldFnshDttm.getTime() + 1);
        // 1. 将用户更新为不可用
        this.updateFnlYn(id, oldFnshDttm);
        // 2. 插入删除数据
        UserBasiM oldObj = this.getObjById(id, oldFnshDttm);
        oldObj.setRecFnshDttm(newBgnDttm);
        oldObj.setRecBgnDttm(newBgnDttm);
        oldObj.setFnlYn(Constants.FnlYn.YES);
        oldObj.setCretCvUserId(UserUtils.getLoginUser().getUserId());
        boolean flag = this.save(oldObj);
        if (flag) {
            // 3. 更新用户关联关系
            return roleRService.delUser(id, oldFnshDttm, newBgnDttm);
        }
        return false;
    }


    /**
     *  用户绑定角色
     * @param dto
     * @return
     */
    @Override
    public BaseResult addUserRole(UserBasiMDto dto) {
        Date oldFnshDttm = new Date();
        Date newBgnDttm = new Date(oldFnshDttm.getTime() + 1);
        String userId = dto.getCvUserId();
        List<String> newRoleIds = dto.getRoleList();
        // 1. 查询用户之前绑定的所有角色id
        List<String> oldRoleIds = roleRService.getRoleIdsByUserId(userId);
        List<String> addRoleIds = new ArrayList<>();
        List<String> delRoleIds = new ArrayList<>();
        if (CollectionUtils.isEmpty(newRoleIds)) {
            delRoleIds = oldRoleIds;
        } else if (CollectionUtils.isEmpty(oldRoleIds)) {
            addRoleIds = newRoleIds;
        } else {
            addRoleIds = newRoleIds.stream().filter(item -> !oldRoleIds.contains(item)).collect(Collectors.toList());
            delRoleIds = oldRoleIds.stream().filter(item -> !newRoleIds.contains(item)).collect(Collectors.toList());
        }
        if (CollectionUtils.isNotEmpty(delRoleIds)) {
            // 2. 需要删除的关联关系
            roleRService.delUserRole(userId, delRoleIds, oldFnshDttm, newBgnDttm);
        }
        if (CollectionUtils.isNotEmpty(addRoleIds)) {
            // 3. 增加的关联关系
            roleRService.addUserRole(userId, addRoleIds, newBgnDttm);
        }
        return BaseResult.successMsg("授权完成!");
    }

    /**
     * 初始化密码
     * @param id
     * @return
     */
    @Override
    public boolean initPassword(String id) {
        Date oldFnshDttm = new Date();
        Date newBgnDttm = new Date(oldFnshDttm.getTime() + 1);
        // 1. 将用户更新为不可用
        this.updateFnlYn(id, oldFnshDttm);
        // 2. 插入数据
        UserBasiM oldObj = this.getObjById(id, oldFnshDttm);
        oldObj.setRecFnshDttm(Constants.FNSHDTTM);
        oldObj.setRecBgnDttm(newBgnDttm);
        oldObj.setFnlYn(Constants.FnlYn.YES);
        oldObj.setLgnPwdEncd(PasswordUtils.getSHA256Str(Constants.DEFAULT_PASSWORD_USER));
        oldObj.setCretCvUserId(UserUtils.getLoginUser().getUserId());
        return this.save(oldObj);
    }

    @Override
    public boolean unlockUser(String id) {
        Date oldFnshDttm = new Date();
        Date newBgnDttm = new Date(oldFnshDttm.getTime() + 1);
        // 1. 将用户更新为不可用
        this.updateFnlYn(id, oldFnshDttm);
        // 2. 插入数据
        UserBasiM oldObj = this.getObjById(id, oldFnshDttm);
        oldObj.setRecFnshDttm(Constants.FNSHDTTM);
        oldObj.setRecBgnDttm(newBgnDttm);
        oldObj.setCvBnacLckStCd(Constants.UserBnacLckStCd.UNLOCK);
        oldObj.setFnlYn(Constants.FnlYn.YES);
        oldObj.setCretCvUserId(UserUtils.getLoginUser().getUserId());
        return this.save(oldObj);
    }

    @Override
    public boolean checkUserId(String userId) {
        int count = userBasiMMapper.checkUserId(userId);
        if (count < 1) {
            return true;
        }
        return false;
    }

    /**
     * 根据登录id查询用户
     * @param lgnId
     * @return
     */
    @Override
    public UserBasiM getByLgnId(String lgnId) {
        QueryWrapper<UserBasiM> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserBasiM::getCvLgnId, lgnId)
            .eq(UserBasiM::getRecFnshDttm, Constants.FNSHDTTM);
        List<UserBasiM> list = this.list(queryWrapper);
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    @Override
    public UserBasiM getObjById(String id, Date fnshDttm) {
        QueryWrapper<UserBasiM> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserBasiM::getCvUserId, id)
                .eq(UserBasiM::getRecFnshDttm, fnshDttm);
        return this.getOne(queryWrapper);
    }

    /**
     * 更新锁定状态
     * @param lgnId
     * @param code
     * @return
     */
    @Override
    public boolean updateCvBnacLckStCd(String lgnId, String code) {
        UserBasiM obj = this.getByLgnId(lgnId);
        if (obj != null) {
            obj.setCvBnacLckStCd(code);
            return this.updateById(obj);
        }
        return false;
    }

    /**
     * 查询当前部门下级的所有用户信息
     * @param deptId
     * @return
     */
    @Override
    public List<UserBasiMVo> getSubordinateUserByDeptId(String deptId) {
        return userBasiMMapper.getSubordinateUserByDeptId(deptId);
    }

    /**
     * 查询所有角色信息
     * @return
     */
    @Override
    public List<RoleVo> getAllRole() {
        return userBasiMMapper.getAllRole();
    }

    /**
     *
     * @param roleName
     * @return
     */
    @Override
    public boolean checkRoleName(String roleName) {
        int count = userBasiMMapper.checkRoleName(roleName);
        if (count < 1) {
            return true;
        }
        return false;
    }

    /**
     * 添加角色
     * @param dto
     * @return
     */
    @Override
    public BaseResult addRole(RoleSaveDto dto) {
        Date bgnDttm = new Date();
        UserBasiM obj = new UserBasiM();
        obj.setCvUserId(UUID.randomUUID().toString());
        obj.setRecFnshDttm(Constants.FNSHDTTM);
        obj.setRecBgnDttm(bgnDttm);
        obj.setCretCvUserId(UserUtils.getLoginUser().getUserId());
        obj.setCvOwnCmpyId("1");
        obj.setUserNm(dto.getUserNm());
        obj.setUserExplTxt(dto.getUserExplTxt());
        obj.setCvUserDstsCd(dto.getCvUserDstsCd());
        boolean flag = this.save(obj);
        if (flag) {
            return BaseResult.successMsg("添加成功!");
        }
        return BaseResult.failedMsg("添加失败!");
    }

    /**
     * 编辑角色
     * @param dto
     * @return
     */
    @Override
    public BaseResult editRole(RoleSaveDto dto) {
        Date oldFnshDttm = new Date();
        Date bgnDttm = new Date(oldFnshDttm.getTime() + 1);
        UserBasiM obj = this.getObjById(dto.getCvUserId(), Constants.FNSHDTTM);
        // 1. 更新原来角色状态
        this.updateFnlYn(dto.getCvUserId(), oldFnshDttm);
        // 2. 新插入一条记录（id一样）
        obj.setRecFnshDttm(Constants.FNSHDTTM);
        obj.setRecBgnDttm(bgnDttm);
        obj.setCretCvUserId(UserUtils.getLoginUser().getUserId());
        obj.setUserNm(dto.getUserNm());
        obj.setUserExplTxt(dto.getUserExplTxt());
        boolean flag = this.save(obj);
        if (flag) {
            return BaseResult.successMsg("编辑成功!");
        }
        return BaseResult.failedMsg("编辑失败!");
    }

    /**
     * 删除角色
     * @param id
     * @return
     */
    @Override
    public boolean delRole(String id) {
        Date oldFnshDttm = new Date();
        Date newBgnDttm = new Date(oldFnshDttm.getTime() + 1);
        // 1. 角色更新为不可用
        boolean flag = this.updateFnlYn(id, oldFnshDttm);
        // 2. 插入记录创建者数据
        this.insertByRoleDelete(oldFnshDttm, newBgnDttm, id);
        // 3. 获取角色关联用户
       /* List<RoleR> roleRList = roleRService.getUserRoleByRoleId(id);
        List<String> idList = new ArrayList<>();
        for (RoleR roleR : roleRList) {
            idList.add(roleR.getLlvCvUserId());
        }*/
        // 4. 删除角色关联所有用户关系
//        roleRService.updateRoleRelation(oldFnshDttm,id);
//        roleRService.insertByRoleDelete(oldFnshDttm, newBgnDttm, id);
        roleRService.delRole(oldFnshDttm, newBgnDttm, id);
        return flag;
    }

    private boolean insertByRoleDelete(Date oldFnshDttm, Date newBgnDttm, String id) {
        UserBasiM oldObj = this.getObjById(id, oldFnshDttm);
        oldObj.setRecFnshDttm(newBgnDttm);
        oldObj.setRecBgnDttm(newBgnDttm);
        oldObj.setFnlYn(Constants.FnlYn.NO);
        oldObj.setCretCvUserId(UserUtils.getLoginUser().getUserId());
        return this.save(oldObj);
    }

    /**
     * 查询角色下的用户
     * @param dto
     * @return
     */
    @Override
    public Page<UserBasiMVo> getUserPageByRole(RoleQueryDto dto) {
        Page<UserBasiMVo> page = new Page<>(dto.getCurrent(), dto.getSize());
        page = userBasiMMapper.getUserPageByRole(page, dto);
        return page;
    }

    /**
     * 新增角色和用户关系
     * @param roleId
     * @param userId
     * @return
     */
    @Override
    public boolean addRoleUserRelation(String roleId, String userId) {
        UserBasiM hlvObj = this.getObjById(roleId, Constants.FNSHDTTM);
        UserBasiM obj = this.getObjById(userId, Constants.FNSHDTTM);
        return roleRService.saveRoleR(hlvObj, obj, new Date());
    }

    @Override
    public List<RoleVo> getRoleByUserId(String userId) {
        return userBasiMMapper.getRoleByUserId(userId);
    }

    @Override
    public RoleVo getRoleById(String id) {
        return userBasiMMapper.getRoleById(id);
    }

    @Override
    public boolean addBatchRoleUserRelation(RoleSaveDto dto) {
        Date newBgnDttm = new Date();
        String roleId = dto.getCvUserId();
        List<String> userIdList = dto.getUserIdList();
        if (StringUtils.isNotEmpty(roleId) && CollectionUtils.isNotEmpty(userIdList)) {
            List<String> oldUserIdList = roleRService.getUserIdsByRoleId(roleId);
            List<String> addUserIds = new ArrayList<>();
            if (CollectionUtils.isEmpty(oldUserIdList)) {
                addUserIds = userIdList;
            } else {
                addUserIds = userIdList.stream().filter(item -> !oldUserIdList.contains(item)).collect(Collectors.toList());
            }
            if (CollectionUtils.isNotEmpty(addUserIds)) {
                // 3. 增加的关联关系
              return roleRService.addRoleUserRelation(roleId, addUserIds, newBgnDttm);
            }
        }
        return false;
    }

    @Override
    public boolean delBatchRoleUserRelation(RoleSaveDto dto) {
        Date oldFnshDttm = new Date();
        Date newBgnDttm = new Date(oldFnshDttm.getTime() + 1);
        String roleId = dto.getCvUserId();
        List<String> userIdList = dto.getUserIdList();
        if (StringUtils.isNotEmpty(roleId) && CollectionUtils.isNotEmpty(userIdList)) {
           return roleRService.delRoleUserRelation(roleId, userIdList, oldFnshDttm, newBgnDttm);
        }
        return false;
    }

    @Override
    public BaseResult getUserMenu() {
        String userId = UserUtils.getLoginUser().getUserId();
        List<String> superUserIds = Constants.SUPER_ACCOUNT_USER_ID;
        List<String> superRoleIds = Constants.SUPER_ACCOUNT_ROLE_ID;
        boolean flag = false;
        if (superUserIds.contains(userId)) {
            flag = true;
        }
        if (!flag) {
            // 查询用户绑定的角色
            List<String> roleIdList = roleRService.getRoleIdsByUserId(userId);
            if (CollectionUtils.isNotEmpty(roleIdList)) {
                for (String roleId : roleIdList) {
                    if (superRoleIds.contains(roleId)) {
                        flag = true;
                        break;
                    }
                }
            }
        }
        if (flag) {
            List<AuthTreeDto> authTreeDtoList = authObjBasiMService.getAuthObjTree("");
            List<TreeDto> treeDtos = TreeUtils.getTree(authTreeDtoList);
            return BaseResult.successMsg("查询成功!", treeDtos);
        } else {
            // 查询用户绑定的角色
            List<String> roleIdList = roleRService.getRoleIdsByUserId(userId);
            if (CollectionUtils.isNotEmpty(roleIdList)) {
                List<AuthTreeDto> authTreeDtoList = authObjBasiMService.getAuthObjTreeByRoleIds(roleIdList);
                List<TreeDto> treeDtos = TreeUtils.getTree(authTreeDtoList);
                if (CollectionUtils.isNotEmpty(treeDtos)) {
                    return BaseResult.successMsg("查询成功!", treeDtos);
                }
            }
        }
        return BaseResult.failedMsg("无菜单权限,请联系管理员!");
    }

    /**
     * 将数据更新为不可用
     * @param cvUserId
     * @return
     */
    private boolean updateFnlYn(String cvUserId, Date oldFnshDttm) {
        UpdateWrapper<UserBasiM> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().set(UserBasiM::getRecFnshDttm, oldFnshDttm)
                .set(UserBasiM::getFnlYn, Constants.FnlYn.NO);
        updateWrapper.lambda().eq(UserBasiM::getCvUserId, cvUserId)
                .eq(UserBasiM::getRecFnshDttm, Constants.FNSHDTTM);
        return this.update(updateWrapper);

    }

    /**
     * 批量更新为不可用
     * @param cvUserIdList
     * @return
     */
    private boolean updateBatchFnlYn(Date oldFnshDttm, List<String> cvUserIdList) {
        if (CollectionUtils.isNotEmpty(cvUserIdList)) {
            List<String> userDstsCdList = new ArrayList<>();
            userDstsCdList.add(Constants.UserDstsCd.USER);
            userDstsCdList.add(Constants.UserDstsCd.DEPT);
            UpdateWrapper<UserBasiM> updateWrapper = new UpdateWrapper<>();
            updateWrapper.lambda().set(UserBasiM::getRecFnshDttm, oldFnshDttm)
                    .set(UserBasiM::getFnlYn, Constants.FnlYn.NO);
            updateWrapper.lambda().in(UserBasiM::getCvUserId, cvUserIdList)
                    .in(UserBasiM::getCvUserDstsCd, userDstsCdList)
                    .eq(UserBasiM::getRecFnshDttm, Constants.FNSHDTTM);
            return this.update(updateWrapper);
        }
        return false;
    }
}
