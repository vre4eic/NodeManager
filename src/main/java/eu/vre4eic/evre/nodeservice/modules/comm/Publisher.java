/**
 * 
 */
package eu.vre4eic.evre.nodeservice.modules.comm;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.springframework.jms.annotation.EnableJms;

import eu.vre4eic.evre.core.Common;
import eu.vre4eic.evre.core.messages.AuthenticationMessage;

import eu.vre4eic.evre.nodeservice.modules.comm.CommModule;

/**
 * @author francesco
 *
 */

@EnableJms
public class Publisher  {

	private static Publisher instance;
	private  MessageProducer tokenDispatcher;
	private Session session;

	private Publisher() throws JMSException {
		session  = CommModule.getInstance().getSession();
		Destination destination = session.createTopic(Common.AUTH_CHANNEL);
		tokenDispatcher = session.createProducer(destination);

	}

	public static Publisher getInstance() {
		if(instance == null) {
			try {
				instance = new Publisher();
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return instance;

	}
	
	
	/* (non-Javadoc)
	 * @see eu.vre4eic.evre.nodeservice.comm.CommToolKit#publishAuthentication(eu.vre4eic.evre.core.messages.AuthenticationMessage)
	 */

	public void publishAuthentication(AuthenticationMessage m) throws JMSException {
		ObjectMessage om = session.createObjectMessage(m);
		tokenDispatcher.send(om);
	}




}
