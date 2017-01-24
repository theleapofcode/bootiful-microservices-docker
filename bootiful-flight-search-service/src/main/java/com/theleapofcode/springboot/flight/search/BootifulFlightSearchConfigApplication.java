package com.theleapofcode.springboot.flight.search;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.theleapofcode.springboot.flight.search.entity.Fares;
import com.theleapofcode.springboot.flight.search.entity.Flight;
import com.theleapofcode.springboot.flight.search.entity.Inventory;
import com.theleapofcode.springboot.flight.search.repository.FlightRepository;

@EnableDiscoveryClient
@SpringBootApplication
public class BootifulFlightSearchConfigApplication implements CommandLineRunner {
	private static final Logger logger = LoggerFactory.getLogger(BootifulFlightSearchConfigApplication.class);

	@Autowired
	private FlightRepository flightRepository;

	public static void main(String[] args) {
		SpringApplication.run(BootifulFlightSearchConfigApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		// Add sample data to flights
		List<Flight> flights = new ArrayList<>();
		flights.add(new Flight("BF100", "BLR", "DEL", "20-JAN-17", new Fares("10000", "INR"), new Inventory(100)));
		flights.add(new Flight("BF101", "BLR", "CHE", "20-JAN-17", new Fares("5000", "INR"), new Inventory(100)));
		flights.add(new Flight("BF102", "HYD", "BLR", "20-JAN-17", new Fares("4000", "INR"), new Inventory(100)));
		flights.add(new Flight("BF103", "DEL", "CHE", "20-JAN-17", new Fares("15000", "INR"), new Inventory(100)));
		flights.add(new Flight("BF104", "MUM", "CHE", "20-JAN-17", new Fares("8000", "INR"), new Inventory(100)));
		flights.add(new Flight("BF105", "MUM", "BLR", "20-JAN-17", new Fares("6000", "INR"), new Inventory(100)));
		flights.add(new Flight("BF106", "KOL", "MUM", "20-JAN-17", new Fares("7000", "INR"), new Inventory(100)));

		flightRepository.save(flights);

		logger.info("Looking to load flights...");
		for (Flight flight : flightRepository.findByOriginAndDestinationAndFlightDate("BLR", "CHE", "20-JAN-17")) {
			logger.info(flight.toString());
		}
	}
}
