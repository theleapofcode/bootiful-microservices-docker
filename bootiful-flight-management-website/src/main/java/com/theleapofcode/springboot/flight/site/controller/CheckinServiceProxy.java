package com.theleapofcode.springboot.flight.site.controller;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.theleapofcode.springboot.flight.site.model.CheckInRecord;

@FeignClient(name = "api-gateway")
public interface CheckinServiceProxy {
	@RequestMapping(value = "checkinapi/checkin/create", method = RequestMethod.POST)
	long checkin(@RequestBody CheckInRecord checkInRecord);
}
