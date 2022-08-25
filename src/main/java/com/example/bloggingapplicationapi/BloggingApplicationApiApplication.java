package com.example.bloggingapplicationapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BloggingApplicationApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BloggingApplicationApiApplication.class, args);
	}

}
