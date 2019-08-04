package com.popiang.controller;

import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import javax.validation.Valid;

import org.owasp.html.PolicyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.popiang.exceptions.ImageTooSmallException;
import com.popiang.exceptions.InvalidFileException;
import com.popiang.model.FileInfo;
import com.popiang.model.Interest;
import com.popiang.model.Profile;
import com.popiang.model.SiteUser;
import com.popiang.service.FileService;
import com.popiang.service.InterestService;
import com.popiang.service.ProfileService;
import com.popiang.service.UserService;
import com.popiang.status.PhotoUploadStatus;

//
// handling profile activities
//
@Controller
public class ProfileController {

	@Autowired
	private UserService userService;

	@Autowired
	private ProfileService profileService;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private PolicyFactory policyFactory;
	
	@Autowired
	private InterestService interestService;
	
	@Value("${picture.upload.directory}")
	private String uploadPictureDirectory;
	
	@Value("${photo.status.ok}")
	private String photoStatusOK;
	
	@Value("${photo.status.invalid}")
	private String photoStatusInvalid;
	
	@Value("${photo.status.ioexception}")
	private String photoStatusIOException;
	
	@Value("${photo.upload.toosmall}")
	private String photoStatusTooSmall;
	
	//
	// private method to get current authenticated user
	//
	private SiteUser getUser() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		
		return userService.getUser(email);
	}

	//
	// private method called in public showProfile method
	//
	private ModelAndView showProfile(SiteUser user) {
		
		ModelAndView modelAndView = new ModelAndView();
		
		if(user == null) {
			modelAndView.setViewName("redirect:/");
			return modelAndView;
		}
		
		Profile profile = profileService.getProfile(user);
		
		// for newly registered user which profile is not created yet
		if(profile == null) {
			profile = new Profile();
			profile.setUser(user);
			profileService.saveProfile(profile);
		}
		
		// security measure to avoid sensitive info leaked to public
		Profile webProfile = new Profile();
		webProfile.safeCopyFrom(profile);
		
		modelAndView.getModel().put("userID", user.getId());
		modelAndView.getModel().put("profile", webProfile);
		modelAndView.setViewName("app.profile");
		
		return modelAndView;
	}
	
	//
	// showing profile page of current user
	//
	@RequestMapping("/profile")
	public ModelAndView showProfile() {
	
		SiteUser user = getUser();

		ModelAndView modelAndView = showProfile(user);
		
		modelAndView.getModel().put("ownProfile", true);
		
		return modelAndView;
	}
	
	@RequestMapping("/profile/{id}")
	public ModelAndView showProfile(@PathVariable("id") Long id) {
		
		SiteUser user = userService.getUser(id);

		ModelAndView modelAndView = showProfile(user);
		
		modelAndView.getModel().put("ownProfile", false);
		
		return modelAndView;
	}
	
	//
	// showing edit profile page
	//
	@RequestMapping(value = "/edit-profile-about", method = RequestMethod.GET)
	public ModelAndView editProfileAbout(ModelAndView modelAndView) {

		SiteUser user = getUser();
		Profile profile = profileService.getProfile(user);

		Profile webProfile = new Profile();
		webProfile.safeCopyFrom(profile);

		modelAndView.getModel().put("profile", webProfile);
		modelAndView.setViewName("app.editProfileAbout");
		
		return modelAndView;
	}
	
	//
	// processing edit profile page
	//
	@RequestMapping(value = "/edit-profile-about", method = RequestMethod.POST)
	public ModelAndView editProfileAbout(ModelAndView modelAndView, @Valid Profile webProfile, BindingResult result) {

		modelAndView.setViewName("app.editProfileAbout");
		
		SiteUser user = getUser();
		Profile profile = profileService.getProfile(user);

		// security measure to sanitize HTML before updating current user profile
		profile.setMergeFrom(webProfile, policyFactory);

		// updating profile of current user is there's no error
		if(!result.hasErrors()) {
			profileService.saveProfile(profile);
			modelAndView.setViewName("redirect:/profile");
		}
		
		return modelAndView;
	}	
	
	//
	// handling photo uploads
	//
	@RequestMapping(value = "/upload-profile-photo", method = RequestMethod.POST)
	@ResponseBody // return JSON data
	public ResponseEntity<PhotoUploadStatus> handlePhotoUploads(ModelAndView modelAndView, @RequestParam("file") MultipartFile file) {
		
		// to capture upload status
		PhotoUploadStatus status = new PhotoUploadStatus(photoStatusOK);
		
		// get current authenticated user
		SiteUser user = getUser();
		
		// get the profile of the current authenticated user
		Profile profile = profileService.getProfile(user);
		
		// getting the existing photo path if available
		Path oldPhotoPath = profile.getPhotoPath(uploadPictureDirectory);
		
		try {
			
			// save the uploaded image and return several photo info
			FileInfo photoInfo = fileService.saveImgFile(file, uploadPictureDirectory, "photo", "p" + user.getId(), 100, 100);

			// save the photo info into the profile
			profile.saveFileInfo(photoInfo);
			
			// save@update the profile
			profileService.saveProfile(profile);
			
			// delete old photo if available
			if(oldPhotoPath != null) {
				Files.delete(oldPhotoPath);
			}
			
		} catch (InvalidFileException e) {				//
			status.setMessage(photoStatusInvalid);		//
			e.printStackTrace();						//
		} catch (IOException e) {						//
			status.setMessage(photoStatusIOException);	// handling errors in uploading photo
			e.printStackTrace();						//
		} catch (ImageTooSmallException e) {			//
			status.setMessage(photoStatusTooSmall);		//
			e.printStackTrace();						//
		}												//
		
		return new ResponseEntity<>(status, HttpStatus.OK) ;
	}			
	
	//
	// honestly, i don't really understand this method!!!
	// i think this method responsible to display the profile picture
	//
	@RequestMapping(value = "/profilephoto/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<InputStreamResource> servePhoto(@PathVariable("id") Long id) throws IOException {
		
		SiteUser user = userService.getUser(id);
		Profile profile = profileService.getProfile(user);
		
		Path photoPath = Paths.get(uploadPictureDirectory, "default", "default-profile.png");
		
		if(profile != null && profile.getPhotoPath(uploadPictureDirectory) != null) {
			photoPath = profile.getPhotoPath(uploadPictureDirectory);
		}
		
		return ResponseEntity
				.ok()
				.contentLength(Files.size(photoPath))
				.contentType(MediaType.parseMediaType(URLConnection.guessContentTypeFromName(photoPath.toString())))
				.body(new InputStreamResource(Files.newInputStream(photoPath, StandardOpenOption.READ)));
	}
	
	//
	// saving interest using REST
	//
	@RequestMapping(value = "/saveinterest", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> saveInterest(@RequestParam("name") String interestName) {
		
		SiteUser user = getUser();
		Profile profile = profileService.getProfile(user);
		
		String cleanedInterestName = policyFactory.sanitize(interestName);
		
		Interest interest = interestService.createOneIfNotExist(cleanedInterestName);
		
		profile.addInterest(interest);
		profileService.saveProfile(profile);
		
		return new ResponseEntity<>(null, HttpStatus.OK);
	}
	
	//
	// removing interest using REST
	//
	@RequestMapping(value = "/deleteinterest", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> deleteInterest(@RequestParam("name") String interestName) {
		
		SiteUser user = getUser();
		Profile profile = profileService.getProfile(user);

		profile.removeInterest(interestName);

		profileService.saveProfile(profile);
		
		return new ResponseEntity<>(null, HttpStatus.OK);
	}	
	
}
