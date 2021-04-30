package com.example.demo.application.security;

import java.util.Base64;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtConfig {
    private String token;

    @Value("${security.jwt.secret}")
    private String secret;

    @Value("${security.jwt.expire}")
    private long validity;

    @Value("${security.login-url}")
    private String loginUrl;

    @Value("${security.jwt.prefix}")
    private String tokenPrefix;

    @Value("${security.jwt.auth-header}")
    private String authHeader;

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String getToken() {
        return this.token;
    }

    public String getSecret() {
        return this.secret;
    }

    public long getValidity() {
        return this.validity;
    }

    public String getLoginUrl() {
        return this.loginUrl;
    }

    public String getTokenPrefix() {
        return tokenPrefix;
    }

    public String getAuthHeader() {
        return authHeader;
    }
}
