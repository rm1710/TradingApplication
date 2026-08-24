package com.trading.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trading.entity.UserEntity;
import com.trading.entity.VerificationCode;
import com.trading.enums.VerificationType;
import com.trading.repository.VerificationCodeRepository;
import com.trading.utils.OtpUtils;

@Service
public class VerificationCodeService {
	
	@Autowired
	private VerificationCodeRepository verificationCodeRepository;
	
	public VerificationCode sendVerificationCode(UserEntity user, VerificationType verificationType){
		VerificationCode verificationCode1 =new VerificationCode();
		verificationCode1.setOtp(OtpUtils.generateOTP());
		verificationCode1.setVerificationType(null);
		
		return verificationCodeRepository.save(verificationCode1);
	}
	
	VerificationCode getVerificationCode(Long id) throws Exception {
		Optional<VerificationCode> verificationCode= verificationCodeRepository.findById(id);
		if(verificationCode.isPresent()) {
			return verificationCode.get();
		}
		throw new Exception("verification code not found");
	}
	
	public VerificationCode getVerificationCodeByUser(Long userId) {
		return verificationCodeRepository.findByUserId(userId);
	}
	
	public void deleteVerificationCodeById(VerificationCode verificationCode) {
		verificationCodeRepository.delete(verificationCode);
	}
	
}
