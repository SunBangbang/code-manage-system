package com.codemanage.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codemanage.common.constants.Constants;
import com.codemanage.common.entity.BaseResult;
import com.codemanage.common.util.UserUtils;
import com.codemanage.system.dto.request.AuthObjSaveDto;
import com.codemanage.system.dto.response.AuthTreeDto;
import com.codemanage.system.entity.AuthObjBasiM;
import com.codemanage.system.entity.UserRoleR;
import com.codemanage.system.mapper.AuthObjBasiMMapper;
import com.codemanage.system.service.IAuthObjBasiMService;
import com.codemanage.system.service.IAuthRService;
import com.codemanage.system.service.IUserRoleRService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * <p>
 * CVSO_权限对象基础_主表 服务实现类
 * </p>
 *
 * @author hyh
 * @since 2022-07-16
 */
@Service
public class AuthObjBasiMServiceImpl extends ServiceImpl<AuthObjBasiMMapper, AuthObjBasiM> implements IAuthObjBasiMService {

    @Autowired
    private AuthObjBasiMMapper authObjBasiMMapper;

    @Autowired
    private IAuthRService authRService;

    @Autowired
    private IUserRoleRService userRoleRService;

    /**
     * 查询菜单树
     * @param name
     * @return
     */
    @Override
    public List<AuthTreeDto> getAuthObjTree(String name) {
        List<String> dstsCdList = new ArrayList<>();
        dstsCdList.add(Constants.AuthDstsCd.FOLDER);
        dstsCdList.add(Constants.AuthDstsCd.MENU);
        return authObjBasiMMapper.getAuthObjTree(name, dstsCdList);
    }

    /**
     * 查询类型为Folder的菜单树
     * @return
     */
    @Override
    public List<AuthTreeDto> getAuthFolderTree() {
        List<String> dstsCdList = new ArrayList<>();
        dstsCdList.add(Constants.AuthDstsCd.FOLDER);
        return authObjBasiMMapper.getAuthObjTree(null, dstsCdList);
    }

    /**
     * 检查屏幕id是否唯一
     * @param uiId
     * @return
     */
    @Override
    public boolean checkUiId(String uiId) {
        int count = authObjBasiMMapper.checkUiId(uiId);
        if (count < 1) {
            return true;
        }
        return false;
    }

    /**
     * 检查菜单名称是否唯一
     * @param name
     * @return
     */
    @Override
    public boolean checkAuthName(String name) {
        int count = authObjBasiMMapper.checkAuthName(name);
        if (count < 1) {
            return true;
        }
        return false;
    }

    /**
     * 添加菜单
     * @param dto
     * @return
     */
    @Override
    public BaseResult addAuthObj(AuthObjSaveDto dto) {
        Date newBgnDttm = new Date();
        AuthObjBasiM obj = new AuthObjBasiM();
        BeanUtils.copyProperties(dto, obj);
        obj.setCvAuthObjId(UUID.randomUUID().toString());
        obj.setCretCvUserId(UserUtils.getLoginUser().getUserId());
        obj.setFnlYn(Constants.FnlYn.YES);
        obj.setRecFnshDttm(Constants.FNSHDTTM);
        obj.setRecBgnDttm(newBgnDttm);
        // 1. 保存权限
        boolean flag = this.save(obj);
        if (flag) {
        // 2. 保存关联关系
            AuthObjBasiM hlvObj = this.getObjById(obj.getHlvCvAuthObjId(), Constants.FNSHDTTM);
            if ("0".equals(obj.getHlvCvAuthObjId())){
                hlvObj = new AuthObjBasiM();
                hlvObj.setCvAuthObjId("0");
                hlvObj.setCvAuthObjDstsCd(Constants.AuthDstsCd.FOLDER);
            }
            authRService.saveAuthR(hlvObj, obj, newBgnDttm);
            return BaseResult.successMsg("添加菜单成功！");
        }
        return BaseResult.failedMsg("添加菜单失败！");
    }

    /**
     * 根据id查询详情
     * @param id
     * @param fnshDttm
     * @return
     */
    @Override
    public AuthObjBasiM getObjById(String id, Date fnshDttm) {
        QueryWrapper<AuthObjBasiM> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(AuthObjBasiM::getCvAuthObjId, id)
                .eq(AuthObjBasiM::getRecFnshDttm, fnshDttm);
        return this.getOne(queryWrapper);
    }

    @Override
    public BaseResult editAuthObj(AuthObjSaveDto dto) {
        Date oldFnshDttm = new Date();
        Date bgnDttm = new Date(oldFnshDttm.getTime() + 1);
        AuthObjBasiM obj = this.getObjById(dto.getCvAuthObjId(),Constants.FNSHDTTM);
        String oldHlvId = obj.getHlvCvAuthObjId();
        String newHlvId = dto.getHlvCvAuthObjId();
        // 1. 更新原来用户状态
        this.updateFnlYn(dto.getCvAuthObjId(), oldFnshDttm);
        obj.setRecFnshDttm(Constants.FNSHDTTM);
        obj.setRecBgnDttm(bgnDttm);
        obj.setCvAuthObjDstsCd(dto.getCvAuthObjDstsCd());
        obj.setHlvCvAuthObjId(dto.getHlvCvAuthObjId());
        obj.setMenuUsgYn(dto.getMenuUsgYn());
        obj.setDsplSrno(dto.getDsplSrno());
        obj.setCvUiId(dto.getCvUiId());
        obj.setAuthObjNm(dto.getAuthObjNm());
        obj.setAuthObjExplTxt(dto.getAuthObjExplTxt());
        obj.setCretCvUserId(UserUtils.getLoginUser().getUserId());
        boolean flag = this.save(obj);
        if (flag && !oldHlvId.equals(newHlvId)) {
            // 3. 更新关系数据
            authRService.updateFnlYn(obj.getHlvCvAuthObjId(), obj.getCvAuthObjId(), oldFnshDttm);
            // 4. 插入关系数据
            AuthObjBasiM hlvObj = this.getObjById(obj.getHlvCvAuthObjId(), Constants.FNSHDTTM);
            if ("0".equals(obj.getHlvCvAuthObjId())){
                hlvObj = new AuthObjBasiM();
                hlvObj.setHlvCvAuthObjId("0");
                hlvObj.setCvAuthObjDstsCd(Constants.AuthDstsCd.FOLDER);
            }
            authRService.saveAuthR(hlvObj, obj, bgnDttm);
        }
        if (flag) {
            return BaseResult.successMsg("编辑菜单成功！");
        }
        return BaseResult.failedMsg("编辑菜单失败！");
    }

    /**
     * 删除菜单
     * @param id
     * @return
     */
    @Override
    public boolean delAuthObj(String id) {
        Date oldFnshDttm = new Date();
        Date newBgnDttm = new Date(oldFnshDttm.getTime() + 1);
        List<String> idList = authObjBasiMMapper.getChildIdsById(id);
        this.updateBatchFnlYn(oldFnshDttm, idList);
        boolean flag = this.insertByAuthObjDelete(oldFnshDttm, newBgnDttm, idList);
        authRService.delAuthObj(oldFnshDttm, newBgnDttm, idList);
        return flag;
    }

    /**
     * 查询角色的菜单树
     * @param roleId
     * @return
     */
    @Override
    public List<AuthTreeDto> getAuthObjTreeByRoleId(String roleId) {
        //1. 查询所有使用中的菜单
        List<AuthTreeDto> allList = authObjBasiMMapper.getAuthObjList();
        //2. 查询角色绑定的菜单
        List<UserRoleR> userRoleRList = userRoleRService.getByRoleId(roleId);
        if (CollectionUtils.isNotEmpty(userRoleRList)) {
            for (AuthTreeDto authTreeDto : allList) {
                for (UserRoleR userRoleR : userRoleRList) {
                    if (authTreeDto.getId().equals(userRoleR.getCvAuthObjId())) {
                        authTreeDto.setChecked(true);
                    }
                }
            }
        }

        return allList;
    }

    /**
     * 添加角色和菜单关系
     * @param roleId
     * @param newAuthIds
     * @return
     */
    @Override
    public BaseResult addRoleAuthRelation(String roleId, List<String> newAuthIds) {
        Date oldFnshDttm = new Date();
        Date newBgnDttm = new Date(oldFnshDttm.getTime() + 1);
        // 1. 查询角色之前绑定的所有菜单
        List<String> oldAuthIds = new ArrayList<>();
        List<UserRoleR> userRoleRList = userRoleRService.getByRoleId(roleId);
        if (CollectionUtils.isNotEmpty(userRoleRList)) {
            for (UserRoleR userRoleR : userRoleRList) {
                oldAuthIds.add(userRoleR.getCvAuthObjId());
            }
        }
        List<String> addAuthIds = new ArrayList<>();
        List<String> delAuthIds = new ArrayList<>();
        if (CollectionUtils.isEmpty(newAuthIds)) {
            delAuthIds = oldAuthIds;
        } else if (CollectionUtils.isEmpty(oldAuthIds)) {
            addAuthIds = newAuthIds;
        } else {
            addAuthIds = newAuthIds.stream().filter(item -> !oldAuthIds.contains(item)).collect(Collectors.toList());
            delAuthIds = oldAuthIds.stream().filter(item -> !newAuthIds.contains(item)).collect(Collectors.toList());
        }
        if (CollectionUtils.isNotEmpty(delAuthIds)) {
            // 2. 需要删除的关联关系
            userRoleRService.delRoleAuth(roleId, delAuthIds, oldFnshDttm, newBgnDttm);
        }
        if (CollectionUtils.isNotEmpty(addAuthIds)) {
            // 3. 增加的关联关系
            List<AuthObjBasiM> addAuthList = this.listByIds(addAuthIds);
            userRoleRService.addRoleAuth(roleId, addAuthList, newBgnDttm);
        }
        return BaseResult.successMsg("绑定完成!");
    }

    @Override
    public List<AuthTreeDto> getAuthObjTreeByRoleIds(List<String> roleIdList) {

        List<AuthTreeDto> resultList = new ArrayList<>();
        //1. 查询所有使用中的菜单
        List<AuthTreeDto> allList = authObjBasiMMapper.getAuthObjList();
        //2. 查询角色绑定的菜单
        List<String> authIds = userRoleRService.getAuthIdByRoleIds(roleIdList);
        for (AuthTreeDto authTreeDto : allList) {
            for (String authId : authIds) {
                if (authTreeDto.getId().equals(authId)) {
                    resultList.add(authTreeDto);
                }
            }
        }
        return resultList;
    }


    private boolean insertByAuthObjDelete(Date oldFnshDttm, Date newBgnDttm, List<String> idList) {
        QueryWrapper<AuthObjBasiM> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(AuthObjBasiM::getCvAuthObjId, idList)
                .eq(AuthObjBasiM::getRecFnshDttm, oldFnshDttm);
        List<AuthObjBasiM> list = this.list(queryWrapper);
        for (AuthObjBasiM obj : list) {
            obj.setRecFnshDttm(newBgnDttm);
            obj.setRecBgnDttm(newBgnDttm);
            obj.setFnlYn(Constants.FnlYn.YES);
            obj.setCretCvUserId(UserUtils.getLoginUser().getUserId());
        }
        return this.saveBatch(list);
    }
    private boolean updateFnlYn(String cvAuthObjId, Date oldFnshDttm) {
        UpdateWrapper<AuthObjBasiM> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().set(AuthObjBasiM::getRecFnshDttm, oldFnshDttm)
                .set(AuthObjBasiM::getFnlYn, Constants.FnlYn.NO);
        updateWrapper.lambda().eq(AuthObjBasiM::getCvAuthObjId, cvAuthObjId)
                .eq(AuthObjBasiM::getRecFnshDttm, Constants.FNSHDTTM);
        return this.update(updateWrapper);
    }

    private boolean updateBatchFnlYn(Date oldFnshDttm, List<String> idList) {
        if (CollectionUtils.isNotEmpty(idList)) {
            UpdateWrapper<AuthObjBasiM> updateWrapper = new UpdateWrapper<>();
            updateWrapper.lambda().set(AuthObjBasiM::getRecFnshDttm, oldFnshDttm)
                    .set(AuthObjBasiM::getFnlYn, Constants.FnlYn.NO);
            updateWrapper.lambda().in(AuthObjBasiM::getCvAuthObjId, idList)
                    .eq(AuthObjBasiM::getRecFnshDttm, Constants.FNSHDTTM);
            return this.update(updateWrapper);
        }
        return false;
    }

    private List<AuthObjBasiM> listByIds(List<String> ids) {
        QueryWrapper<AuthObjBasiM> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(AuthObjBasiM::getCvAuthObjId, ids)
                .eq(AuthObjBasiM::getRecFnshDttm, Constants.FNSHDTTM);
        return this.list(queryWrapper);
    }

}
