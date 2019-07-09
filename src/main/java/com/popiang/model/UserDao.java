package com.popiang.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository						 // standard crud
public interface UserDao extends CrudRepository<SiteUser, Long> {

	SiteUser findByEmail(String email);
	
}
