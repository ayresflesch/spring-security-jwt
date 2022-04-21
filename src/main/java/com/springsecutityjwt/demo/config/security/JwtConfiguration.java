package com.springsecutityjwt.demo.config.security;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "application.jwt")
public class JwtConfiguration {
    private String secretKey;
    private Long expiration;
    private String tokenPrefix;
}
