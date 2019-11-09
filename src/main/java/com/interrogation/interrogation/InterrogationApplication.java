package com.interrogation.interrogation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class InterrogationApplication {

	public static void main(String[] args) {
		SpringApplication.run(InterrogationApplication.class, args);
	}

}
