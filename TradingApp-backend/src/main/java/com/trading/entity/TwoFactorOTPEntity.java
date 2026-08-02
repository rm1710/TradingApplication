package com.trading.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="TA_TWO_FACTOR_OTP")
public class TwoFactorOTPEntity {
	@Id
	private String id;
	
	private String otp;
	
	@JsonProperty(access= JsonProperty.Access.WRITE_ONLY)
	@OneToOne
	private UserEntity user;
	
	@JsonProperty(access= JsonProperty.Access.WRITE_ONLY)
	private String jwt;
}
