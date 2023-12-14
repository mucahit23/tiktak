package com.tiktak;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@SpringBootApplication
@EnableConfigurationProperties
public class TiktakApplication {

    public static void main(final String[] args) {
        SpringApplication.run(TiktakApplication.class, args);
    }

}
