package com.popiang.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Calendar;

import javax.transaction.Transactional;

import org.junit.Assert;
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
	public void testSave() {
		
		StatusUpdate status = new StatusUpdate("This is a test status update");
		System.out.println(status);
		statusUpdateDao.save(status);

		assertNotNull("Not null ID", status.getId());
		assertNotNull("Not null added date", status.getAdded());
		
		StatusUpdate retrieved = statusUpdateDao.findById(status.getId()).orElse(null);
		
		assertEquals("Status update should be equal", retrieved, status);
		
	}
	
	@Test
	public void testFindLatest() {
		
		StatusUpdate lastStatusUpdate = null;
		
		Calendar calendar = Calendar.getInstance();
		
		for(int i = 0; i < 10; ++i) {

			calendar.add(Calendar.DAY_OF_YEAR, 1);
			
			StatusUpdate status = new StatusUpdate("Status update " + i, calendar.getTime());
			
			statusUpdateDao.save(status);
			
			lastStatusUpdate = status;
			
		}
		
		StatusUpdate retrieved = statusUpdateDao.findFirstByOrderByAddedDesc();
		
		Assert.assertArrayEquals("Latest status update", new StatusUpdate[] {retrieved}, new StatusUpdate[] {lastStatusUpdate});
		
	}
	
}
