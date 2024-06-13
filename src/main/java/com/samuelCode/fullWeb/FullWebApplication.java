package com.samuelCode.fullWeb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.samuelCode.dependency.exception"})
public class FullWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(FullWebApplication.class, args);
	}

}
