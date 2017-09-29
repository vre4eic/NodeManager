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
package eu.vre4eic.evre.core.comm;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.springframework.jms.annotation.EnableJms;

import eu.vre4eic.evre.core.Common.Topics;
import eu.vre4eic.evre.core.messages.Message;

import eu.vre4eic.evre.core.comm.CommModule;

/**
 * @author francesco
 *
 */

@EnableJms
public class Publisher<T extends Message>  {

	private  MessageProducer dipatcher;
	private Session session;

	public Publisher(Topics channel) {
		try {
			session  = CommModule.getInstance().getSession();
			Destination destination = session.createTopic(channel.toString());
			dipatcher = session.createProducer(destination);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Publisher(String brokerURL, Topics channel) {
		try {
			session  = CommModule.getInstance(brokerURL).getSession();
			Destination destination = session.createTopic(channel.toString());
			dipatcher = session.createProducer(destination);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void publish(T m)  {
		ObjectMessage om;
		try {
			om = session.createObjectMessage(m);
			dipatcher.send(om);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}




}
