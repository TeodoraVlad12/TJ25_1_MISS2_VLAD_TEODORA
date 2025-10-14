package com.lab2.Lab2_TH;

import com.lab2.Lab2_TH.properties.DatabaseProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(DatabaseProperties.class)
public class Lab2ThApplication {

	public static void main(String[] args) {
		SpringApplication.run(Lab2ThApplication.class, args);
	}

}
