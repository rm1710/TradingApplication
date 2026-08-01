package com.trading.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trading.entity.User;
import com.trading.models.AuthResponse;
import com.trading.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private final UserService userService;

	public AuthController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> register(@RequestBody User user) throws Exception {
		AuthResponse auth = userService.register(user);
		return new ResponseEntity<>(auth, HttpStatus.CREATED);
	}
	
	
	@PostMapping("/signin")
	public ResponseEntity<AuthResponse> login(@RequestBody User user) throws Exception{
		AuthResponse auth= userService.signin(user);
		return new ResponseEntity<>(auth,HttpStatus.ACCEPTED);

	}
}
