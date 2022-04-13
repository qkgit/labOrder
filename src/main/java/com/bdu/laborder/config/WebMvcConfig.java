package com.bdu.laborder.config;

import com.bdu.laborder.common.constant.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author Qi
 * @data 2021/4/20 14:42
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private  LoginInterceptor loginInterceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /** 本地文件上传路径 */
        registry.addResourceHandler(Constant.RESOURCE_PREFIX + "/**")
                .addResourceLocations("file:" + FileStorageConfig.getUploadDir() + "/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(Constant.RESOURCE_PREFIX+"/**")
                .excludePathPatterns("/login","/logout");
    }


}
