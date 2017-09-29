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
package eu.vre4eic.evre.nodeservice.modules.metadata;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;

import javax.jms.JMSException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.vre4eic.evre.core.comm.MessageListener;
import eu.vre4eic.evre.core.comm.Publisher;
import eu.vre4eic.evre.core.comm.Subscriber;
import eu.vre4eic.evre.core.comm.SubscriberFactory;
import eu.vre4eic.evre.core.messages.MetadataMessage;


/**
 * This class must be instanced to automatically receive information of the users authenticated with the system.
 * The method checkToken() can be used  to verify if the token received during the invocation of a service is valid.
 * 
 * example:
 * <br>
 * <code>
 * AuthModule module = AuthModule.getInstance(tcp://localhost:61616); <br>
 * module.checkToken(tkn);
 * </code>
 * 
 * <br>
 * If you don't specify a Broker URL ( i.e. AuthModule.getInstance() )
 * <br>then the default URL is tcp://v4e-lab.isti.cnr.it:61616
 * 
 * @author francesco
 *
 */

public class MDModule {
	
	private static Logger log = LoggerFactory.getLogger(MDModule.class);

	private static MDModule instance = null;
	private Hashtable<String, List<MetadataMessage>> mdTable;
	Publisher<MetadataMessage> mdp;
	
	private static String BROKER_URL = "tcp://localhost:61616";
	
	protected MDModule() throws JMSException{		
		this(BROKER_URL);		
	}	
	
	// TODO Broker URL is not valid anymore ... we use configuration of communication module
	protected MDModule(String brokerURL) throws JMSException{
		//initialize data structure for tokens
		mdTable = new  Hashtable<String, List<MetadataMessage>> ();
				
		log.info(" #### Metadata Module instanciated ####");
		log.info(" Connecting to Broker:: " + brokerURL);
		
		//subscribe Auth_channel
		doSubcribe();
		
	}
	
	/**
	 * The class constructor is protected and can be instantiated with this method.
	 * The default Broker URL is: v4e-lab.isti.cnr.it
	 * @return AuthModule - the singleton instance of the Class
	 */
	public static MDModule getInstance() {
		if(instance == null) {
	         try {
				instance = new MDModule();
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
	 * @return AuthModule - the singleton instance of the Class
	 */

	public static MDModule getInstance(String brokerURL) {
		if(instance == null) {
	         try {
				instance = new MDModule(brokerURL);
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				log.info(e.getMessage()); 
				e.printStackTrace();
			}
	      }
	      return instance;
	      
	}
	
	/**
	 * It is a private method invoked during the class instantiation to register a listener to the authentication channel
	 * @param brokerURL - the URL of the Broker provider
	 * @throws JMSException - JMS interfaces are used to connect to the provider
	 */
	private void doSubcribe() throws JMSException{	
		Subscriber<MetadataMessage> subscriber = SubscriberFactory.getMetadataSubscriber();
		
		subscriber.setListener(new MessageListener<MetadataMessage>()  {			
			@Override
			public void onMessage(MetadataMessage mdm) {
				addMessage(mdm);				
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
	 *  Method invoked by the authentication listener to register tokens of new authenticated users
	 * @param mdm - AuthenticationMessage received from the system
	 */
	protected void addMessage(MetadataMessage mdm){
		synchronized(mdTable) {
			List<MetadataMessage> list =  mdTable.get(mdm.getToken());
			if (list == null) {
				list = new ArrayList<MetadataMessage>();
				mdTable.put(mdm.getToken(), list);
			}
			list.add(mdm);
		}
	}
	

	


	/**
	 * utility to print the table of the managed tokens
	 */
	public void listToken(){
		log.info("" );log.info("" );
		log.info("#### ------------------MetaData Operations listed by Tokens---------------------- ####" );
		for (Entry<String, List<MetadataMessage>> tokenEntry : mdTable.entrySet()) {
			log.info("------------------ Token " + tokenEntry.getKey() +"  ----------------------" );
			for (MetadataMessage entry : tokenEntry.getValue()) {
				log.info(entry.getOperation().toString() );
			}					
			log.info("---------------------------------------------------------------------- " );
			
		}
		log.info("#### ------------------MetaData Operations listed by Tokens---------------------- ####" );
	}


}
