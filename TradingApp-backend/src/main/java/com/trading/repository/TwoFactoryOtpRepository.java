package com.trading.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.trading.entity.TwoFactorOTPEntity;

public interface TwoFactoryOtpRepository extends JpaRepository<TwoFactorOTPEntity, String> {

	TwoFactorOTPEntity findByUserId(Long userId);

}