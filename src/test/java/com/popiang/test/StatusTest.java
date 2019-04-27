package com.popiang.test;

import javax.transaction.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.popiang.App;
import com.popiang.model.StatusUpdate;
import com.popiang.model.StatusUpdateDao;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {App.class})
@WebAppConfiguration
@Transactional
public class StatusTest {

	@Autowired
	private StatusUpdateDao statusUpdateDao;
	
	@Test
	public void dummyTest() {
		
		StatusUpdate status = new StatusUpdate("This is a test status update");
		
		statusUpdateDao.save(status);

//		assertNotNull("Not null ID", status.getId());
//		assertNotNull("Not null added date", status.getAdded());
//		
//		StatusUpdate retrieved = statusUpdateDao.findById(status.getId()).orElse(null);
//		
//		assertEquals("Status update should be equal", retrieved, status);
		
	}
	
}
