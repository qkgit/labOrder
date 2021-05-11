package com.bdu.laborder.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @Author Qi
 * @data 2021/4/20 14:42
 */
@Configuration
public class MyWebConfig implements WebMvcConfigurer {

    @Bean
    public HandlerInterceptor getMyInterceptor() {
        return new MyInterceptor();
    }

    @Override
    public void addInterceptors( InterceptorRegistry registry) {
        InterceptorRegistration ir = registry.addInterceptor(getMyInterceptor());
        ir.addPathPatterns("/*");
        ir.excludePathPatterns("/login");
        ir.excludePathPatterns("/logout");
    }
}
