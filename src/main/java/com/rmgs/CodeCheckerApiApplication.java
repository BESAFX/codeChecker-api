package com.rmgs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Rehab Sayed
 */
@SpringBootApplication
@ComponentScan({"com.rmgs"})
public class CodeCheckerApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodeCheckerApiApplication.class, args);
	}
	
	
}
