package eu.vre4eic.evre.core.messages.impl;

import org.springframework.context.annotation.Configuration;

import eu.vre4eic.evre.core.Common.ResponseStatus;
import eu.vre4eic.evre.core.messages.Message;

@Configuration
public class MessageImpl implements Message {

	String message;
	ResponseStatus status;
	
	public MessageImpl() {
		super();
	}

	public MessageImpl(String message, ResponseStatus status) {
		super();
		this.status = status;
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}
	
	

	
	public ResponseStatus getStatus() {
		return this.status;

	}

	
	public Message setStatus(ResponseStatus status) {
		this.status=status;
		return this;

	}

	
	public Message setMessage(String message) {
		this.message=message;
		return this;

	}

}
