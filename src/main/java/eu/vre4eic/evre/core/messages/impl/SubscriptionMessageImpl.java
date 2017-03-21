package eu.vre4eic.evre.core.messages.impl;

import eu.vre4eic.evre.core.Common.ResponseStatus;
import eu.vre4eic.evre.core.Common.UserRole;
import eu.vre4eic.evre.core.messages.SubscriptionMessage;

public class SubscriptionMessageImpl implements SubscriptionMessage {

	
	private String messageBrokerURL;
	private ResponseStatus status;
	private String message;
	
	
	public SubscriptionMessageImpl() {
		
	}

	public SubscriptionMessageImpl(String messageBrokerURL, String message, ResponseStatus status) {
		this.status=status;
		this.message=message;
		this.messageBrokerURL=messageBrokerURL;
	}
	
	@Override
	public String getMessage() {
		
		return this.message;
	}

	@Override
	public ResponseStatus getStatus() {
		return this.status;
	}

	@Override
	public SubscriptionMessage setStatus(ResponseStatus status) {
		this.status=status;
		return this;

	}

	@Override
	public SubscriptionMessage setMessage(String message) {
		this.message=message;
		return this;

	}

	@Override
	public String getMessageBrokerURL() {
		return messageBrokerURL;
	}

	@Override
	public void setMessageBrokerURL(String messageBrokerURL) {
		

	this.messageBrokerURL=messageBrokerURL;
	}

}
