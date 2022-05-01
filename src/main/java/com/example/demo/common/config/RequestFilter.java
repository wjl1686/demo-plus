package com.example.demo.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * ===================================================
 *
 * @Title: RequestFilter.java
 * @Package: cn.hsa.spp.tender.biz.config
 * @Description:
 * @author: DuZK
 * @create: 2019年08月03日 13:40
 * @Version: V1.0
 * ===================================================
 */
@Configuration
public class RequestFilter extends CorsFilter {
    public RequestFilter() {
        super(configSource());
    }

    /**
     * 跨域设置
     *
     * @return UrlBasedCorsConfigurationSource
     */
    private static UrlBasedCorsConfigurationSource configSource() {
        List<String> allowedHeaders = new ArrayList<>();
        //增加自定义设置headers参数
        List<String> allowedMethods = new ArrayList<>();
        allowedMethods.add("OPTIONS");
        allowedMethods.add("POST");
        allowedMethods.add("GET");

        CorsConfiguration config = new CorsConfiguration();
        //默认不携带cookies
        config.setAllowCredentials(false);
        config.addAllowedOrigin(CorsConfiguration.ALL);
        config.setAllowedHeaders(allowedHeaders);
        config.setAllowedMethods(allowedMethods);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
