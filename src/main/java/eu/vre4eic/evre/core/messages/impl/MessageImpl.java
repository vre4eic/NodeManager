package eu.vre4eic.evre.core.messages.impl;

import org.springframework.context.annotation.Configuration;

import eu.vre4eic.evre.core.Common.ResponseStatus;

@Configuration
public class MessageImpl implements eu.vre4eic.evre.core.messages.Message {

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

	
	public void setStatus(ResponseStatus status) {
		this.status=status;

	}

	
	public void setMessage(String message) {
		this.message=message;

	}

}
