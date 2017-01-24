package com.theleapofcode.springboot.flight.fares;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.theleapofcode.springboot.flight.fares.entity.Fare;
import com.theleapofcode.springboot.flight.fares.repository.FaresRepository;

@EnableDiscoveryClient
@SpringBootApplication
public class BootifulFlightFaresApplication implements CommandLineRunner {
	private static final Logger logger = LoggerFactory.getLogger(BootifulFlightFaresApplication.class);

	@Autowired
	FaresRepository faresRepository;

	public static void main(String[] args) {
		SpringApplication.run(BootifulFlightFaresApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		// Add sample data to fares
		Fare[] fares = { new Fare("BF100", "20-JAN-17", "10000"), new Fare("BF101", "20-JAN-17", "5000"),
				new Fare("BF102", "20-JAN-17", "4000"), new Fare("BF103", "20-JAN-17", "15000"),
				new Fare("BF104", "20-JAN-17", "8000"), new Fare("BF105", "20-JAN-17", "6000"),
				new Fare("BF106", "20-JAN-17", "7000") };
		List<Fare> list = Arrays.stream(fares).collect(Collectors.toList());
		list.forEach(fare -> faresRepository.save(fare));

		logger.info("Result: " + faresRepository.getFareByFlightNumberAndFlightDate("BF101", "20-JAN-17"));
	}

}
