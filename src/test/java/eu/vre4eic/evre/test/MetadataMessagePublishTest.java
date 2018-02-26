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
package eu.vre4eic.evre.test;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import eu.vre4eic.evre.core.Common.MetadataOperationType;
import eu.vre4eic.evre.core.Common.ResponseStatus;

import eu.vre4eic.evre.core.messages.MetadataMessage;
import eu.vre4eic.evre.core.messages.impl.MetadataMessageImpl;
import eu.vre4eic.evre.core.comm.Publisher;
import eu.vre4eic.evre.core.comm.PublisherFactory;;

public class MetadataMessagePublishTest {
	
	public static void main(String[] args)  {
		
		//get the publisher for metadata messages
		Publisher<MetadataMessage> mdp =  PublisherFactory.getMetatdaPublisher();
		
		// create the metadatamessage for a query operation
		MetadataMessage mdm = new MetadataMessageImpl(" description", ResponseStatus.SUCCEED)
				.setToken("tokenid")
				.setOperation(MetadataOperationType.QUERY);

		try {
			JSONArray ja= new JSONArray();
			JSONObject jo= new JSONObject();
			try {
				jo.put("milliseconds", "12");
				jo.put("modified", "2017");
				ja.put(jo);
				JSONObject mainObj = new JSONObject();
				mainObj.put("data", jo);
				mdm.setJsonMessage(mainObj);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//publish the message
			
			mdp.publish(mdm);
			
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
		} 
		System.exit(0);

	}
	

}
