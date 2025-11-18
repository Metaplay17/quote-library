package org.example.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix = "app")
@Data
public class AppProperties {
    private Long jwtExpiration;
    private String secretKey;
}
