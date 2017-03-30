package eu.vre4eic.evre.nodeservice.modules.authentication;

import java.time.LocalDateTime;

import javax.jms.JMSException;
import javax.jms.Message;

import javax.jms.ObjectMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.vre4eic.evre.core.messages.AuthenticationMessage;
import eu.vre4eic.evre.nodeservice.modules.comm.MessageListener;

/**
 * 
 * Class used to receive asynchronous messages related to users authenticated by  the system.
 * The authentication message can represent  Login or Logout operations and contains token which must be used by users for each service invocation.
 * @author francesco
 *
 */
public class AuthListener implements MessageListener<AuthenticationMessage>{
	
	private AuthModule module;
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	public  AuthListener(AuthModule authModule) {
		this.module = authModule;
	}
	
	/* (non-Javadoc)
	 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	 */
	@Override
	public void onMessage(AuthenticationMessage message) {

		log.info("##### authentication message arrived #####");
		//log.info("token: " + am.getToken());
		//log.info("time limit: "+ am.getTimeLimit());

		if (message.getTimeLimit().equals(LocalDateTime.MIN))
			module.cancelToken(message);
		else
			module.registerToken(message);           	


	}



}
