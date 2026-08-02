package com.trading.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.trading.entity.TwoFactorOTP;
import com.trading.entity.User;
import com.trading.models.AuthResponse;
import com.trading.repository.UserRepository;
import com.trading.security.JwtProvider;
import com.trading.utils.OtpUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	@Autowired
    private final UserRepository userRepository;
	@Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final AuthService authService;
    @Autowired
    private final TwoFactorOtpService twoFactorOtpService;
    
    @Autowired
    private final EmailService emailService;

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
        
        User authUser= userRepository.findByEmail(user.getEmail());
        
        if(user.getTwoFactorAuth().isEnabled()) {
        	AuthResponse res= new AuthResponse();
        	res.setMessage("Two factor auth is enabled");
        	res.setTwoFactorAuthEnabled(true);
        	String otp= OtpUtils.generateOTP();
        	
        	TwoFactorOTP oldTwoFactorOTP= twoFactorOtpService.findByUserId(authUser.getId());
        	
        	if(oldTwoFactorOTP!=null) {
        		twoFactorOtpService.deleteTwoFactorOtp(oldTwoFactorOTP);
        	}
        	
        	TwoFactorOTP newTwoFactorOTP= twoFactorOtpService.createTwoFactorOTP(authUser, otp, jwt);
        	
        	emailService.sendVerificationOtpEmail(user.getEmail(), otp);
        	
        	res.setSession(newTwoFactorOTP.getId());
        }

        AuthResponse response = new AuthResponse();
        response.setJwt(jwt);
        response.setStatus(true);
        response.setMessage("Login Successful");

        return response;
    }

	public AuthResponse VerifyOTP(String otp, String id) throws Exception {
		TwoFactorOTP twoFactorOTP= twoFactorOtpService.findById(id);
		if(twoFactorOtpService.verifyTwoFactorOtp(twoFactorOTP, otp)) {
			AuthResponse res= new AuthResponse();
			res.setMessage("Two factor authentication verified");
			res.setTwoFactorAuthEnabled(true);
			res.setJwt(twoFactorOTP.getJwt());
			return res;
		}
		throw new Exception ("invalid otp");
	}
}