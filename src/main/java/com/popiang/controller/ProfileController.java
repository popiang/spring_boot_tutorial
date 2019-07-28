package com.popiang.controller;

import javax.validation.Valid;

import org.owasp.html.PolicyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.popiang.model.Profile;
import com.popiang.model.SiteUser;
import com.popiang.service.ProfileService;
import com.popiang.service.UserService;

//
// controller handling profile activities
//
@Controller
public class ProfileController {

	@Autowired
	private UserService userService;

	@Autowired
	private ProfileService profileService;
	
	@Autowired
	private PolicyFactory policyFactory;
	
	@Value("${picture.upload.directory}")
	private String uploadPictureDirectory;
	
	//
	// private method to get current authenticated user
	//
	private SiteUser getUser() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		
		return userService.getUser(email);
	}

	//
	// showing profile page of current user
	//
	@RequestMapping("/profile")
	public ModelAndView showProfile(ModelAndView modelAndView) {
	
		SiteUser user = getUser();
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
		
		modelAndView.getModel().put("profile", webProfile);
		modelAndView.setViewName("app.profile");
		
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

		// security measure to sanitize html before updating current user profile
		profile.setMergeFrom(webProfile, policyFactory);

		// updating profile of current user is there's no error
		if(!result.hasErrors()) {
			profileService.saveProfile(profile);
			modelAndView.setViewName("redirect:/profile");
		}
		
		return modelAndView;
	}	
	
}
