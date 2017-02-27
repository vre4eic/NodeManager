package eu.vre4eic.evre.core.messages;



/**
 * This interface defines the message published when a topic is subscribed
 * @author Cesare
 *
 */
public interface SubscriptionMessage extends Message {

	/**
	 * Gets the URL of the service managing the Message Broker
	 * @return String - the URL of the Message Broker
	 */
	public String getMessageBrokerURL();
	
	/**
	 * Sets the URL of the service managing the Message Broker
	 *
	 * @param id String - the Message Broker URL
	 */
	public void setMessageBrokerURL(String id);

}
