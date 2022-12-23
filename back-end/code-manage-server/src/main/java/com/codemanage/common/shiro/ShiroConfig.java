package com.codemanage.common.shiro;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro 配置
 * @author hyh
 * @since  2022-06-14
 **/
@Configuration
public class ShiroConfig {


    /**
     * 配置访问资源权限
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
        // 配置安全管理器
        filterFactoryBean.setSecurityManager(securityManager);
        // 自定义拦截器
        Map<String, Filter> filtersMap = new LinkedHashMap<String, Filter>();
        filtersMap.put("customUserFilter",new CustomUserFilter());
        filterFactoryBean.setFilters(filtersMap);
        // 配置受限资源
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        filterChainDefinitionMap.put("/", "anon");
        filterChainDefinitionMap.put("/favicon.ico", "anon");
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/index.html/**", "anon");
        filterChainDefinitionMap.put("/index.html#/**", "anon");
        filterChainDefinitionMap.put("/config.js", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/img/**", "anon");
        filterChainDefinitionMap.put("/doc.html#/**", "anon");

        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/logout", "anon");
        filterChainDefinitionMap.put("/api/*", "anon");
        filterChainDefinitionMap.put("/accountDataSource/*", "anon");
        filterChainDefinitionMap.put("/**", "customUserFilter");
        filterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return filterFactoryBean;
    }

    /**
     * securityManager 管理器
     * @return
     */
    @Bean("securityManager")
    public SessionsSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置自定义realm
        securityManager.setRealm(userRealm());
        // 设置 session
        securityManager.setSessionManager(sessionManager());
        // 设置 ehcache
        securityManager.setCacheManager(ehCacheManager());
        return securityManager;
    }

    /**
     * 设置会话管理
     *
     * @return
     */
    @Bean
    public DefaultWebSessionManager sessionManager(){
        CustomSessionManager sessionManager = new CustomSessionManager();
        // 设置session过期时间，单位毫秒; 30分钟
        sessionManager.setGlobalSessionTimeout(1000*60*30);
        // 删除无效session
        sessionManager.setDeleteInvalidSessions(true);
        // 去掉 shiro 登录时url里的JSESSIONID
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        // 创建Cookie
        Cookie cookie = new SimpleCookie("MEDICALJSESSIONID");
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        sessionManager.setSessionIdCookie(cookie);
        return sessionManager;
    }

    /**
     * 配置 Ehcache 缓存
     * @return
     */
    @Bean
    public EhCacheManager ehCacheManager() {
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:ehcache/ehcache-shiro.xml");
        return cacheManager;
    }

    /**
     * 自定义realm
     * @return
     */
    @Bean
    public Realm userRealm() {
        CustomRealm realm = new CustomRealm();
//        // 密码算法
//        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
//        // 设置加密算法 这里使用MD5算法
//        hashedCredentialsMatcher.setHashAlgorithmName("md5");
//        // 设置加密次数，比如两次
//        hashedCredentialsMatcher.setHashIterations(1);
//        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
//        realm.setCredentialsMatcher(hashedCredentialsMatcher);
        realm.setCredentialsMatcher(credentialsMatcher());
        return realm;
    }

    /**
     * 自定义密码凭证
     * @return
     */
    @Bean
    public RetryLimitCredentialsMatcher credentialsMatcher() {
        RetryLimitCredentialsMatcher credentialsMatcher = new RetryLimitCredentialsMatcher(ehCacheManager());
        // 加密算法
        credentialsMatcher.setHashAlgorithmName("SHA-256");
        // 加密次数
        credentialsMatcher.setHashIterations(1);
        // 是否存储为16进制
        credentialsMatcher.setStoredCredentialsHexEncoded(true);
        return credentialsMatcher;
    }

    /**
     * Shiro生命周期处理器
     * @return
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     *  开启shiro aop注解支持.
     *  使用代理方式;所以需要开启代码支持;
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor  = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor ;
    }
}
