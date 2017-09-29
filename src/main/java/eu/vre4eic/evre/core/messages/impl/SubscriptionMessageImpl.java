/*******************************************************************************
 * Copyright (c) 2017 VRE4EIC Consortium
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package eu.vre4eic.evre.core.messages.impl;

import eu.vre4eic.evre.core.Common.ResponseStatus;
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
