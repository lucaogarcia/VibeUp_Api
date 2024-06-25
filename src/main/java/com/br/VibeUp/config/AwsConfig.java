package com.br.VibeUp.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:aws-config.properties") // Specify the location of your aws-config.properties
@ConfigurationProperties(prefix = "aws")
@Getter
@Setter
public class AwsConfig {


    private String accessKeyId;
    private String secretKey;
    private String region;
}
