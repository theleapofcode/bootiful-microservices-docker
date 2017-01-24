package com.theleapofcode.springboot.flight.book.component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.stereotype.Component;

import com.theleapofcode.springboot.flight.book.entity.BookingRecord;
import com.theleapofcode.springboot.flight.book.entity.Inventory;
import com.theleapofcode.springboot.flight.book.entity.Passenger;
import com.theleapofcode.springboot.flight.book.exception.BookingException;
import com.theleapofcode.springboot.flight.book.messaging.Sender;
import com.theleapofcode.springboot.flight.book.repository.BookingRepository;
import com.theleapofcode.springboot.flight.book.repository.InventoryRepository;

@EnableFeignClients
@RefreshScope
@Component
public class BookingComponent {
	private static final Logger logger = LoggerFactory.getLogger(BookingComponent.class);

	private BookingRepository bookingRepository;
	private InventoryRepository inventoryRepository;
	private FaresService faresService;

	private Sender sender;

	@Autowired
	public BookingComponent(BookingRepository bookingRepository, Sender sender, InventoryRepository inventoryRepository,
			FaresService faresService) {
		this.bookingRepository = bookingRepository;
		this.sender = sender;
		this.inventoryRepository = inventoryRepository;
		this.faresService = faresService;
	}

	public long book(BookingRecord record) {
		logger.info("calling fares to get fare");
		// call fares to get fare
		Fare fare = faresService.getFare(record.getFlightNumber(), record.getFlightDate());
		logger.info("Fare received " + fare);
		// check fare
		if (!record.getFare().equals(fare.getFare())) {
			throw new BookingException("fare is tampered");
		}

		logger.info("calling inventory to get inventory");
		// check inventory
		Inventory inventory = inventoryRepository.findByFlightNumberAndFlightDate(record.getFlightNumber(),
				record.getFlightDate());
		if (!inventory.isAvailable(record.getPassengers().size())) {
			throw new BookingException("No more seats avaialble");
		}
		logger.info("successfully checked inventory" + inventory);
		logger.info("calling inventory to update inventory");
		// update inventory
		inventory.setAvailable(inventory.getAvailable() - record.getPassengers().size());
		inventoryRepository.saveAndFlush(inventory);
		logger.info("sucessfully updated inventory");

		// save booking
		record.setStatus(BookingStatus.BOOKING_CONFIRMED);
		Set<Passenger> passengers = record.getPassengers();
		passengers.forEach(passenger -> passenger.setBookingRecord(record));
		record.setBookingDate(new Date());
		long id = bookingRepository.save(record).getId();
		logger.info("Successfully saved booking");

		// send a message to search microservice to update inventory
		logger.info("sending a booking event");
		Map<String, Object> bookingDetails = new HashMap<String, Object>();
		bookingDetails.put("FLIGHT_NUMBER", record.getFlightNumber());
		bookingDetails.put("FLIGHT_DATE", record.getFlightDate());
		bookingDetails.put("NEW_INVENTORY", inventory.getBookableInventory());
		sender.send(bookingDetails);
		logger.info("booking event successfully delivered " + bookingDetails);
		return id;
	}

	public BookingRecord getBooking(long id) {
		return bookingRepository.findOne(id);
	}

	public void updateStatus(String status, long bookingId) {
		BookingRecord record = bookingRepository.findOne(bookingId);
		record.setStatus(status);
	}

}
