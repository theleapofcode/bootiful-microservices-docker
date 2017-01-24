package com.theleapofcode.springboot.flight.fares.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.theleapofcode.springboot.flight.fares.entity.Fare;

public interface FaresRepository extends JpaRepository<Fare, Long> {
	Fare getFareByFlightNumberAndFlightDate(String flightNumber, String flightDate);
}
