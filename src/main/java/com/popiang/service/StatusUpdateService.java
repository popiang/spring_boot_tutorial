package com.popiang.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.popiang.model.StatusUpdate;
import com.popiang.model.StatusUpdateDao;

@Service
public class StatusUpdateService {

	private final static int PAGESIZE = 3;
	
	@Autowired
	private StatusUpdateDao dao;
	
	public void save(StatusUpdate status) {
		dao.save(status);
	}
	
	public StatusUpdate getLatest() {
		return dao.findFirstByOrderByAddedDesc();
	}
	
	public Page<StatusUpdate> getPage(int pageNumber) {
		
		PageRequest request = PageRequest.of(pageNumber - 1, PAGESIZE, Sort.Direction.DESC, "added");
		
		return dao.findAll(request);
		
	}
}
