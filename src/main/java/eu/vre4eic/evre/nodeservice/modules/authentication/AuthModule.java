package eu.vre4eic.evre.nodeservice.modules.authentication;

import java.time.LocalDateTime;
import java.util.Hashtable;
import java.util.Map.Entry;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.vre4eic.evre.core.Common;
import eu.vre4eic.evre.core.messages.AuthenticationMessage;

public class AuthModule {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	private static AuthModule instance = null;
	private Hashtable<String, AuthenticationMessage> AuthTable;
	private AuthSubscriber consumer;
	
	protected AuthModule() throws JMSException{
		//initialize data structure for tokens
		AuthTable = new  Hashtable<String, AuthenticationMessage> ();
		
		//retrieve Broker URL
		String brokerURL = getBrokerURL();
		
		log.info(" #### Authentication Module instanciated ####");
		log.info(" Connecting to Broker:: " + brokerURL);
		
		//subscribe Auth_channel
		doSubcribe(brokerURL);
		
	}
	
	public static AuthModule getInstance() {
		if(instance == null) {
	         try {
				instance = new AuthModule();
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	      }
	      return instance;
	      
	}
	
	// TODO do we get it dynamically from NodeService? (see discovery mechanism)
	private String getBrokerURL(){
		return "tcp://v4e-lab.isti.cnr.it:61616";
		
	}
	
	private void doSubcribe(String brokerURL) throws JMSException{	
		consumer = new AuthSubscriber(brokerURL);
		Session session = consumer.getSession();
		Destination destination = session.createTopic(Common.AUTH_CHANNEL);
		MessageConsumer messageConsumer = session.createConsumer(destination);
		messageConsumer.setMessageListener(new AuthListener(this));
		log.info(" subscribed to topic:: " + Common.AUTH_CHANNEL);

	}

	public void registerToken(AuthenticationMessage am){
		AuthTable.put(am.getToken(), am);
		doHousekeeping();
	}
	
	public boolean checkToken (String tkn) {
		if (AuthTable == null) {
			getInstance();
			return false;
		}
		
		if (AuthTable.containsKey(tkn)) {
			AuthenticationMessage am = AuthTable.get(tkn);
			LocalDateTime now = LocalDateTime.now();
			if (now.isBefore(am.getTimeLimit()))
				return true;
			else {
				AuthTable.remove(tkn);
				return false;			
			}

		}
		return false;				
	}
	
	
	
	public void close(){
		consumer.close();
	}
	
	
	private void doHousekeeping(){
		LocalDateTime now = LocalDateTime.now();	
		for (Entry<String, AuthenticationMessage> entry : AuthTable.entrySet()) {
			LocalDateTime timelimit = entry.getValue().getTimeLimit();
			if (timelimit.isBefore(now))
				AuthTable.remove(entry.getKey());
		}
	}

}
