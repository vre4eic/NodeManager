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


import java.io.Serializable;

import  eu.vre4eic.evre.core.Common.ResponseStatus;

/**
 * This interface defines the methods to set/get the values returned when an operation is executed.
 * @author Cesare
 *
 */

public interface Message extends Serializable{
	/**
	 * Returns a message related to the request.
	 * @return String - a text message
	 */
	
	public String getMessage();
	
	/**
	 * Gets the status of the executed request
	 * @return status ResponseStatus - FAILED, SUCCEED, EMPTY_RESULT, WARNING
	 */
	public ResponseStatus getStatus() ;
	
	/**
	 * Sets the status of the executed request
	 * @param status ResponseStatus - FAILED, SUCCEED, EMPTY_RESULT, WARNING
	 */
	public Message setStatus(ResponseStatus status) ;

	/**
	 * Sets a message to give details on the operation.
	 * @param message String - the message to set
	 */
	public Message setMessage(String message);

}
