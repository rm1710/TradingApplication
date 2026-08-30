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
public class ForgotPasswordTokenEntity {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private String id;
	
	@OneToOne
	private UserEntity user;
	
	private String otp;
	
	private VerificationType verificationType;
	
	private String sendTo;
}
