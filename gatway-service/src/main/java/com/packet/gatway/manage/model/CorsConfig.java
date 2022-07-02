package com.packet.gatway.manage.model;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

/**
 * 解决跨域问题，代码版
 * 另外一种方式为配置版，已在nacos中配置实现
 */
@Configuration
public class CorsConfig
{
    @Bean
    public CorsWebFilter corsWebFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 配置跨域
        CorsConfiguration config = new CorsConfiguration();
        // 配置请求头 此处为任意请求头
        config.addAllowedHeader("*");
        // 配置请求方式 此处为任意方式
        config.addAllowedMethod("*");
        // 配置请求来源
        config.addAllowedOrigin("*");
        // 允许携带cookie跨域
        config.setAllowCredentials(true);
        source.registerCorsConfiguration("/**", config);
        CorsWebFilter corsWebFilter = new CorsWebFilter(source);
        return corsWebFilter;
    }

}
