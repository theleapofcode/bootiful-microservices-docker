package com.theleapofcode.springboot.flight.site;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import com.theleapofcode.springboot.flight.site.controller.BookingServiceProxy;
import com.theleapofcode.springboot.flight.site.controller.CheckinServiceProxy;
import com.theleapofcode.springboot.flight.site.controller.SearchServiceProxy;
import com.theleapofcode.springboot.flight.site.model.BookingRecord;
import com.theleapofcode.springboot.flight.site.model.CheckInRecord;
import com.theleapofcode.springboot.flight.site.model.Flight;
import com.theleapofcode.springboot.flight.site.model.Passenger;
import com.theleapofcode.springboot.flight.site.model.SearchQuery;

@EnableDiscoveryClient
@RefreshScope
@EnableGlobalMethodSecurity
@SpringBootApplication
public class BootifulFlightSiteApplication implements CommandLineRunner {
	private static final Logger logger = LoggerFactory.getLogger(BootifulFlightSiteApplication.class);

	@Autowired
	private SearchServiceProxy searchServiceProxy;

	@Autowired
	private BookingServiceProxy bookingServiceProxy;

	@Autowired
	private CheckinServiceProxy checkinServiceProxy;

	public static void main(String[] args) {
		SpringApplication.run(BootifulFlightSiteApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		// Search for a flight
		SearchQuery searchQuery = new SearchQuery("BLR", "CHE", "20-JAN-17");
		List<Flight> flights = searchServiceProxy.search(searchQuery);

		flights.forEach(flight -> logger.info(" flight >" + flight));

		// create a booking only if there are flights.
		if (flights == null || flights.size() == 0) {
			return;
		}
		Flight flight = flights.get(0);
		BookingRecord booking = new BookingRecord(flight.getFlightNumber(), flight.getOrigin(), flight.getDestination(),
				flight.getFlightDate(), null, flight.getFares().getFare());
		Set<Passenger> passengers = new HashSet<Passenger>();
		passengers.add(new Passenger("Tony", "Stark", "Male", booking));
		booking.setPassengers(passengers);
		long bookingId = 0;
		try {
			bookingId = bookingServiceProxy.book(booking);
			logger.info("Booking created " + bookingId);
		} catch (Exception e) {
			logger.error("BOOKING SERVICE NOT AVAILABLE...!!!");
		}

		// check in passenger
		if (bookingId == 0)
			return;
		try {
			CheckInRecord checkIn = new CheckInRecord("Tony", "Stark", "28C", null, "BF101", "20-JAN-17", bookingId);
			long checkinId = checkinServiceProxy.checkin(checkIn);
			logger.info("Checked IN " + checkinId);
		} catch (Exception e) {
			logger.error("CHECK IN SERVICE NOT AVAILABLE...!!!");
		}
	}

}