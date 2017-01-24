package com.theleapofcode.springboot.flight.book.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class FaresService {

	private FaresServiceProxy faresServiceProxy;

	@Autowired
	public FaresService(FaresServiceProxy faresServiceProxy) {
		this.faresServiceProxy = faresServiceProxy;
	}

	@HystrixCommand(fallbackMethod = "getMinimumFare")
	public Fare getFare(String flightNumber, String flightDate) {
		return faresServiceProxy.getFare(flightNumber, flightDate);
	}

	public Fare getMinimumFare(String flightNumber, String flightDate) {
		return new Fare(flightNumber, flightDate, "1000");
	}

}
