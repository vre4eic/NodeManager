/*******************************************************************************
 * Copyright (c) 2018 VRE4EIC Consortium
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

import org.springframework.context.annotation.Configuration;

import eu.vre4eic.evre.core.Common.ResponseStatus;
import eu.vre4eic.evre.core.messages.Message;

/**
 * This class implements the methods defined in {@link eu.vre4eic.evre.core.messages.Message} interface.
 * 
 * @author Cesare 
 *
 */
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
