package com.trading.controller;

import com.trading.entity.UserEntity;
import com.trading.entity.VerificationCode;
import com.trading.enums.VerificationType;
import com.trading.service.EmailService;
import com.trading.service.UserService;
import com.trading.service.VerificationCodeService;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

	@Autowired
    private final UserService userService;
	@Autowired
	private VerificationCodeService verificationCodeService;
	
	@Autowired
    private final EmailService emailService;

    @GetMapping("/profile")
    public ResponseEntity<UserEntity> getUserProfile(
            @RequestHeader("Authorization") String jwt) throws Exception {

        return new ResponseEntity<>(userService.findUserProfileByJwt(jwt),HttpStatus.OK);
    }

    @PostMapping("/verification/{verificationType}/send-otp")
    public ResponseEntity<String> sendVerificationOtp(
            @RequestHeader("Authorization") String jwt,
            @PathVariable VerificationType verificationType) throws Exception {

        UserEntity user = userService.findUserProfileByJwt(jwt);
        
        VerificationCode verificationCode=verificationCodeService.getVerificationCodeByUser(user.getId());
        		
        if(verificationCode==null) {
        	verificationCode=verificationCodeService.sendVerificationCode(user,verificationType);
        }
        if(verificationType.equals(verificationType.EMAIL)) {
        	emailService.sendVerificationOtpEmail(user.getEmail(), verificationCode.getOtp());
        }
        return new ResponseEntity<>("OTP sent successfully",HttpStatus.OK);
    }

    @PatchMapping("/enable-two-factor/verify-otp/{otp}")
    public ResponseEntity<UserEntity> enableTwoFactorAuthentication(
    		@PathVariable String otp,
            @RequestHeader("Authorization") String jwt) throws Exception {
    	UserEntity user= userService.findUserProfileByJwt(jwt);
    	
    	VerificationCode verificationCode= verificationCodeService.getVerificationCodeByUser(user.getId());
    	
    	String sendTo= verificationCode.getVerificationType().equals(VerificationType.EMAIL)?verificationCode.getEmail():verificationCode.getMobile();
    	
    	boolean isVerified= verificationCode.getOtp().equals(otp);
    	
    	if(isVerified) {
    		UserEntity updatedUser=userService.enableTwoFactorAuthentication(verificationCode.getVerificationType(), sendTo, user);
    		
    		verificationCodeService.deleteVerificationCodeById(verificationCode);
    		
    		return new ResponseEntity<>(updatedUser,HttpStatus.OK);
    	}
    	
    	throw new Exception("wrong otp inserted");
    }
}