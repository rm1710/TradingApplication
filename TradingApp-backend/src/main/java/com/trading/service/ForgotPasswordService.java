package com.trading.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trading.entity.ForgotPasswordTokenEntity;
import com.trading.entity.UserEntity;
import com.trading.enums.VerificationType;
import com.trading.repository.ForgotPasswordRepository;

@Service
public class ForgotPasswordService {
	 
	@Autowired
	private ForgotPasswordRepository forgotPasswordRepository;
	
	public ForgotPasswordTokenEntity createToken(UserEntity user, String id,String otp, VerificationType verificationType, String sendTo) {
		ForgotPasswordTokenEntity token = new ForgotPasswordTokenEntity();
		token.setUser(user);
		token.setSendTo(sendTo);
		token.setVerificationType(verificationType);
		token.setOtp(otp);
		token.setId(id);
		return forgotPasswordRepository.save(token);
	}
	
	public ForgotPasswordTokenEntity findById(String Id) {
		return null;
	}
	
	public ForgotPasswordTokenEntity findByUser(String userId) {
		return null;
	}
	
	void deleteToken(ForgotPasswordTokenEntity token) {
		
	}
	
	
}
