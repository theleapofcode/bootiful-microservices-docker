package com.theleapofcode.springboot.flight.checkin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.theleapofcode.springboot.flight.checkin.entity.CheckInRecord;

public interface CheckinRepository extends JpaRepository<CheckInRecord, Long> {

}
