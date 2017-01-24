package com.theleapofcode.springboot.flight.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.theleapofcode.springboot.flight.book.entity.BookingRecord;

public interface BookingRepository extends JpaRepository<BookingRecord, Long> {

}
