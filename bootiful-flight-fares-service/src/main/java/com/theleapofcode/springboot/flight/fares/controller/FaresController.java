package com.theleapofcode.springboot.flight.fares.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.theleapofcode.springboot.flight.fares.component.FaresComponent;
import com.theleapofcode.springboot.flight.fares.entity.Fare;
import com.theleapofcode.springboot.flight.fares.stats.TPMCounter;

@RestController
@CrossOrigin
@RequestMapping("/fares")
public class FaresController {
	private FaresComponent faresComponent;

	private TPMCounter tpm;

	private GaugeService gaugeService;

	@Autowired
	FaresController(FaresComponent faresComponent, GaugeService gaugeService) {
		this.faresComponent = faresComponent;
		this.gaugeService = gaugeService;
		this.tpm = new TPMCounter();
	}

	@RequestMapping("/get")
	Fare getFare(@RequestParam(value = "flightNumber") String flightNumber,
			@RequestParam(value = "flightDate") String flightDate) {
		tpm.increment();
		gaugeService.submit("tpm", tpm.count.intValue());

		return faresComponent.getFare(flightNumber, flightDate);
	}
}
