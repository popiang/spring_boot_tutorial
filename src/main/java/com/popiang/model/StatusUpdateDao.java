package com.popiang.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusUpdateDao extends CrudRepository<StatusUpdate, Long> {

	public StatusUpdate findFirstByOrderByAddedDesc();
	
}
