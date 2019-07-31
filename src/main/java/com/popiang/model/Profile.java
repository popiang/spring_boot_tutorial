package com.popiang.model;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
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

	@Column(name = "photo_name", length = 10)
	private String photoName;

	@Column(name = "photo_extension", length = 5)
	private String photoExtension;

	@Column(name = "photo_directory", length = 10)
	private String photoDirectory;
	
	@ManyToMany
	@JoinTable(name = "profile_interests", 
	joinColumns = { @JoinColumn(name="profile_id") }, 
	inverseJoinColumns = { @JoinColumn(name="interes_id") })
	@OrderColumn(name = "display_order")
	private Set<Interest> interests;
	
	public Profile() {
		
	}
	
	public Profile(SiteUser user) {
		this.user = user;
	}

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

	public String getPhotoName() {
		return photoName;
	}

	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}

	public String getPhotoExtension() {
		return photoExtension;
	}

	public void setPhotoExtension(String photoExtension) {
		this.photoExtension = photoExtension;
	}

	public String getPhotoDirectory() {
		return photoDirectory;
	}

	public void setPhotoDirectory(String photoDirectory) {
		this.photoDirectory = photoDirectory;
	}
	
	public Set<Interest> getInterests() {
		return interests;
	}

	public void setInterests(Set<Interest> interests) {
		this.interests = interests;
	}

	//
	// getting a copy of an about from a profile 
	// security measure to avoid sensitive user info leaked to public
	//
	public void safeCopyFrom(Profile other) {
		if (other.about != null) {
			this.about = other.about;
		}
		
		if(other.interests != null) {
			this.interests = other.interests;
		}
	}

	//
	// updating profile after sanitize the HTML using policyFactory
	//
	public void setMergeFrom(Profile webProfile, PolicyFactory policyFactory) {
		if (webProfile.about != null) {
			this.about = policyFactory.sanitize(webProfile.about);
		}
	}
	
	//
	// setting photo info into respective variables
	//
	public void saveFileInfo(FileInfo fileInfo) {
		this.photoName = fileInfo.getBasename();
		this.photoExtension = fileInfo.getExtension();
		this.photoDirectory = fileInfo.getSubDirectory();
	}

	//
	// returning the full path of photo
	//
	public Path getPhotoPath(String baseDirectory) {
		
		if(photoName == null) {
			return null;
		}
		
		return Paths.get(baseDirectory, photoDirectory, photoName + "." + photoExtension);
	}
}
