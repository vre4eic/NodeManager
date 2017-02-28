package eu.vre4eic.evre.nodeservice.modules.authentication;

import java.time.LocalDateTime;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.vre4eic.evre.core.messages.AuthenticationMessage;

public class AuthListener implements MessageListener{
	
	private AuthModule module;
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	public  AuthListener(AuthModule authModule) {
		this.module = authModule;
	}
	
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
			e.printStackTrace();
		}
	}


}
