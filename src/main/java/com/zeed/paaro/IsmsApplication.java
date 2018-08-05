package com.zeed.paaro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@EnableResourceServer
@EnableScheduling
@SpringBootApplication
@ComponentScan(basePackages = {"com.zeed.paaro","com.zeed.generic","com.zeed.usermanagement.request","com.zeed.usermanagement.models"})
public class IsmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(IsmsApplication.class, args);
	}
}
