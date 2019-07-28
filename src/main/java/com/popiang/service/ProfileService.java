package com.popiang.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.popiang.model.Profile;
import com.popiang.model.ProfileDao;
import com.popiang.model.SiteUser;

@Service
public class ProfileService {

	@Autowired
	private ProfileDao profileDao;
	
	public void saveProfile(Profile profile) {
		profileDao.save(profile);
	}
	
	public Profile getProfile(SiteUser user) {
		return profileDao.findByUser(user);
	}
}
