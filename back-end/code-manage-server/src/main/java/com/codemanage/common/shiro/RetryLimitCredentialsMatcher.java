package com.codemanage.common.shiro;

import com.codemanage.system.service.IUserBasiMService;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自定义密码校验器
 * @author hyh
 * @since  2022-06-14
 **/
public class RetryLimitCredentialsMatcher extends HashedCredentialsMatcher {

    /**
     * 错误次数
     */
    private Integer maxPasswordCount = 5;

    /**
     * 登录次数缓存
     */
    private Cache<String, AtomicInteger> passwordRetryCache;

    @Autowired
    private IUserBasiMService userBasiMService;

    public RetryLimitCredentialsMatcher(CacheManager cacheManager) {
        passwordRetryCache = cacheManager.getCache("passwordRetryCache");
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        // 获取用户
        String lgnId = (String) token.getPrincipal();
        Set<String> keys = passwordRetryCache.keys();
        // 用户登录次数
        AtomicInteger retryCount = passwordRetryCache.get(lgnId);
        if (null == retryCount) {
            // 用户没有登录过，次数加1并放入缓存
            retryCount = new AtomicInteger(0);
            passwordRetryCache.put(lgnId, retryCount);
        }
        // 如果次数大于最大错误次数，锁定用户并抛出异常
        if (retryCount.incrementAndGet() > maxPasswordCount) {
            userBasiMService.updateCvBnacLckStCd(lgnId, "N");
            throw new LockedAccountException();
        }
        // 校验密码
        boolean matches = super.doCredentialsMatch(token, info);
        if (matches) {
            // 密码正确，清空缓存
            passwordRetryCache.remove(lgnId);
        }
        return matches;
    }
}
