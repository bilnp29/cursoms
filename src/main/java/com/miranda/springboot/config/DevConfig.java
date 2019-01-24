package com.miranda.springboot.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.miranda.springboot.services.DBService;
import com.miranda.springboot.services.EmailService;
import com.miranda.springboot.services.SmtpEmailService;

@Configuration
@Profile("dev")
public class DevConfig {

	@Autowired
	private DBService dbService;

	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;

	@Bean
	public boolean instanciandoDataBase() throws ParseException {

		if (strategy.equals("create")) {
			dbService.instaciandoTestDataBase();
			return true;
		}
		return false;
	}
	
	@Bean
	public EmailService emailService(){
		return new SmtpEmailService();
	}
	
}
