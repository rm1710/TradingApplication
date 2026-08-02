package com.trading.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.trading.entity.TwoFactorOTP;
import com.trading.entity.User;
import com.trading.repository.TwoFactoryOtpRepository;

public class TwoFactorOtpService {
	
	@Autowired
	public TwoFactoryOtpRepository twoFactorOtpRepository;
	
	
	public TwoFactorOTP createTwoFactorOTP(User user, String otp,String jwt) {
		UUID uuid= UUID.randomUUID();
		String id = uuid.toString();
		TwoFactorOTP twoFactorOTP = new TwoFactorOTP();
		twoFactorOTP.setOtp(otp);
		twoFactorOTP.setJwt(jwt);
		twoFactorOTP.setId(id);
		twoFactorOTP.setUser(user);
		
		return twoFactorOtpRepository.save(twoFactorOTP);
	}
	
	public TwoFactorOTP findByUserId(Long id) {
	    return twoFactorOtpRepository.findByUserId(id);
	}
	
	public TwoFactorOTP findById(String id) {
		Optional<TwoFactorOTP> otp= twoFactorOtpRepository.findById(id);
		return otp.orElse(null);
	}
	
	public boolean verifyTwoFactorOtp(TwoFactorOTP twoFactorOtp, String otp) {
		return twoFactorOtp.getOtp().equals(otp);
	}
	
	public void deleteTwoFactorOtp(TwoFactorOTP twoFactorOtp) {
		twoFactorOtpRepository.delete(twoFactorOtp);
	}
	
	
}
