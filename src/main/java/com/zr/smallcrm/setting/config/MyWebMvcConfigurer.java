//package com.zr.smallcrm.setting.config;
//
//import com.zr.smallcrm.setting.Interceptor.LoginHandlerInterceptor;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class MyWebMvcConfigurer implements WebMvcConfigurer {
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//
//        String[] addPathPatterns = {
//                "/**"
//        };
//        String[] excludePathPatterns = {
//                "/login.do","/login.jsp","/","/login"
//        };
//
//        registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns(addPathPatterns).excludePathPatterns(excludePathPatterns);
//    }
//}
