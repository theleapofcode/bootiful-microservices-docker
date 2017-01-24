package com.theleapofcode.springboot.flight.site.controller;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.theleapofcode.springboot.flight.site.model.BookingRecord;

@FeignClient(name = "api-gateway")
public interface BookingServiceProxy {
	@RequestMapping(value = "bookapi/book/create", method = RequestMethod.POST)
	long book(@RequestBody BookingRecord bookingRecord);

	@RequestMapping(value = "bookapi/book/get/{id}", method = RequestMethod.GET)
	BookingRecord getBooking(@PathVariable("id") long id);
}
