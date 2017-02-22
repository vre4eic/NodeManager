package eu.vre4eic.evre.nodeservice.comm;

import javax.jms.JMSException;

import eu.vre4eic.evre.core.Common.ResponseStatus;
import eu.vre4eic.evre.core.messages.AuthenticationMessage;

public interface CommToolKit {
	
	void publishAuthentication(AuthenticationMessage m) throws JMSException;
	
}