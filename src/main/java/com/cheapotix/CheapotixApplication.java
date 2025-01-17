package com.cheapotix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CheapotixApplication {

	public static void main(String[] args) {
		SpringApplication.run(CheapotixApplication.class, args);
	}

}
