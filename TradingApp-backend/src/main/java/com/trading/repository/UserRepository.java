package com.trading.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.trading.entity.UserEntity;



public interface UserRepository extends JpaRepository<UserEntity, Long>{

	UserEntity findByEmail(String email);

}
