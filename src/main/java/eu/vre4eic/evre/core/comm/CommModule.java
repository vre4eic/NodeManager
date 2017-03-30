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

import eu.vre4eic.evre.nodeservice.Utils;

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
