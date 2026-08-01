package com.trading.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.trading.enums.UserRole;
import com.trading.models.TwoFactorAuth;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="TA_USERS")
public class User {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;
	
	private String fullName;
	private String email;
	
	@JsonProperty(access= JsonProperty.Access.WRITE_ONLY)
	private String password;
	
	
	@Embedded
	private TwoFactorAuth twoFactorAuth= new TwoFactorAuth();
	
	
	private UserRole role=UserRole.ROLE_CUSTOMER;
}
