package com.theleapofcode.springboot.flight.checkin.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@EnableBinding(CheckInSource.class)
@Component
public class Sender {

	@Output(CheckInSource.CHECKINQ)
	@Autowired
	private MessageChannel messageChannel;

	public void send(Object message) {
		messageChannel.send(MessageBuilder.withPayload(message).build());
	}
}

interface CheckInSource {
	public static String CHECKINQ = "checkInQ";

	@Output("checkInQ")
	public MessageChannel checkInQ();

}