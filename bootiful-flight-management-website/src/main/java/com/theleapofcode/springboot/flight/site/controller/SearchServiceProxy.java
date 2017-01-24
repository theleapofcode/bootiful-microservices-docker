package com.theleapofcode.springboot.flight.site.controller;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.theleapofcode.springboot.flight.site.model.Flight;
import com.theleapofcode.springboot.flight.site.model.SearchQuery;

@FeignClient(name = "api-gateway")
public interface SearchServiceProxy {
	@RequestMapping(value = "searchapi/search/get", method = RequestMethod.POST)
	List<Flight> search(@RequestBody SearchQuery searchQuery);
}
