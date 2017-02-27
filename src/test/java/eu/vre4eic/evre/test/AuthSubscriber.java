package eu.vre4eic.evre.test;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

import eu.vre4eic.evre.core.Common;

public class AuthSubscriber {

	private static String brokerURL = "tcp://localhost:61616";
	private static transient ConnectionFactory factory;
	private transient Connection connection;
	private transient Session session;

	public AuthSubscriber() throws JMSException {
		factory = new ActiveMQConnectionFactory(brokerURL);
		
		//TODO limit the list of trusted packages (see http://activemq.apache.org/objectmessage.html)
		((ActiveMQConnectionFactory) factory).setTrustAllPackages(true);
		
		connection = factory.createConnection();
		connection.start();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	}

	public void close() throws JMSException {
		if (connection != null) {
			connection.close();
		}
	}    

	public static void main(String[] args) throws JMSException {
		AuthSubscriber consumer = new AuthSubscriber();
		Session session = consumer.getSession();
		Destination destination = session.createTopic(Common.AUTH_CHANNEL);
		MessageConsumer messageConsumer = session.createConsumer(destination);
		messageConsumer.setMessageListener(new AuthListener());
	}

	public Session getSession() {
		return session;
	}


}
