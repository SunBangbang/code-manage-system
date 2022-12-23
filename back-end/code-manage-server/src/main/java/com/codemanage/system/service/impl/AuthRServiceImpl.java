package com.codemanage.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codemanage.common.constants.Constants;
import com.codemanage.common.util.UserUtils;
import com.codemanage.system.entity.AuthObjBasiM;
import com.codemanage.system.entity.AuthR;
import com.codemanage.system.mapper.AuthRMapper;
import com.codemanage.system.service.IAuthRService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * CVSO_权限_关系表 服务实现类
 * </p>
 *
 * @author hyh
 * @since 2022-07-16
 */
@Service
public class AuthRServiceImpl extends ServiceImpl<AuthRMapper, AuthR> implements IAuthRService {


    /**
     * 保存菜单关联关系
     * @param hlvObj
     * @param obj
     * @param newBgnDttm
     * @return
     */
    @Override
    public boolean saveAuthR(AuthObjBasiM hlvObj, AuthObjBasiM obj, Date newBgnDttm) {
        if (hlvObj != null && obj != null) {
            AuthR authR = new AuthR();
            authR.setHlvCvAuthObjId(hlvObj.getCvAuthObjId());
            authR.setLlvCvAuthObjId(obj.getCvAuthObjId());
            authR.setRecFnshDttm(Constants.FNSHDTTM);
            authR.setRecBgnDttm(newBgnDttm);
            authR.setHlvCvAuthObjDstsCd(hlvObj.getCvAuthObjDstsCd());
            authR.setLlvCvAuthObjDstsCd(obj.getCvAuthObjDstsCd());
            authR.setCretCvUserId(UserUtils.getLoginUser().getUserId());
            return this.save(authR);
        }
        return false;
    }

    /**
     * 更新菜单关联关系为不可用
     * @param hlvCvAuthObjId
     * @param cvAuthObjId
     * @param oldFnshDttm
     * @return
     */
    @Override
    public boolean updateFnlYn(String hlvCvAuthObjId, String cvAuthObjId, Date oldFnshDttm) {
        UpdateWrapper<AuthR> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().set(AuthR::getRecFnshDttm, oldFnshDttm)
                .set(AuthR::getFnlYn, Constants.FnlYn.NO);
        updateWrapper.lambda().eq(AuthR::getHlvCvAuthObjId, hlvCvAuthObjId)
                .eq(AuthR::getLlvCvAuthObjId, cvAuthObjId)
                .eq(AuthR::getRecFnshDttm, Constants.FNSHDTTM);
        return this.update(updateWrapper);
    }

    /**
     * 删除菜单关联关系
     * @param oldFnshDttm
     * @param newBgnDttm
     * @param idList
     * @return
     */
    @Override
    public boolean delAuthObj(Date oldFnshDttm, Date newBgnDttm, List<String> idList) {
        UpdateWrapper<AuthR> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().set(AuthR::getRecFnshDttm, oldFnshDttm)
                .set(AuthR::getFnlYn, Constants.FnlYn.NO);
        updateWrapper.lambda().eq(AuthR::getRecFnshDttm, Constants.FNSHDTTM)
                .and(wrapper -> wrapper.in(AuthR::getHlvCvAuthObjId, idList).
                        or().in(AuthR::getLlvCvAuthObjId, idList));

        boolean flag = this.update(updateWrapper);
        if (flag) {
            QueryWrapper<AuthR> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(AuthR::getRecFnshDttm, oldFnshDttm)
                    .and(wrapper -> wrapper.in(AuthR::getHlvCvAuthObjId, idList).
                            or().in(AuthR::getLlvCvAuthObjId, idList));
            List<AuthR> list = this.list(queryWrapper);
            for (AuthR authR : list) {
                authR.setRecFnshDttm(newBgnDttm);
                authR.setRecBgnDttm(newBgnDttm);
                authR.setFnlYn(Constants.FnlYn.YES);
                authR.setCretCvUserId(UserUtils.getLoginUser().getUserId());
            }
            return this.saveBatch(list);
        }
        return false;
    }
}
