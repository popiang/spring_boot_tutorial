package com.popiang.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.owasp.html.PolicyFactory;

@Entity
@Table(name = "profile")
public class Profile {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	@OneToOne(targetEntity = SiteUser.class)
	@JoinColumn(name = "user_id", nullable = false)
	private SiteUser user;
	
	@Column(name = "about", length = 5000)
	@Size(max = 5000, message = "{profile.about.size}")
	private String about;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SiteUser getUser() {
		return user;
	}

	public void setUser(SiteUser user) {
		this.user = user;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}
	
	//
	// security measure to avoid sensitive user info leaked to public
	//
	public void safeCopyFrom(Profile other) {
		if(other.about != null) {
			this.about = other.about;
		}
	}

	//
	// updating profile after sanitize the html using policyFactory
	//
	public void setMergeFrom(Profile webProfile, PolicyFactory policyFactory) {
		if(webProfile.about != null) {
			this.about = policyFactory.sanitize(webProfile.about);
		}
	}
}
