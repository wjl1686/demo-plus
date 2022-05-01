package com.example.demo.common.config;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * GET Spring boot支持在查询参数中加{}[]字符
 *
 * @author zl
 * @date 2021/8/2 16:06
 */
@Configuration
public class SystemConfig {

    @Bean
    public ServletWebServerFactory webServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.addConnectorCustomizers(connector -> connector.setProperty("relaxedQueryChars", "[]{}"));
        return factory;
    }
}
