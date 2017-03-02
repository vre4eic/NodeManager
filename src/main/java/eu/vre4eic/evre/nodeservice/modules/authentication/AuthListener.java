package eu.vre4eic.evre.nodeservice.modules.authentication;

import java.time.LocalDateTime;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.vre4eic.evre.core.messages.AuthenticationMessage;

/**
 * 
 * Class used to receive asynchronous messages of authenticated users in  the system.
 * The authentication message can represent  Login or Logout operations and contains token which must be used for each operation.
 * @author francesco
 *
 */
public class AuthListener implements MessageListener{
	
	private AuthModule module;
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	public  AuthListener(AuthModule authModule) {
		this.module = authModule;
	}
	
	/* (non-Javadoc)
	 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	 */
	@Override
	public void onMessage(Message message) {
		try {

	        if (message instanceof ObjectMessage) {
	            try {
	            	AuthenticationMessage am = (AuthenticationMessage) ((ObjectMessage) message).getObject();
	            	log.info("##### authentication message arrived #####");
	            	log.info("token: " + am.getToken());
	            	log.info("time limit: "+ am.getTimeLimit());

	            	if (am.getTimeLimit().equals(LocalDateTime.MIN))
	            		module.cancelToken(am);
	            	else
	            		module.registerToken(am);           	
	            	
	            }
	            catch (JMSException ex) {
	                throw new RuntimeException(ex);
	            }
	        }
	        else {
	            throw new IllegalArgumentException("Message must be of type ObjectMessage serializing AuthenticationMessage");
	        }
		
		} catch (Exception e) {
			// MessageListener cannot throw exceptions
			e.printStackTrace();
		}
	}


}
