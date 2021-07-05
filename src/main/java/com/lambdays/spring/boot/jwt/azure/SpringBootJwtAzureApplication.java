package com.lambdays.spring.boot.jwt.azure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@SpringBootApplication
public class SpringBootJwtAzureApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootJwtAzureApplication.class, args);
	}

}
