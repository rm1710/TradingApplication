package com.trading.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trading.entity.TwoFactorOTP;
import com.trading.entity.User;

public interface TwoFactoryOtpRepository extends JpaRepository<TwoFactorOTP, String> {

	TwoFactorOTP findByUserId(Long userId);

}