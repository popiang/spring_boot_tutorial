package com.popiang.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

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
public class ProfileControllerRestTest {

	@Autowired
	private UserService userService;
	
	@Autowired
	private ProfileService profileService;
	
	@Autowired
	private InterestService interestService;
	
	@Autowired
	private WebApplicationContext webAppContext;
	
	private MockMvc mockMvc;
	
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
	}
	
	@Test
	@WithMockUser(username = "popiang@hotmail.com")
	public void testSaveAndDeleteInterest() throws Exception {
		
		String interestText = "sample interest";
		
		mockMvc.perform(post("/saveinterest").param("name", interestText)).andExpect(status().isOk());
		
		Interest interest = interestService.getInterest(interestText);
		
		assertNotNull("Interest should exist", interest);
		assertEquals("Retreived interest test should match", interestText, interest.getName());
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		
		SiteUser user = userService.getUser(email);
		Profile profile = profileService.getProfile(user);
		
		assertTrue("Profile should contain interest", profile.getInterests().contains(interest));
		
		mockMvc.perform(post("/deleteinterest").param("name", interestText)).andExpect(status().isOk());
		
		assertFalse("Profile should not contain interest", profile.getInterests().contains(interest));
		
	}
}
