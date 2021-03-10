package com.springwebsite.config;

import com.springwebsite.Interceptor.NavMenuInterceptor;
import com.springwebsite.navMenu.NavMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class MvcConfig implements WebMvcConfigurer {

    private final NavMenuService navMenuService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new NavMenuInterceptor(navMenuService))
                .addPathPatterns("/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {     // 서버 재시작 없이 정적 리소스 자원 리로드.
        registry.addResourceHandler("/**")
                .addResourceLocations("file:src/main/resources/templates/", "file:src/main/resources/static/", "file:///home/ec2-user/app/step1/spring-website/src/main/resources/static/");
    }
}
