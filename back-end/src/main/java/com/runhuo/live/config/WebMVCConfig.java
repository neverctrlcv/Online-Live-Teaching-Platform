package com.runhuo.live.config;

import com.runhuo.live.interceptor.LoginInterceptor;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @product name: IntelliJ IDEA
 * @Author never debug
 * @Date: 2023/5/8 16:12
 */
@SpringBootConfiguration
public class WebMVCConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .exposedHeaders("*");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor())
                .excludePathPatterns("/admin/**")
                .excludePathPatterns("/searchAndPlay/**")
                .excludePathPatterns("/user/**")
                .addPathPatterns("/video/**")
                //不拦截静态资源
                .excludePathPatterns("/avatar/**","/cover/**","/video/**");
    }

    @Bean
    public LoginInterceptor loginInterceptor(){
        return new LoginInterceptor();
    }
}
