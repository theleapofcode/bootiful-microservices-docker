package com.theleapofcode.springboot.flight.book.exception;

public class BookingException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BookingException(String message) {
		super(message);
	}
}
