package com.trading.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trading.entity.TwoFactorOTPEntity;
import com.trading.entity.UserEntity;
import com.trading.repository.TwoFactoryOtpRepository;

@Service
public class TwoFactorOtpService {
	
	@Autowired
	public TwoFactoryOtpRepository twoFactorOtpRepository;
	
	
	public TwoFactorOTPEntity createTwoFactorOTP(UserEntity user, String otp,String jwt) {
		UUID uuid= UUID.randomUUID();
		String id = uuid.toString();
		TwoFactorOTPEntity twoFactorOTP = new TwoFactorOTPEntity();
		twoFactorOTP.setOtp(otp);
		twoFactorOTP.setJwt(jwt);
		twoFactorOTP.setId(id);
		twoFactorOTP.setUser(user);
		
		return twoFactorOtpRepository.save(twoFactorOTP);
	}
	
	public TwoFactorOTPEntity findByUserId(Long id) {
	    return twoFactorOtpRepository.findByUserId(id);
	}
	
	public TwoFactorOTPEntity findById(String id) {
		Optional<TwoFactorOTPEntity> otp= twoFactorOtpRepository.findById(id);
		return otp.orElse(null);
	}
	
	public boolean verifyTwoFactorOtp(TwoFactorOTPEntity twoFactorOtp, String otp) {
		return twoFactorOtp.getOtp().equals(otp);
	}
	
	public void deleteTwoFactorOtp(TwoFactorOTPEntity twoFactorOtp) {
		twoFactorOtpRepository.delete(twoFactorOtp);
	}
	
	
}
