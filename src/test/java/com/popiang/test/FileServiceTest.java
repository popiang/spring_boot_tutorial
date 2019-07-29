package com.popiang.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.lang.reflect.Method;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.popiang.App;
import com.popiang.service.FileService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {App.class})
@WebAppConfiguration
@Transactional
public class FileServiceTest {

	@Autowired
	private FileService fileService;
	
	@Value("${picture.upload.directory}")
	private String uploadPictureDirectory;
	
	@Test
	public void testGetExtension() throws Exception {
		
		Method method = FileService.class.getDeclaredMethod("getFileExtension", String.class);
		method.setAccessible(true);

		assertEquals("should be png", "png", (String)method.invoke(fileService, "test.png"));
		assertEquals("should be jpg", "jpg", (String)method.invoke(fileService, "test.jpg"));
		assertEquals("should be jpeg", "jpeg", (String)method.invoke(fileService, "test.jpeg"));
		assertEquals("should be gif", "gif", (String)method.invoke(fileService, "test.gif"));
		assertNull("should be png", (String)method.invoke(fileService, "test"));
		
	}
	
	@Test
	public void testIsImageExtension() throws Exception {
		
		Method method = FileService.class.getDeclaredMethod("isImageExtension", String.class);
		method.setAccessible(true);

		assertTrue("should be png", (Boolean)method.invoke(fileService, "png"));
		assertTrue("should be png", (Boolean)method.invoke(fileService, "PNG"));
		assertTrue("should be png", (Boolean)method.invoke(fileService, "jpg"));
		assertTrue("should be png", (Boolean)method.invoke(fileService, "JPG"));
		assertTrue("should be png", (Boolean)method.invoke(fileService, "gif"));
		assertTrue("should be png", (Boolean)method.invoke(fileService, "GIF"));
		assertTrue("should be png", (Boolean)method.invoke(fileService, "JPEG"));
		assertTrue("should be png", (Boolean)method.invoke(fileService, "jpeg"));
		assertFalse("should be png", (Boolean)method.invoke(fileService, "doc"));
		assertFalse("should be png", (Boolean)method.invoke(fileService, "gi"));
		
	}
	
	@Test
	public void testCreateDirectory() throws Exception {
		
		Method method = FileService.class.getDeclaredMethod("makeSubdirectory", String.class, String.class);
		method.setAccessible(true);
		
		for(int i = 0; i < 10000; ++i) {
			
			File created = (File)method.invoke(fileService, uploadPictureDirectory, "photo");
			
			assertTrue("Directory should exist: " + created.getAbsolutePath(), created.exists());
			
		}

	}
	
}




