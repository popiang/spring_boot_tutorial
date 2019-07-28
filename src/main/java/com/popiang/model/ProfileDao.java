package com.popiang.model;

import org.springframework.data.repository.CrudRepository;

									// standard CRUD
public interface ProfileDao extends CrudRepository<Profile, Long> {
	Profile findByUser(SiteUser user);
}
