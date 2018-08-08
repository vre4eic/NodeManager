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
/**
 * 
 */
package eu.vre4eic.evre.core.messages.impl;

import eu.vre4eic.evre.core.Common.ControlOperationType;
import eu.vre4eic.evre.core.Common.ResponseStatus;
import eu.vre4eic.evre.core.messages.LifeCycleMessage;

/**
 * @author francesco
 *
 */
public class LifeCycleMessageImpl extends ControlMessageImpl implements LifeCycleMessage {

	private String serviceName;
	private String entryPoint;

	public LifeCycleMessageImpl() {
		super();
	}

	
	public LifeCycleMessageImpl(String message, ResponseStatus status, ControlOperationType op) {
		super(message, status);
		setOperationType(op);
	}


	@Override
	public LifeCycleMessage setServiceName(String name) {
		serviceName = name;
		return this;
	}


	@Override
	public String getServiceName() {
		return serviceName;
	}


	@Override
	public LifeCycleMessage setEntryPoint(String ep) {
		entryPoint = ep;
		return this;
	}


	@Override
	public String getEntryPoint() {
		return entryPoint;
	}


}
