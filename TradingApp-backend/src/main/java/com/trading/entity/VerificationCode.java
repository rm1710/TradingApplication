package com.trading.entity;

import com.trading.enums.VerificationType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class VerificationCode {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String otp;
	
	@OneToOne
	private UserEntity user;
	
	private String email;
	
	private String mobile;
	
	private VerificationType verificationType;
	
}
