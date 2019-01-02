package com.miranda.springboot.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.miranda.springboot.services.DBService;

@Configuration
@Profile("test")
public class TestConfig {

	@Autowired
	private DBService dbService; 
	
	@Bean
	public boolean instanciandoDataBase() throws ParseException {
		dbService.instaciandoTestDataBase();
		return true;
	}
}
