package com.trading.service;

import static org.apache.commons.lang3.StringUtils.isBlank;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;

    public Authentication authenticate(String email, String password) {

        if (isBlank(email)) {
            throw new BadCredentialsException("Email cannot be empty.");
        }


        if (isBlank(password)) {
            throw new BadCredentialsException("Password cannot be empty.");
        }

        email = email.trim();

        try {
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password));
        } catch (BadCredentialsException ex) {
            throw new BadCredentialsException("Invalid email or password.");
        }
    }
}