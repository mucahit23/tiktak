package com.tiktak;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
@EnableConfigurationProperties
@EnableCaching
public class TiktakApplication {

    public static void main(final String[] args) {
        SpringApplication.run(TiktakApplication.class, args);
    }

}
