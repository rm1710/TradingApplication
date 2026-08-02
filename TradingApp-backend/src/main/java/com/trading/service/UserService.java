package com.trading.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.trading.entity.UserEntity;
import com.trading.enums.VerificationType;
import com.trading.models.TwoFactorAuth;
import com.trading.repository.UserRepository;
import com.trading.security.JwtProvider;

public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	private JwtProvider jwtProvider;
	
	public UserEntity findUserProfileByJwt(String jwt) throws Exception {
		String email= jwtProvider.getEmailFromToken(jwt);
		UserEntity user= userRepository.findByEmail(email);
		if(user ==null) {
			throw new Exception("user not found");
		}
		return user;
	}
	
	public UserEntity findUserByEmail(String email) throws Exception {
		UserEntity user= userRepository.findByEmail(email);
		if(user ==null) {
			throw new Exception("user not found");
		}
		return user;
	}
	
	public UserEntity findUserById(Long userId) throws Exception {
		Optional<UserEntity> user= userRepository.findById(userId);
		if(user.isEmpty()) {
			throw new Exception("user not found");
		}
		return user.get();
	}
	
	public UserEntity enableTwoFactorAuthentication(VerificationType verificationType, String sendTo,UserEntity user) {
		TwoFactorAuth twoFactorAuth= new TwoFactorAuth();
		twoFactorAuth.setEnabled(true);
		twoFactorAuth.setSendTo(verificationType);
		
		user.setTwoFactorAuth(twoFactorAuth);
		
		return userRepository.save(user);
	}
	
	UserEntity updatePassword(UserEntity user, String newPassword) {
		user.setPassword(newPassword);
		return  userRepository.save(user);
	}
}
