package com.zlr.blog.admin.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Zenglr
 * @program: simple_blog
 * @packagename: com.zlr.blog.admin.config
 * @Description
 * @create 2022-08-09-下午10:51
 */
@Configuration
@MapperScan("com.zlr.blog.admin.mapper")
public class MyBatisPlusConfig {

    //分页插件
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;
    }
}
