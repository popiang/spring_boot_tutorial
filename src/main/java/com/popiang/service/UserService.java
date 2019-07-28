package com.popiang.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.popiang.model.SiteUser;
import com.popiang.model.TokenType;
import com.popiang.model.UserDao;
import com.popiang.model.VerificationDao;
import com.popiang.model.VerificationToken;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private VerificationDao verificationDao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public void register(SiteUser user) {
		user.setRole("ROLE_USER");
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userDao.save(user);
	}
	
	public void save(SiteUser user) {
		userDao.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		SiteUser user = userDao.findByEmail(email);
		
		if(user == null) {
			return null;
		}
		
		List<GrantedAuthority> auth = AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRole());
		
		String password = user.getPassword();
		
		Boolean enabled = user.getEnabled();
		
		return new User(email, password, enabled, true, true, true, auth);
		
	}
	
	public String createEmailVerificationToken(SiteUser user) {

		VerificationToken token = new VerificationToken(UUID.randomUUID().toString(), user, TokenType.REGISTER);
		verificationDao.save(token);
		return token.getToken();
		
	}
	
	public VerificationToken getVerificationToken(String token) {
		return verificationDao.findByToken(token);
	}

	public void deleteToken(VerificationToken token) {
		verificationDao.delete(token);
	}

	public SiteUser getUser(String email) {
		return userDao.findByEmail(email);
	}
	
}




