package com.theleapofcode.springboot.flight.book;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.theleapofcode.springboot.flight.book.component.BookingComponent;
import com.theleapofcode.springboot.flight.book.entity.BookingRecord;
import com.theleapofcode.springboot.flight.book.entity.Inventory;
import com.theleapofcode.springboot.flight.book.entity.Passenger;
import com.theleapofcode.springboot.flight.book.repository.InventoryRepository;

@EnableCircuitBreaker
@EnableDiscoveryClient
@SpringBootApplication
public class BootifulFlightBookApplication implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(BootifulFlightBookApplication.class);

	@Autowired
	private BookingComponent bookingComponent;

	@Autowired
	InventoryRepository inventoryRepository;

	public static void main(String[] args) {
		SpringApplication.run(BootifulFlightBookApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		// Add sample data to inventory
		Inventory[] invs = { new Inventory("BF100", "20-JAN-17", 100), new Inventory("BF101", "20-JAN-17", 100),
				new Inventory("BF102", "20-JAN-17", 100), new Inventory("BF103", "20-JAN-17", 100),
				new Inventory("BF104", "20-JAN-17", 100), new Inventory("BF105", "20-JAN-17", 100),
				new Inventory("BF106", "20-JAN-17", 100) };
		Arrays.asList(invs).forEach(inventory -> inventoryRepository.save(inventory));

		// Add sample data to booking
		BookingRecord booking = new BookingRecord("BF101", "BLR", "CHE", "20-JAN-17", new Date(), "5000");
		Set<Passenger> passengers = new HashSet<Passenger>();
		passengers.add(new Passenger("Tony", "Stark", "Male", booking));

		booking.setPassengers(passengers);
		long record = bookingComponent.book(booking);
		logger.info("Booking successfully saved..." + record);

		logger.info("Looking to load booking record...");
		logger.info("Result: " + bookingComponent.getBooking(record));

	}

}
