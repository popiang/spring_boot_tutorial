package com.popiang.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashSet;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.popiang.App;
import com.popiang.model.Interest;
import com.popiang.model.Profile;
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
			new SiteUser("testuser1@gmail.com", "hello", "ali", "ahmad"),
			new SiteUser("testuser2@gmail.com", "hello", "siti", "ramli"),
			new SiteUser("testuser3@gmail.com", "hello", "rosli", "deraman")
	};
	
	private String[][] interests = {
			{"music", "gaming", "movie"},
			{"gaming", "drawing", "cooking"},
			{"cooking", "music", "driving"}
	};
	
	@Test
	public void testInterest() {
		
		for(int i = 0; i < users.length; ++i) {
			
			SiteUser user = users[i];
			String[] interestArray = interests[i];
			
			userService.register(user);
			
			HashSet<Interest> interestSet = new HashSet<>();
			
			for(String interestText : interestArray) {

				Interest interest = interestService.createOneIfNotExist(interestText);
				interestSet.add(interest);

				assertNotNull("Interest should not be null", interest);
				assertNotNull("Interest should have ID", interest.getId());
				assertEquals("Interest text should match", interestText, interest.getName());
				
			}
			
			Profile profile = new Profile(user);
			profile.setInterests(interestSet);
			profileService.saveProfile(profile);
			
			Profile retreivedProfile = profileService.getProfile(user);
			
			assertEquals("Interest sets should match", interestSet, retreivedProfile.getInterests()); 
			
		}
	}
}
