package com.trading.security;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import com.trading.constants.JwtConstant;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	final SecretKey key= Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
	
	public Claims extractClaims(String token) {
		Claims claims = Jwts.parser()
		        .verifyWith(key)
		        .build()
		        .parseSignedClaims(token)
		        .getPayload();
		
		return claims;
	}

	public String extractEmail(String token) {
		return extractClaims(token).get("email", String.class);
	}	
	
	public String extractAuthorities(String token) {

        return extractClaims(token).get("authorities", String.class);
    }
}
