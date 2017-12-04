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
package eu.vre4eic.evre.test;

import javax.jms.JMSException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import eu.vre4eic.evre.core.Common.MetadataOperationType;
import eu.vre4eic.evre.core.Common.ResponseStatus;
import eu.vre4eic.evre.core.Common.Topics;
import eu.vre4eic.evre.core.messages.MetadataMessage;
import eu.vre4eic.evre.core.messages.impl.MetadataMessageImpl;
import eu.vre4eic.evre.core.comm.Publisher;
import eu.vre4eic.evre.core.comm.PublisherFactory;;

public class MetadataTest {
	
	public static void main(String[] args)  {
		
//		System.out.println(Topics.AUTH_Channel);
		Publisher<MetadataMessage> mdp =  PublisherFactory.getMetatdaPublisher();
		MetadataMessage mdm = new MetadataMessageImpl("pluto message", ResponseStatus.SUCCEED)
				.setToken("pluto")
				.setOperation(MetadataOperationType.QUERY);

		
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
		try {
			mdp.publish(mdm);
			Thread.sleep(2000);
			mdm.setOperation(MetadataOperationType.INSERT);
			mdp.publish(mdm);
			Thread.sleep(2000);
			mdm.setOperation(MetadataOperationType.UPDATE);
			mdp.publish(mdm);
			
			Thread.sleep(3000);
			mdm.setToken("pippo").setOperation(MetadataOperationType.QUERY).setMessage("pippo message");
			mdp.publish(mdm);			
			Thread.sleep(2000);
			mdm.setOperation(MetadataOperationType.INSERT);
			mdp.publish(mdm);
			Thread.sleep(2000);
			mdm.setOperation(MetadataOperationType.UPDATE);
			mdp.publish(mdm);
			
			
			

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		for (Topics topic : Topics.values()) {
			System.out.println(topic.name());
		}

	}
	

}
