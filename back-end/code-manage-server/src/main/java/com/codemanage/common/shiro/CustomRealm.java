package com.codemanage.common.shiro;

import com.codemanage.system.entity.UserBasiM;
import com.codemanage.system.service.IUserBasiMService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 自定义认证 Realm
 * @author hyh
 * @since  2022-06-14
 **/
public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private IUserBasiMService userBasiMService;

    /**
     * 登录用户权限验证（本系统暂无此需求）
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * 用户登录认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 获取登录用户
        String username = (String) authenticationToken.getPrincipal();
        // 查询用户信息
        UserBasiM user = userBasiMService.getByLgnId(username);
        if (null == user) {
            // 用户不存在
            throw new UnknownAccountException();
        }
        if ("N".equals(user.getCvBnacLckStCd())) {
            // 该用户已被禁用
            throw new LockedAccountException();
        }
        // 使用用户名作为盐值
//        ByteSource salt = ByteSource.Util.bytes(username);
        // 将用户数据封装成 AuthenticationInfo 对象返回
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getLgnPwdEncd(), getName());
        return info;
    }
}
