package com.popiang.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.popiang.validation.PasswordMatch;

@Entity
@Table(name = "users")
@PasswordMatch(message = "{register.repeatpassword.mismatch}")
public class SiteUser {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private long id;
	
	@Column(name = "email", unique = true)
	@Email(message = "{register.email.invalid}")
	@NotBlank(message = "{register.email.invalid}")
	private String email;
	
	@Column(name = "enabled")
	private Boolean enabled = false;
	
	@Column(name = "password")
	private String password;
	
	@Transient
	@Size(min = 5, max = 15, message = "{register.password.size}")
	private String plainPassword;
	
	@Transient
	private String repeatPassword;
	
	@Column(name = "role")
	private String role;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPlainPassword() {
		return plainPassword;
	}

	public void setPlainPassword(String plainPassword) {
		this.password = plainPassword;
		this.plainPassword = plainPassword;
	}

	public String getRepeatPassword() {
		return repeatPassword;
	}

	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
}
