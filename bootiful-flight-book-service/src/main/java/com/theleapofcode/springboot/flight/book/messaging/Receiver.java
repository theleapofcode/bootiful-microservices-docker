package com.theleapofcode.springboot.flight.book.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

import com.theleapofcode.springboot.flight.book.component.BookingComponent;
import com.theleapofcode.springboot.flight.book.component.BookingStatus;

@EnableBinding(BookingSink.class)
@Component
public class Receiver {

	@Autowired
	BookingComponent bookingComponent;

	@ServiceActivator(inputChannel = BookingSink.CHECKINQ)
	public void accept(long bookingID) {
		bookingComponent.updateStatus(BookingStatus.CHECKED_IN, bookingID);
	}

}

interface BookingSink {
	public static String CHECKINQ = "checkInQ";

	@Input("checkInQ")
	public MessageChannel checkInQ();

}