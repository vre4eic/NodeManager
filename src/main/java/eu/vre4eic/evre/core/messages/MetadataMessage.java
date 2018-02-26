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



import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonIgnore;

import eu.vre4eic.evre.core.Common.MetadataOperationType;

/**
 * This interface defines the methods  to set/get values used to notify to e-VRE services that an operation has been executed
 * by the Metadata Service
 * @author Cesare
 *
 */
public interface MetadataMessage extends Message {

	/**
	 * Gets the token of the user that has executed the operation. 
	 * @return String - the token identifying the user
	 */
	public String getToken();
	/**
	 * Sets the token of the user that has requested the operation. 
	 * @param token String - the user token
	 */
	public MetadataMessage setToken(String token);

	/**
	 * Returns the operation type
	 * 
	 * @return MetadataOperationType - the operation
	 */
	public MetadataOperationType getOperation();

	/**
	 * Sets the operation type 
	 * 
	 * @param op MetadataOperationType - the type of the operation
	 */
	public MetadataMessage setOperation(MetadataOperationType op);
	
	
	/**
	 * Sets the message as a JSON object 
	 * 
	 * @param message JSONObject - the message
	 */
	
	public void setJsonMessage(JSONObject message);
	
	/**
	 * Returns a JSON object containing the result of the request.
	 * @return JSONObject - a JSON object
	 */
	
	public JSONObject getJsonMessage();
	
	
	
}
