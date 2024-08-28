package com.keyi.client.param;

import java.util.concurrent.atomic.AtomicLong;

public class ClientRequest {
	private final long id;
	private Object content;
	private static final AtomicLong aid = new AtomicLong();
	
	private String command;

	public ClientRequest() {
		id = aid.incrementAndGet();
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}

	public long getId() {
		return id;
	}

}
