/**
 * 
 */
package eu.vre4eic.evre.nodeservice.comm.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.annotation.EnableJms;

import eu.vre4eic.evre.core.Common;
import eu.vre4eic.evre.core.messages.AuthenticationMessage;
import eu.vre4eic.evre.nodeservice.NodeServiceStart;
import eu.vre4eic.evre.nodeservice.comm.CommToolKit;

/**
 * @author francesco
 *
 */

@EnableJms
public class Publisher implements CommToolKit {

	protected static String brokerURL = "tcp://localhost:61616";
	protected static transient ConnectionFactory factory;
	protected transient Connection connection;
	protected transient Session session;
	protected transient MessageProducer producer;


	//private ApplicationContext context = NodeServiceStart.getApplicationContext();

	public Publisher() throws JMSException {
		
		InputStream in;
		Properties property = new Properties();
		try {
			in = this.getClass().getClassLoader()
					.getResourceAsStream("Nodeservice.properties");
			property.load(in);
			in.close();
		} catch (FileNotFoundException e) {

		} catch (IOException e) {

		}

		
		factory = new ActiveMQConnectionFactory(property.getProperty("BROKER_URL"));
		connection = factory.createConnection();
		try {
			connection.start();
		} catch (JMSException jmse) {
			connection.close();
			throw jmse;
		}
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		producer = session.createProducer(null);
	}

	public void close() throws JMSException {
		if (connection != null) {
			connection.close();
		}
	}




	/* (non-Javadoc)
	 * @see eu.vre4eic.evre.nodeservice.comm.CommToolKit#publishAuthentication(eu.vre4eic.evre.core.messages.AuthenticationMessage)
	 */
	@Override
	public void publishAuthentication(AuthenticationMessage m) throws JMSException {

		Destination destination = session.createTopic(Common.AUTH_CHANNEL);

		MapMessage message = session.createMapMessage();
		message.setString("token", m.getToken());
		message.setString("ttl", m.getTTL());
		//message.setInt("role", (int) m.getRole());

		producer.send(destination, message);
	}


}
