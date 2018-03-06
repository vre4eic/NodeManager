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



import org.json.JSONException;
import org.json.JSONObject;

import eu.vre4eic.evre.core.Common.MetadataOperationType;
import eu.vre4eic.evre.core.Common.ResponseStatus;
import eu.vre4eic.evre.core.messages.Message;
import eu.vre4eic.evre.core.messages.MetadataMessage;

/**
 * This class implements the methods defined in {@link eu.vre4eic.evre.core.messages.MetadataMessage} interface.
 * 
 * @author francesco
 *
 */
public class MetadataMessageImpl extends MessageImpl implements MetadataMessage {
	
	
	
	private String token;
	private MetadataOperationType operation;
	private String jsonMessage;

	public  MetadataMessageImpl() {
		super();
	}
	
	public  MetadataMessageImpl(String message, ResponseStatus status) {
		super(message,status);
	}
	

	
	@Override
	public String getToken() {
		return token;
	}

	
	@Override
	public MetadataMessage setToken(String token) {
		this.token = token;
		return this;

	}

	
	@Override
	public MetadataOperationType getOperation() {
		return operation;
	}

	
	@Override
	public MetadataMessage setOperation(MetadataOperationType op) {
		this.operation = op;
		return this;
	}
	public String toJSON() {
        org.json.JSONObject object = new org.json.JSONObject();
        try{
        object.put("token", this.token);
        object.put("operation", this.operation.toString());
       // object.put("message", this.message);
        object.put("status", ""+this.status);
        object.put("message", new JSONObject(this.jsonMessage));
        }
        catch(Exception e){
        	e.printStackTrace();
        }
        return object.toString();
    }

	@Override
	public void setJsonMessage(JSONObject message) {
		this.jsonMessage=message.toString();
		
	}

	@Override
	public JSONObject getJsonMessage() {
		
		try {
			return new JSONObject(this.jsonMessage);
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
		return null;
	}


}
