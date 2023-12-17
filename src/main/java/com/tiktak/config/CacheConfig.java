package com.tiktak.config;


import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("questions-cache","last-expertise", "questions-by-carId");
        cacheManager.setCaffeine(Caffeine.newBuilder().expireAfterWrite(1, java.util.concurrent.TimeUnit.DAYS));
        return cacheManager;
    }
}
