package com.springwebsite.config;

import com.springwebsite.Interceptor.NavMenuInterceptor;
import com.springwebsite.navMenu.NavMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
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
}
