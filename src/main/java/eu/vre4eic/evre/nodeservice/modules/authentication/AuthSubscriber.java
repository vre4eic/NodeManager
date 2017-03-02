package eu.vre4eic.evre.nodeservice.modules.authentication;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * Class used to create a connection with the Broker
 *
 * 
 * @author francesco
 *
 */
public class AuthSubscriber {

	private static String brokerURL = "tcp://localhost:61616";
	private static transient ConnectionFactory factory;
	private transient Connection connection;
	private transient Session session;

	public AuthSubscriber() throws JMSException {
		this(brokerURL);
	}

	public AuthSubscriber(String brokerURL) throws JMSException {
		factory = new ActiveMQConnectionFactory(brokerURL);
		
		//TODO limit the list of trusted packages (see http://activemq.apache.org/objectmessage.html)
		((ActiveMQConnectionFactory) factory).setTrustAllPackages(true);
		
		connection = factory.createConnection();
		connection.start();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	}

	public Session getSession() {
		return session;
	}
	
	public void close() {
		if (connection != null) {
			try {
				connection.close();
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}    

	



}
