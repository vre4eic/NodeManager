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
package eu.vre4eic.evre.core.messages;


import eu.vre4eic.evre.core.Common.ServiceStatus;
import eu.vre4eic.evre.core.Common.UserRole;

/**
 * This interface defines the message published when a change happens in a service life-cycle.
 * @author Cesare
 *
 */
public interface ServiceLCMessage extends Message {

	/**
	 * Gets the identifier of the service. 
	 * @return String - the id identifying the service
	 */
	public String getServiceId();
	
	/**
	 * Sets the the identifier of the service.
	 *
	 * @param id String - the service identifier
	 */
	public void setServiceId(String id);

	/**
	 * Returns the new life-cycle status
	 * 
	 * @return ServiceStatus - the current service status
	 * @see UserRole
	 */
	public ServiceStatus getServiceStatus();

	/**
	 * Sets the the current service status 
	 * 
	 * @param status ServiceStatus - the service status
	 * @see UserRole
	 */
	public void setServiceStatus(ServiceStatus serviceStatus);
	
}
