package com.Lab3.IC.Lab3.IC;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	// Run our demonstration when the app starts
	@Bean
	CommandLineRunner run(SimpleInjectionDemo demoComponent) {
		return args -> demoComponent.init();
	}
}
