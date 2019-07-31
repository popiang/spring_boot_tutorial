package com.popiang.test;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.popiang.App;
import com.popiang.model.SiteUser;
import com.popiang.service.InterestService;
import com.popiang.service.ProfileService;
import com.popiang.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {App.class})
@WebAppConfiguration
@Transactional
public class ProfileTest {

	@Autowired
	private UserService userService;
	
	@Autowired
	private ProfileService profileService;
	
	@Autowired
	private InterestService interestService;
	
	private SiteUser[] users = {
			new SiteUser("testuser1@gmail.com", "hello"),
			new SiteUser("testuser2@gmail.com", "hello"),
			new SiteUser("testuser3@gmail.com", "hello")
	};
	
	private String[][] interests = {
			{"music", "gaming", "movie"},
			{"gaming", "drawing", "cooking"},
			{"cooking", "music", "driving"}
	};
	
	@Test
	public void testInterest() {
		
	}
	
}
