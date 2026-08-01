package com.trading.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.trading.entity.User;
import com.trading.models.AuthResponse;
import com.trading.repository.UserRepository;
import com.trading.security.JwtProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;

    public AuthResponse register(User user) throws Exception {

        User existingUser = userRepository.findByEmail(user.getEmail());

        if (existingUser != null) {
            throw new Exception("Email already exists");
        }

        String rawPassword = user.getPassword();

        user.setPassword(passwordEncoder.encode(rawPassword));

        userRepository.save(user);

        Authentication authentication =
                authService.authenticate(user.getEmail(), rawPassword);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = JwtProvider.generateToken(authentication);

        AuthResponse response = new AuthResponse();
        response.setJwt(jwt);
        response.setStatus(true);
        response.setMessage("Registration Successful");

        return response;
    }

    public AuthResponse signin(User user) {
    	
        Authentication authentication =
                authService.authenticate(user.getEmail(), user.getPassword());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = JwtProvider.generateToken(authentication);

        AuthResponse response = new AuthResponse();
        response.setJwt(jwt);
        response.setStatus(true);
        response.setMessage("Login Successful");

        return response;
    }
}