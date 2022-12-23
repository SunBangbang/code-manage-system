package com.codemanage.common.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * mybatis-plus 配置
 * @author hyh
 * @since 2022-05-30
 **/
@Configuration
public class MybatisPlusConfig {

    /**
     * 配置 3.4版本之后应该用MybatisPlusInterceptor，在其内部添加一系列使用的拦截器
     * @return
     */
    @Bean
    public MybatisPlusInterceptor paginationInterceptor() {
        MybatisPlusInterceptor mpi = new MybatisPlusInterceptor();
        // 分页拦截器
        PaginationInnerInterceptor page = new PaginationInnerInterceptor();
        mpi.addInnerInterceptor(page);
        return mpi;
    }
}
