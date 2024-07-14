package com.team2final.minglecrm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@EnableJpaAuditing
@SpringBootApplication
@EnableMethodSecurity
public class MingleApplication {
	public static void main(String[] args) {
		try {
			SpringApplication.run(MingleApplication.class, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}