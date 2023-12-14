package com.tiktak.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "image.set")
@Getter
@Setter
public class ImageProperties {

    private int minCount;
    private int maxCount;

}