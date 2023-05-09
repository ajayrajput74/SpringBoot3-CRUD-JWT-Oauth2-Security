package com.restful.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class OauthProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(OauthProjectApplication.class, args);
		
		System.out.println("password "+new BCryptPasswordEncoder().encode("Ajay@123"));
	}

}
