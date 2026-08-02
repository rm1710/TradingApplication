package com.trading.controller;

import com.trading.entity.UserEntity;
import com.trading.enums.VerificationType;
import com.trading.service.EmailService;
import com.trading.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final EmailService emailService;

    @GetMapping("/profile")
    public ResponseEntity<UserEntity> getUserProfile(
            @RequestHeader("Authorization") String jwt) throws Exception {

        return ResponseEntity.ok(userService.findUserProfileByJwt(jwt));
    }

    @PostMapping("/verification/{verificationType}/send-otp")
    public ResponseEntity<String> sendVerificationOtp(
            @RequestHeader("Authorization") String jwt,
            @PathVariable VerificationType verificationType) throws Exception {

        UserEntity user = userService.findUserProfileByJwt(jwt);
        // emailService.sendVerificationOtpEmail(user.getEmail(), otp);

        return ResponseEntity.ok("OTP sent successfully");
    }

    @PatchMapping("/two-factor-auth/verify/{otp}")
    public ResponseEntity<UserEntity> enableTwoFactorAuthentication(
            @RequestHeader("Authorization") String jwt,
            @PathVariable String otp) throws Exception {

        return ResponseEntity.ok(userService.findUserProfileByJwt(jwt));
    }
}