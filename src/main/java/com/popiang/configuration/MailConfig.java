package com.popiang.configuration;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {

	//
	// data for java mail configuration is retrieved from validation.properties file
	//
	
	@Value("${mail.smtp.host}")
	private String host;
	
	@Value("${mail.smtp.port}")
	private int port;
	
	@Value("${mail.smtp.user}")
	private String username;
	
	@Value("${mail.smtp.password}")
	private String password;
	
	@Bean
	public JavaMailSender mailSender() {
		
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		
		Properties prop = mailSender.getJavaMailProperties();

		//
		// configuration for JavaMailSender
		//
		prop.put("mail.smtp.auth", true);
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.host", "smtp.mailtrap.io");
		prop.put("mail.smtp.port", "2525");
		prop.put("mail.smtp.ssl.trust", "smtp.mailtrap.io");
		
		//
		// authentication
		//
		Session session = Session.getInstance(prop, new Authenticator() {
		    @Override
		    protected PasswordAuthentication getPasswordAuthentication() {
		        return new PasswordAuthentication(username, password);
		    }
		});
		
		mailSender.setSession(session);
		
		return mailSender;
		
	}
	
}



