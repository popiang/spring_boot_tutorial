package com.popiang.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.popiang.model.StatusUpdate;
import com.popiang.model.StatusUpdateDao;

@Service
public class StatusUpdateService {

	@Autowired
	private StatusUpdateDao dao;
	
	public void save(StatusUpdate status) {
		dao.save(status);
	}
	
	public StatusUpdate getLatest() {
		return dao.findFirstByOrderByAddedDesc();
	}
}
