package com.popiang.test;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
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
public class BulkTests {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProfileService profileService;
	
	@Autowired
	private InterestService interestService;

	private static final String namesFile = "/com/popiang/test/data/names.txt";
	private static final String interestsFile = "/com/popiang/test/data/hobbies.txt";
	
	private List<String> loadFile(String filename, int maxlength) throws IOException {
		
		Path filePath = new ClassPathResource(filename).getFile().toPath();

		Stream<String> stream = Files.lines(filePath);
		
		List<String> items = stream
			.filter(line -> !line.isEmpty())
			.map(line -> line.trim())
			.filter(line -> line.length() <= 25)
			.map(line -> line.substring(0, 1).toUpperCase() + line.substring(1).toLowerCase())
			.collect(Collectors.toList());
		
		stream.close();
		
		return items;
	}
	
	@Test
	public void createTestData() throws IOException {
		
		Random random = new Random();
		
		List<String> interests = loadFile(interestsFile, 25);
		List<String> names = loadFile(namesFile, 25);
		
		for(int numUsers = 0; numUsers < 200; ++numUsers) {
			
			String firstName = names.get(random.nextInt(names.size()));
			String lastName = names.get(random.nextInt(names.size()));
			
			String email = firstName.toLowerCase() + lastName.toLowerCase() + "@example.com";
			
			if(userService.getUser(email) != null) {
				continue;
			}
			
			String password = "pass" + firstName.toLowerCase();
			password = password.substring(0, Math.min(15, password.length()));
			
			assertTrue(password.length() <= 15);
			
			SiteUser user = new SiteUser(email, password, firstName, lastName);
			user.setEnabled(random.nextInt(5) != 0);
			
			userService.register(user);
			
			Profile profile = new Profile(user);
			
			int numberInterests = random.nextInt(7);
			
			Set<Interest> userInterests = new HashSet<Interest>();
			
			for(int i = 0; i < numberInterests; ++i) {
				
				String interestText = interests.get(random.nextInt(interests.size()));
				
				Interest interest = interestService.createOneIfNotExist(interestText);
				
				userInterests.add(interest);
				
			}
			
			profile.setInterests(userInterests);
			profileService.saveProfile(profile);
			
		}
		
		assertTrue(true);
		
	}
	
}
