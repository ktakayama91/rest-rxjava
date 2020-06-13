package com.example.rxjava.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.rxjava.application.config")
@EntityScan(basePackages = "com.example.rxjava.adapter.output.repository.entity")
@EnableJpaRepositories(basePackages = "com.example.rxjava.adapter.output.repository")
public class RxjavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(RxjavaApplication.class, args);
	}

}
