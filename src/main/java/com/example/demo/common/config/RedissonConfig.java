package com.example.demo.common.config;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by wujianlong on 2019/04/19.
 */
@Configuration
@Slf4j
public class RedissonConfig {

    @Bean
    public RedissonClient redissonClient() {
        RedissonClient client = null;
        try {
            Config config = new Config();
            config.useSingleServer().setAddress("redis://132.145.125.215:6379");

            client = Redisson.create(config);
        } catch (Exception e) {
            log.error("Redisson-exception" + e);
        }

        return client;
    }
}
