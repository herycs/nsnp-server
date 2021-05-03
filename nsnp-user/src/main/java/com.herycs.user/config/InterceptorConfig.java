package com.herycs.user.config;

import com.herycs.user.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

//@Configuration
//public class InterceptorConfig extends WebMvcConfigurationSupport {
//
//    @Autowired
//    private JwtInterceptor jwtInterceptor;
//
//    protected void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(jwtInterceptor)
//                .addPathPatterns("/**")
//                .excludePathPatterns("/**/login");
//    }
//}
