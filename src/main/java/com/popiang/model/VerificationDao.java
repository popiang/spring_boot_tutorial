package com.popiang.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository								 // standard crud
public interface VerificationDao extends CrudRepository<VerificationToken, Long> {

	VerificationToken findByToken(String token);
	
}
