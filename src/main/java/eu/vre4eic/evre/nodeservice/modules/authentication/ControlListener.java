package eu.vre4eic.evre.nodeservice.modules.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.vre4eic.evre.core.messages.ControlMessage;
import eu.vre4eic.evre.core.comm.MessageListener;

/**
 * 
 * Class used to receive asynchronous messages related to users authenticated by  the system.
 * The authentication message can represent  Login or Logout operations and contains token which must be used by users for each service invocation.
 * @author francesco
 *
 */
public class ControlListener implements MessageListener<ControlMessage>{
	
	private AuthModule module;
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	public  ControlListener(AuthModule authModule) {
		this.module = authModule;
	}
	
	/* (non-Javadoc)
	 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	 */
	@Override
	public void onMessage(ControlMessage message) {

		log.info("##### Control message arrived #####");
		//log.info("token: " + am.getToken());
		//log.info("time limit: "+ am.getTimeLimit());
		switch (message.getOperationType()) {
		case PING:
			System.out.println("########### I'm alive #########  .... pong");			
//			break;
		case PRINT_STATUS:
			module.listToken();			
			break;

		default:
			break;
		}

	}



}
