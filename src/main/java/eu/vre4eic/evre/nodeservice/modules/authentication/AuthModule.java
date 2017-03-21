package eu.vre4eic.evre.nodeservice.modules.authentication;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
import eu.vre4eic.evre.nodeservice.modules.comm.Publisher;
import eu.vre4eic.evre.nodeservice.modules.comm.PublisherFactory;


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

public class AuthModule {
	
	private static Logger log = LoggerFactory.getLogger(AuthModule.class);

	private static AuthModule instance = null;
	private Hashtable<String, AuthenticationMessage> AuthTable;
	private AuthSubscriber consumer;
	Publisher<AuthenticationMessage> ap;
	
	private static String BROKER_URL = "tcp://localhost:61616";
	
	protected AuthModule() throws JMSException{		
		this(BROKER_URL);		
	}	
	
	protected AuthModule(String brokerURL) throws JMSException{
		//initialize data structure for tokens
		AuthTable = new  Hashtable<String, AuthenticationMessage> ();
		ap = PublisherFactory.getAuthenticationPublisher();
				
		log.info(" #### Authentication Module instanciated ####");
		log.info(" Connecting to Broker:: " + brokerURL);
		
		//subscribe Auth_channel
		doSubcribe(brokerURL);
		
	}
	
	/**
	 * The class constructor is protected and can be instantiated with this method.
	 * The default Broker URL is: v4e-lab.isti.cnr.it
	 * @return AuthModule - the singleton instance of the Class
	 */
	public static AuthModule getInstance() {
		if(instance == null) {
	         try {
				instance = new AuthModule();
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

	public static AuthModule getInstance(String brokerURL) {
		if(instance == null) {
	         try {
				instance = new AuthModule(brokerURL);
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				log.info(e.getMessage()); 
				e.printStackTrace();
			}
	      }
	      return instance;
	      
	}
	
	// TODO do we get it dynamically from NodeService? (see discovery mechanism)
	private String getBrokerURL(){
		return "tcp://v4e-lab.isti.cnr.it:61616";
		
	}
	
	/**
	 * It is a private method invoked during the class instantiation to register a listener to the authentication channel
	 * @param brokerURL - the URL of the Broker provider
	 * @throws JMSException - JMS interfaces are used to connect to the provider
	 */
	private void doSubcribe(String brokerURL) throws JMSException{	
		consumer = new AuthSubscriber(brokerURL);
		Session session = consumer.getSession();
		Destination destination = session.createTopic(Common.AUTH_CHANNEL+"?consumer.retroactive=true");
		MessageConsumer messageConsumer = session.createConsumer(destination);
		messageConsumer.setMessageListener(new AuthListener(this));
		log.info(" subscribed to topic:: " + destination.toString());
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 *  Method invoked by the authentication listener to register tokens of new authenticated users
	 * @param am - AuthenticationMessage received from the system
	 */
	protected void registerToken(AuthenticationMessage am){
		AuthTable.put(am.getToken(), am);
		log.info(" #### registered authentication token ####");
		log.info(" token " + am.getToken() );
		log.info(" token timeout " + am.getTimeLimit() );
		doHousekeeping();
	}
	
	/**
	 *  Method invoked by the authentication listener to register tokens of new authenticated users
	 * @param am - AuthenticationMessage received from the system
	 */
	protected void cancelToken(AuthenticationMessage am) {
		AuthTable.remove(am.getToken());
		log.info(" #### cancelled authentication token ####");
		log.info(" token " + am.getToken() );
		doHousekeeping();	
	}

	
	/**
	 * It must be used to check validity of the token receved with a service invocation
	 * @param token - the token received with a service invocation
	 * @return true - if the token is valid
	 */
	public boolean checkToken (String token) {
		if (AuthTable == null) {
			getInstance();
			return false;
		}
		
		if (AuthTable.containsKey(token)) {
			AuthenticationMessage am = AuthTable.get(token);
			ZoneId zone = ZoneId.of(am.getTimeZone());
			LocalDateTime now = LocalDateTime.now(zone);
			if (now.isBefore(am.getTimeLimit())){ // token valid
				doRenew(am, now);
				return true;
			}
			else { // token expired
				AuthTable.remove(token);
				return false;			
			}

		}
		return false;				
	}
	
	
	
	private void doRenew(AuthenticationMessage am, LocalDateTime now) {
		log.info("########### dorenew ##########");
		log.info(am.getTimeLimit().toString());
		int renewable = Integer.valueOf(am.getRenewable());
		LocalDateTime halftime = am.getTimeLimit().minusMinutes(renewable/2);
		if (now.isAfter(halftime)) {
			am.setTimeLimit(now.plusMinutes(renewable));
			try {
				log.info("########### Renewd ##########");
				log.info(am.getTimeLimit().toString());
				ap.publish(am);
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 *  not used
	 */
	public void close(){
		consumer.close();
	}
	
	
	/**
	 *  helper method to remove the expired token
	 */
	private void doHousekeeping(){
		
//		LocalDateTime now = LocalDateTime.now();	
		for (Entry<String, AuthenticationMessage> entry : AuthTable.entrySet()) {
			LocalDateTime timelimit = entry.getValue().getTimeLimit();
			ZoneId zone = ZoneId.of(entry.getValue().getTimeZone());
			LocalDateTime now = LocalDateTime.now(zone);	
			if (timelimit.isBefore(now))
				AuthTable.remove(entry.getKey());
		}
	}

	/**
	 * utility to print the table of the managed tokens
	 */
	public void listToken(){
		log.info("#### --------------------------- Token list ----------------------------------- ####" );
//		LocalDateTime now = LocalDateTime.now();	
		for (Entry<String, AuthenticationMessage> entry : AuthTable.entrySet()) {
			ZoneId zone = ZoneId.of(entry.getValue().getTimeZone());
			LocalDateTime now = LocalDateTime.now(zone);	
			LocalDateTime timelimit = entry.getValue().getTimeLimit();
			Duration period = Duration.between(now, timelimit);
			if (period.isNegative())
				log.info("Token: "+ entry.getKey() + " EXPIRED:   " + period);
			else
				log.info("Token: "+ entry.getKey() + " VALID for: " + period);
		}
		log.info("#### --------------------------- Token list ----------------------------------- ####" );
	}


}
