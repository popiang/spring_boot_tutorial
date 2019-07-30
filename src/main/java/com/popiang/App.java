package com.popiang;

import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;

@EnableAsync
@SpringBootApplication
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
	
	//
	// required for using TILES
	//
	@Bean
	public TilesConfigurer tilesConfigurer() {
		
		TilesConfigurer tilesConfigurer = new TilesConfigurer();
		String [] defs = {"/WEB-INF/tiles.xml"};
		tilesConfigurer.setDefinitions(defs);
		
		return tilesConfigurer;
	}

	//
	// required for using TILES
	//	
	@Bean
	public UrlBasedViewResolver tilesViewResolver() {
		
		UrlBasedViewResolver tilesViewResolver = new UrlBasedViewResolver();
		tilesViewResolver.setViewClass(TilesView.class);

		return tilesViewResolver;
	}
	
	//
	// bean for password encoder used in userservice
	//
	@Bean
	public PasswordEncoder getEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	//
	// bean to handle forbidden access error
	//
	@Bean
	public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> errorHandler() {
		
		return new WebServerFactoryCustomizer<ConfigurableServletWebServerFactory>() {

			@Override
			public void customize(ConfigurableServletWebServerFactory factory) {
			
				factory.addErrorPages(new ErrorPage(HttpStatus.FORBIDDEN, "/403"));
				
			}
			
		};
		
	}
	
	//
	// bean to set html policy, setting what html elements are allowed
	// used in textarea in profile about
	// other then the allowed belows will be filtered out
	//
	@Bean
	public PolicyFactory getUserHtmlPolicy() {
		return new HtmlPolicyBuilder()
				.allowCommonBlockElements()				// html common blocks
				.allowCommonInlineFormattingElements()	// html common inline formatting elements
				.toFactory();
	}
	
}


