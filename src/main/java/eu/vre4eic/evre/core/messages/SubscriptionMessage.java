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



/**
 * This interface defines the <i>message</i> published when a topic is subscribed
 * @author Cesare
 *
 */
public interface SubscriptionMessage extends Message {

	/**
	 * Gets the URL of the service managing the Message Broker
	 * @return String - the URL of the Message Broker
	 */
	public String getMessageBrokerURL();
	
	/**
	 * Sets the URL of the service managing the Message Broker
	 *
	 * @param id String - the Message Broker URL
	 */
	public void setMessageBrokerURL(String id);

}
