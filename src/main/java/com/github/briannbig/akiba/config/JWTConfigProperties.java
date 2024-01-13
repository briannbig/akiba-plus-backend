package com.github.briannbig.akiba.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt-properties.app")
public record JWTConfigProperties(String jwtSecret, String jwtExpirationMs, String jwtRefreshExpirationMs) {
}
