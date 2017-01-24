package com.theleapofcode.springboot.flight.search.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.theleapofcode.springboot.flight.search.component.SearchComponent;
import com.theleapofcode.springboot.flight.search.component.SearchQuery;
import com.theleapofcode.springboot.flight.search.entity.Flight;
import com.theleapofcode.springboot.flight.search.stats.TPMCounter;

@RefreshScope
@CrossOrigin
@RestController
@RequestMapping("/search")
class SearchRestController {
	private static final Logger logger = LoggerFactory.getLogger(SearchRestController.class);

	private SearchComponent searchComponent;

	@Value("${originairports.shutdown}")
	private String originAirportShutdownList;

	private TPMCounter tpm;

	private GaugeService gaugeService;

	@Autowired
	public SearchRestController(SearchComponent searchComponent, GaugeService gaugeService) {
		this.searchComponent = searchComponent;
		this.gaugeService = gaugeService;
		this.tpm = new TPMCounter();
	}

	@RequestMapping(value = "/get", method = RequestMethod.POST)
	List<Flight> search(@RequestBody SearchQuery query) {
		System.out.println("Input : " + query);
		if (Arrays.asList(originAirportShutdownList.split(",")).contains(query.getOrigin())) {
			logger.info("The origin airport is in shutdown state");
			return new ArrayList<Flight>();
		}

		tpm.increment();
		gaugeService.submit("tpm", tpm.count.intValue());

		return searchComponent.search(query);
	}

}
