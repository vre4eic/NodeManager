package eu.vre4eic.evre.core.comm;

import eu.vre4eic.evre.core.messages.Message;

public interface MessageListener<T extends Message> {
	
	public void onMessage(T message);

}
