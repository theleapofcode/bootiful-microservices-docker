package com.theleapofcode.springboot.flight.book.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.theleapofcode.springboot.flight.book.component.BookingComponent;
import com.theleapofcode.springboot.flight.book.entity.BookingRecord;
import com.theleapofcode.springboot.flight.book.stats.TPMCounter;

@RestController
@CrossOrigin
@RequestMapping("/book")
public class BookingController {
	private BookingComponent bookingComponent;

	private TPMCounter createtpm;

	private TPMCounter gettpm;

	private GaugeService gaugeService;

	@Autowired
	BookingController(BookingComponent bookingComponent, GaugeService gaugeService) {
		this.bookingComponent = bookingComponent;
		this.gaugeService = gaugeService;
		this.createtpm = new TPMCounter();
		this.gettpm = new TPMCounter();
	}

	@RequestMapping("/get/{id}")
	BookingRecord getBooking(@PathVariable long id) {
		gettpm.increment();
		gaugeService.submit("gettpm", gettpm.count.intValue());
		return bookingComponent.getBooking(id);
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	long book(@RequestBody BookingRecord record) {
		createtpm.increment();
		gaugeService.submit("createtpm", createtpm.count.intValue());
		return bookingComponent.book(record);
	}

}
