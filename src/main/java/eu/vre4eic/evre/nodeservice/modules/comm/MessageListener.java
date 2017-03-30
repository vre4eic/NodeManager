package eu.vre4eic.evre.nodeservice.modules.comm;

import eu.vre4eic.evre.core.messages.Message;

public interface MessageListener<T extends Message> {
	
	public void onMessage(T message);

}
