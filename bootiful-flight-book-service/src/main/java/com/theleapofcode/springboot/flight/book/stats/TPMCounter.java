package com.theleapofcode.springboot.flight.book.stats;

import java.util.Calendar;
import java.util.concurrent.atomic.LongAdder;

public class TPMCounter {
	public LongAdder count;
	private Calendar expiry = null;

	public TPMCounter() {
		reset();
	}

	public void reset() {
		count = new LongAdder();
		expiry = Calendar.getInstance();
		expiry.add(Calendar.MINUTE, 1);
	}

	public boolean isExpired() {
		return Calendar.getInstance().after(expiry);
	}

	public void increment() {
		if (isExpired()) {
			reset();
		}
		count.increment();
	}

}
