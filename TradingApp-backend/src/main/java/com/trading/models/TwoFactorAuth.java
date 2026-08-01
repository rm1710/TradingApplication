package com.trading.models;

import com.trading.enums.VerificationType;

import lombok.Data;

@Data
public class TwoFactorAuth {
	private boolean isEnabled = false;
	
	private VerificationType sendTo;
}	
