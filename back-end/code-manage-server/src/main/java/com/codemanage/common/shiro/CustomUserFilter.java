package com.codemanage.common.shiro;

import com.codemanage.common.util.UserUtils;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义拦截器，处理用户未登录情况
 * @author hyh
 * @since  2022-06-14
 **/
public class CustomUserFilter extends UserFilter {

    private Logger logger = LoggerFactory.getLogger(CustomUserFilter.class);

    /**
     * 判断用户会话是否失效
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        // 过滤 OPTIONS 请求
        if ("OPTIONS".equals(httpRequest.getMethod().toUpperCase())) {
            return true;
        }
        return super.isAccessAllowed(request, response, mappedValue);
    }


    /**
     * 用户会话失效执行该方法
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestUrl = httpRequest.getRequestURL().toString();
        logger.info("当前请求的接口:" + requestUrl);
        // 解决返回数据跨域问题
        HttpServletResponse res = (HttpServletResponse)response;
        UserUtils.writerMsg(402, "会话已失效!", res);
        return false;
    }
}
