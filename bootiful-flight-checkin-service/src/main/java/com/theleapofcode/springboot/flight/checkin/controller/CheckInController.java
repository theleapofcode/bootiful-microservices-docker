package com.theleapofcode.springboot.flight.checkin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.theleapofcode.springboot.flight.checkin.component.CheckinComponent;
import com.theleapofcode.springboot.flight.checkin.entity.CheckInRecord;
import com.theleapofcode.springboot.flight.checkin.stats.TPMCounter;

@RestController
@CrossOrigin
@RequestMapping("/checkin")
public class CheckInController {

	private CheckinComponent checkInComponent;

	private TPMCounter createtpm;

	private TPMCounter gettpm;

	private GaugeService gaugeService;

	@Autowired
	CheckInController(CheckinComponent checkInComponent, GaugeService gaugeService) {
		this.checkInComponent = checkInComponent;
		this.gaugeService = gaugeService;
		this.createtpm = new TPMCounter();
		this.gettpm = new TPMCounter();
	}

	@RequestMapping("/get/{id}")
	CheckInRecord getCheckIn(@PathVariable long id) {
		gettpm.increment();
		gaugeService.submit("gettpm", gettpm.count.intValue());
		return checkInComponent.getCheckInRecord(id);
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	long checkIn(@RequestBody CheckInRecord checkIn) {
		createtpm.increment();
		gaugeService.submit("createtpm", createtpm.count.intValue());
		return checkInComponent.checkIn(checkIn);
	}

}
