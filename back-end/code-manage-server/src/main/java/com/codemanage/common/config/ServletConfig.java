package com.codemanage.common.config;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

/**
 * 配置重定向路由，解决浏览器刷新后找不到路径报404的问题 或者在前端路由跳转时带上#也可解决此问题
 * @author hyh
 * @since 2022-05-30
 **/
//@Configuration
public class ServletConfig {

    @Bean
    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer() {
        return factory -> {
            ErrorPage errorPage = new ErrorPage(HttpStatus.NOT_FOUND,"/index.html");
            factory.addErrorPages(errorPage);
        };
    }

}
