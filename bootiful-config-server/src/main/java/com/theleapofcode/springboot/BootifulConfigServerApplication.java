package com.theleapofcode.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class BootifulConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootifulConfigServerApplication.class, args);
	}
}
