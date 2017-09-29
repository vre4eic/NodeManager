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
package eu.vre4eic.evre.nodeservice.modules.monitor;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;

import org.apache.activemq.advisory.AdvisorySupport;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ActiveMQMessage;
import org.apache.activemq.command.CommandTypes;
import org.apache.activemq.command.ConsumerId;
import org.apache.activemq.command.ConsumerInfo;
import org.apache.activemq.command.DataStructure;
import org.apache.activemq.command.RemoveInfo;

import eu.vre4eic.evre.core.Common.Topics;
import eu.vre4eic.evre.core.comm.CommModule;


/**
 * @author francesco
 *
 */
public class AdvisoryModule implements MessageListener {
	
	private static AdvisoryModule instance = null;
	public AdvisoryModule(){

		Session session =CommModule.getInstance().getSession();
		for (Topics topic : Topics.values()) {
			try {

				ActiveMQDestination destination = (ActiveMQDestination)session.createTopic(topic.name());

				Destination consumerTopic = AdvisorySupport.getConsumerAdvisoryTopic(destination);
				System.out.println("Subscribing to advisory " + consumerTopic);
				MessageConsumer consumerAdvisory = session.createConsumer(consumerTopic);
				consumerAdvisory.setMessageListener(this);

			} catch (JMSException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}
	
	public static AdvisoryModule getInstance() {
		if(instance == null) {
	         instance = new AdvisoryModule();
	      }
	      return instance;
	      
	}

	@Override
	public void onMessage(Message message) {
		System.out.println("############ ADVISORY #############");
//		System.out.println(message);
		ActiveMQMessage msg = (ActiveMQMessage) message;
		DataStructure ds = msg.getDataStructure();
		if (ds != null) {
			switch (ds.getDataStructureType()) {
			case CommandTypes.CONSUMER_INFO:
				ConsumerInfo consumerInfo = (ConsumerInfo) ds;
				System.out.println("Consumer '" + consumerInfo.getConsumerId()
						+ "' subscribed to '" + consumerInfo.getDestination()
						+ "'");
				break;
			case CommandTypes.REMOVE_INFO:
				RemoveInfo removeInfo = (RemoveInfo) ds;
				ConsumerId consumerId = ((ConsumerId) removeInfo.getObjectId());
				System.out
						.println("Consumer '" + consumerId + "' unsubscribed");
				break;
			default:
				System.out.println("Unkown data structure type");
			}
		} else {
			System.out.println("No data structure provided");
		}
	}		
	}


