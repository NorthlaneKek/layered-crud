package com.example.demo.application.security;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private final JwtConfig jwtConfig;

    public JwtAuthorizationFilter(AuthenticationManager auth, JwtConfig jwtConfig) {

        super(auth);
        this.jwtConfig = jwtConfig;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        UsernamePasswordAuthenticationToken authToken = getAuthToken(request);
        if (authToken == null) {
            filterChain.doFilter(request, response);
            return;
        }

        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthToken(HttpServletRequest request) {
        String token = request.getHeader(jwtConfig.getAuthHeader());

        if (!StringUtils.isEmpty(token) && (token.startsWith(jwtConfig.getTokenPrefix()) || token.startsWith("ey"))) {
            byte[] secret = jwtConfig.getSecret().getBytes();

            Jws<Claims> parsedToken = Jwts.parser().setSigningKey(secret)
                    .parseClaimsJws(token.replace(jwtConfig.getTokenPrefix(), "").trim());

            String subject = parsedToken.getBody().getSubject();

            List<SimpleGrantedAuthority> authorities = ((List<?>) parsedToken.getBody().get("authorities")).stream()
                    .map(authority -> new SimpleGrantedAuthority((String) authority)).collect(Collectors.toList());

            if (!StringUtils.isEmpty(subject)) {
                return new UsernamePasswordAuthenticationToken(subject, null, authorities);
            }
        }
        return null;
    }
}
