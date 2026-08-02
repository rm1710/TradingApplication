package com.trading.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
public class TwoFactorOTP {
	@Id
	private String id;
	
	private String otp;
	
	@JsonProperty(access= JsonProperty.Access.WRITE_ONLY)
	@OneToOne
	private User user;
	
	@JsonProperty(access= JsonProperty.Access.WRITE_ONLY)
	private String jwt;
}
