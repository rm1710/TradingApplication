package com.trading.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.trading.entity.User;



public interface UserRepository extends JpaRepository<User, Long>{

	User findByEmail(String email);
	
	
	

}
