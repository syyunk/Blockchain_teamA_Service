package com.ateam.popserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PopServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PopServerApplication.class, args);
	}

}
