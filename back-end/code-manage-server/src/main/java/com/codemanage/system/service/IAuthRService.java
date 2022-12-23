package com.codemanage.system.service;

import com.codemanage.system.entity.AuthObjBasiM;
import com.codemanage.system.entity.AuthR;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * CVSO_权限_关系表 服务类
 * </p>
 *
 * @author hyh
 * @since 2022-07-16
 */
public interface IAuthRService extends IService<AuthR> {

    /**
     * 创建菜单关联关系
     * @param hlvObj
     * @param obj
     * @param newBgnDttm
     * @return
     */
    boolean saveAuthR(AuthObjBasiM hlvObj, AuthObjBasiM obj, Date newBgnDttm);

    /**
     * 更新关系为不可以
     * @param hlvCvAuthObjId
     * @param cvAuthObjId
     * @param oldFnshDttm
     * @return
     */
    boolean updateFnlYn(String hlvCvAuthObjId, String cvAuthObjId, Date oldFnshDttm);

    /**
     * 删除菜单关联关系
     * @param oldFnshDttm
     * @param newBgnDttm
     * @param idList
     * @return
     */
    boolean delAuthObj(Date oldFnshDttm, Date newBgnDttm, List<String> idList);
}
