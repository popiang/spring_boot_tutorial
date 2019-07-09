package com.popiang.model;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository								 // to manage paging and sorting of status
public interface StatusUpdateDao extends PagingAndSortingRepository<StatusUpdate, Long> {

	public StatusUpdate findFirstByOrderByAddedDesc();
	
}
