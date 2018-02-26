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
package eu.vre4eic.evre.core.comm;

import java.util.Properties;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

import eu.vre4eic.evre.nodeservice.Settings;
import eu.vre4eic.evre.nodeservice.nodemanager.ZKServer;

/**
 * @author francesco
 *
 */
public class CommModule {
	
	private static CommModule instance;
	private ActiveMQConnectionFactory factory;
	private Connection connection;
	private Session session;
	private MessageProducer producer;
	
	protected CommModule() throws JMSException {

		ZKServer.init();
		Properties defaultSettings = Settings.getProperties();
		String ZkServer = defaultSettings.getProperty(Settings.ZOOKEEPER_DEFAULT);
		
		NodeLinker node = NodeLinker.init(ZkServer);		
		String messageBrokerURL =  node.getMessageBrokerURL();

		try {

			factory = new ActiveMQConnectionFactory(messageBrokerURL);
			// TODO
			// Development configuration to be fixed with trusted packages
			factory.setTrustAllPackages(true);

			connection = factory.createConnection();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);



			getConnection().start();
		} catch (JMSException jmse) {
			getConnection().close();
			throw jmse;
		}
}

	protected CommModule(String borokerURL) throws JMSException {

		factory = new ActiveMQConnectionFactory(borokerURL);
		// TODO
		// Development configuration to be fixed with trusted packages
		factory.setTrustAllPackages(true);
		
		connection = factory.createConnection();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

	
		try {
			getConnection().start();
		} catch (JMSException jmse) {
			getConnection().close();
			throw jmse;
		}
}
	
	public static CommModule getInstance() {
		if(instance == null) {
			try {
				instance = new CommModule();
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return instance;

	}

	public static CommModule getInstance(String brokerURL) {
		if(instance == null) {
	         try {
				instance = new CommModule(brokerURL);
			} catch (JMSException e) {
				
				e.printStackTrace();
			}
	      }
	      return instance;
	      
	}

	public Connection getConnection() {
		return connection;
	}


	public Session getSession() {
		return session;
	}


	public MessageProducer getProducer() {
		if (producer==null)
			try {
				producer = session.createProducer(null);
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return producer;
	}






}
