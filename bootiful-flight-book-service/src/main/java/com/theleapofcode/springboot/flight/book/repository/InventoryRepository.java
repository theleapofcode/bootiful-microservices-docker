package com.theleapofcode.springboot.flight.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.theleapofcode.springboot.flight.book.entity.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

	Inventory findByFlightNumberAndFlightDate(String flightNumber, String flightDate);

}
