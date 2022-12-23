package com.codemanage.common.config;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;


/**
 * swagger2 配置
 * @author hyh
 * @since 2022-06-06
 */
@Configuration
@Slf4j
public class Swagger2 {
    @Value("${server.port}")
    private int port;

//    @Value("${server.servlet.context-path}")
//    private String contextPath;


    /**
     * 配置swagger2的一些基本内容，如扫描包等
     *      页面地址： http://localhost:8081/swagger-ui/index.html
     *      页面地址： http://localhost:8081/doc.html
     * @return
     */
    @Bean
    public Docket createRestApi() {
//        log.info(" http://localhost:{}{}/doc.html", port, contextPath == null ? "" : contextPath);
        log.info(" http://localhost:{}/doc.html", port);
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                // 扫描包下所有接口
//                .apis(RequestHandlerSelectors.basePackage("com.codemanage"))
                // 只扫描带注解的接口
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build()
//                .pathMapping(contextPath)
                .enable(true);
    }

    /**
     * 构建api文档的详细信息
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 页面标题
                .title("代码管理系统接口测试")
                // 创建人信息
                .contact(new Contact("hyh", "", ""))
                // 版本号
                .version("1.0")
                // 描述
                .description("API")
                .build();
    }
}
