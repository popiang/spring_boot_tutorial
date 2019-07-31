package com.popiang.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.popiang.model.Interest;
import com.popiang.model.InterestDao;

@Service
public class InterestService {

	@Autowired
	private InterestDao interestDao;
	
	public Interest getInterest(String interestName) {
		return interestDao.findOneByName(interestName);
	}
	
	public void saveInterest(Interest interest) {
		interestDao.save(interest);
	}
	
	public long count() {
		return interestDao.count();
	}
	
	public Interest createOneIfNotExist(String interestText) {
		
		Interest interest = interestDao.findOneByName(interestText);
		
		if(interest == null) {
			interest = new Interest(interestText);
			interestDao.save(interest);
		}
		
		return interest;
	}
	
}
