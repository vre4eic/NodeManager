/**
 * 
 */
package eu.vre4eic.evre.nodeservice.modules.comm;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

import eu.vre4eic.evre.nodeservice.Utils;
import eu.vre4eic.evre.nodeservice.modules.authentication.AuthModule;

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


		Properties property = Utils.getNodeServiceProperties();
		factory = new ActiveMQConnectionFactory(property.getProperty("BROKER_URL"));
		factory.setWatchTopicAdvisories(true);
		connection = factory.createConnection();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		producer = session.createProducer(null);

	
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


	public Connection getConnection() {
		return connection;
	}


	public Session getSession() {
		return session;
	}


	public MessageProducer getProducer() {
		return producer;
	}






}
