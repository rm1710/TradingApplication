package com.trading.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trading.entity.UserEntity;
import com.trading.models.AuthResponse;
import com.trading.service.AuthUserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private final AuthUserService userService;

	public AuthController(AuthUserService userService) {
		this.userService = userService;
	}

	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> register(@RequestBody UserEntity user) throws Exception {
		AuthResponse auth = userService.register(user);
		return new ResponseEntity<>(auth, HttpStatus.CREATED);
	}
	
	
	@PostMapping("/signin")
	public ResponseEntity<AuthResponse> login(@RequestBody UserEntity user) throws Exception{
		AuthResponse auth= userService.signin(user);
		return new ResponseEntity<>(auth,HttpStatus.ACCEPTED);

	}
	
	
	
	public ResponseEntity<AuthResponse> verifySigningOtp(@PathVariable String otp, @RequestParam String id) throws Exception{
		AuthResponse auth= userService.VerifyOTP(otp,id);
		return new ResponseEntity<>(auth, HttpStatus.OK);
	}
}
