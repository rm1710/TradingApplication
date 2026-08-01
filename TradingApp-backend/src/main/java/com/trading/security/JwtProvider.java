package com.trading.security;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.trading.constants.JwtConstant;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider {

    private static final SecretKey key =
            Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

    private final JwtService jwtService;

    public JwtProvider(JwtService jwtService,
                       CustomUserDetailsService customUserDetailsService,
                       PasswordEncoder passwordEncoder) {
        this.jwtService = jwtService;
    }


    public static String generateToken(Authentication auth) {
        String roles = populateAuthorities(auth.getAuthorities());

        return Jwts.builder()
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 86400000))
                .claim("email", auth.getName())
                .claim("authorities", roles)
                .signWith(key)
                .compact();
    }

    public String getEmailFromToken(String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        return jwtService.extractEmail(token);
    }

    private static String populateAuthorities(
            Collection<? extends GrantedAuthority> authorities) {

        Set<String> auth = new HashSet<>();

        for (GrantedAuthority authority : authorities) {
            auth.add(authority.getAuthority());
        }

        return String.join(",", auth);
    }
}