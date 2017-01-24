package com.theleapofcode.springboot.flight.book.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@EnableBinding(BookingSource.class)
@Component
public class Sender {

	@Output(BookingSource.InventoryQ)
	@Autowired
	private MessageChannel messageChannel;

	public void send(Object message) {
		messageChannel.send(MessageBuilder.withPayload(message).build());
	}
}

interface BookingSource {
	public static String InventoryQ = "inventoryQ";

	@Output("inventoryQ")
	public MessageChannel inventoryQ();

}
