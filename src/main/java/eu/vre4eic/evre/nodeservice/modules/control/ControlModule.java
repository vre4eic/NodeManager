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
package eu.vre4eic.evre.nodeservice.modules.control;

import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;

import javax.jms.JMSException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.vre4eic.evre.core.comm.MessageListener;

import eu.vre4eic.evre.core.comm.Subscriber;
import eu.vre4eic.evre.core.comm.SubscriberFactory;
import eu.vre4eic.evre.core.messages.ControlMessage;


/**
 * This class must be instanced to automatically receive information about building blocks
 *
 * @author francesco
 *
 */

public class ControlModule {
	
	private static Logger log = LoggerFactory.getLogger(ControlModule.class);

	private static ControlModule instance = null;
	
	private static String BROKER_URL = "tcp://localhost:61616";
	
	protected ControlModule() throws JMSException{		
		this(BROKER_URL);		
	}	
	
	// TODO Broker URL is not valid anymore ... we use configuration of communication module
	protected ControlModule(String brokerURL) throws JMSException{
				
		log.info(" #### Control Module instanciated ####");
		log.info(" Connecting to Broker:: " + brokerURL);
		
		//subscribe Auth_channel
		doSubcribe();
		
	}
	
	/**
	 * The class constructor is protected and can be instantiated with this method.
	 * @return ControlModule - the singleton instance of the Class
	 */
	public static ControlModule getInstance() {
		if(instance == null) {
	         try {
				instance = new ControlModule();
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				log.info(e.getMessage()); 
				e.printStackTrace();
			}
	      }
	      return instance;
	      
	}
	
	/**
	 * The class constructor is protected and can be instanced only by this method.
	 * @param brokerURL -  the URL of the Local or Remote Broker
	 * @return ControlModule - the singleton instance of the Class
	 */

	public static ControlModule getInstance(String brokerURL) {
		if(instance == null) {
	         try {
				instance = new ControlModule(brokerURL);
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				log.info(e.getMessage()); 
				e.printStackTrace();
			}
	      }
	      return instance;
	      
	}
	
	/**
	 * It is a private method to register a listener to the authentication channel
	 * @throws JMSException - JMS interfaces are used to connect to the provider
	 */
	private void doSubcribe() throws JMSException{	
		Subscriber<ControlMessage> subscriber = SubscriberFactory.getControlSubscriber();
		
		subscriber.setListener(new MessageListener<ControlMessage>()  {			
			@Override
			public void onMessage(ControlMessage msg) {
				doSomething(msg);				
			}			
		});
		
		// Forces thread switch to receive early notification on Auth_channel
		// TODO improve handshake
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 *  Method invoked by the authentication listener to read messages
	 * @param msg ControlMessage
	 */
	protected void doSomething(ControlMessage msg){

			log.info(" #######  Control Message arrived ########");
			log.info("Op type: " + msg.getOperationType());
			log.info("Op  msg: " + msg.getMessage());
	}
	



}
