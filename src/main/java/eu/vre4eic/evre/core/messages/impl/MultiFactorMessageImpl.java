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
/**
 * 
 */
package eu.vre4eic.evre.core.messages.impl;

import eu.vre4eic.evre.core.Common.ResponseStatus;
import eu.vre4eic.evre.core.messages.MultiFactorMessage;

/**
 * @author francesco
 *
 */
public class MultiFactorMessageImpl extends MessageImpl implements MultiFactorMessage {

	private String authId;
	private String userId;
	private String code;

	/**
	 * 
	 */
	public MultiFactorMessageImpl() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param status
	 */
	public MultiFactorMessageImpl(String message, ResponseStatus status) {
		super(message, status);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see eu.vre4eic.evre.core.messages.MultiFactorMessage#setAuthId(java.lang.String)
	 */
	@Override
	public MultiFactorMessage setAuthId(String authId) {
		this.authId = authId;
		return this;
	}

	/* (non-Javadoc)
	 * @see eu.vre4eic.evre.core.messages.MultiFactorMessage#getAuthId()
	 */
	@Override
	public String getAuthId() {
		return authId;
	}

	/* (non-Javadoc)
	 * @see eu.vre4eic.evre.core.messages.MultiFactorMessage#setUserId(java.lang.String)
	 */
	@Override
	public MultiFactorMessage setUserId(String userId) {
		this.userId = userId;
		return this;
	}

	/* (non-Javadoc)
	 * @see eu.vre4eic.evre.core.messages.MultiFactorMessage#getUserId()
	 */
	@Override
	public String getUserId() {
		return userId;
	}

	@Override
	public MultiFactorMessage setCode(String code) {
		this.code = code;
		return this;
	}

	@Override
	public String getCode() {
		return code;
	}

}
