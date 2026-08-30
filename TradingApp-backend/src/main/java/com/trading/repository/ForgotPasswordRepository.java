package com.trading.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trading.entity.ForgotPasswordTokenEntity;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPasswordTokenEntity, String>{
	ForgotPasswordTokenEntity findByUserId(String userId);
}
