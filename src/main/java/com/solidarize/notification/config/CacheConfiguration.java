package com.solidarize.notification.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class CacheConfiguration {

    @Bean
    public ConcurrentHashMap<String, Integer> buildCache() {
        ConcurrentHashMap<String, Integer> cache = new ConcurrentHashMap<>();
        cache.put("mail", 0);
        return cache;
    }
}
