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

import eu.vre4eic.evre.core.Common.ControlOperationType;
import eu.vre4eic.evre.core.Common.ResponseStatus;
import eu.vre4eic.evre.core.messages.ControlMessage;

/**
 * @author francesco
 *
 */
public class ControlMessageImpl extends MessageImpl implements ControlMessage {

	private ControlOperationType op;

	/**
	 * 
	 */
	public ControlMessageImpl() {
		super();
	}

	/**
	 * @param message
	 * @param status
	 */
	public ControlMessageImpl(String message, ResponseStatus status) {
		super(message, status);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see eu.vre4eic.evre.core.messages.ControlMessage#setOperationType(eu.vre4eic.evre.core.Common.ControlOperationType)
	 */
	@Override
	public ControlMessage setOperationType(ControlOperationType op) {
		this.op = op;
		return this;
	}

	@Override
	public ControlOperationType getOperationType() {
		// TODO Auto-generated method stub
		return op;
	}

}
