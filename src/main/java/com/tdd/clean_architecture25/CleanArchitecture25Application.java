package com.tdd.clean_architecture25;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.TimeZone;

@SpringBootApplication
@RestController
public class CleanArchitecture25Application {

	@PostConstruct
	public void init() {
		//Lock timezone to Asia/Jakarta for consistent audit logs
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Jakarta"));
	}

	public static void main(String[] args) {
		SpringApplication.run(CleanArchitecture25Application.class, args);
	}

	@GetMapping("/")
	public String parent() {
		return "Hello Everyone V.0.1";
	}

}
