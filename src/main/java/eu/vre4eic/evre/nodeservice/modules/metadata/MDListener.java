package eu.vre4eic.evre.nodeservice.modules.metadata;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.vre4eic.evre.core.messages.MetadataMessage;

/**
 * 
 * Class used to receive asynchronous messages related to users authenticated by  the system.
 * The authentication message can represent  Login or Logout operations and contains token which must be used by users for each service invocation.
 * @author francesco
 *
 */
public class MDListener implements MessageListener{
	
	private MDModule module;
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	public  MDListener(MDModule module) {
		this.module = module;
	}
	
	/* (non-Javadoc)
	 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	 */
	@Override
	public void onMessage(Message message) {
		try {

	        if (message instanceof ObjectMessage) {
	            try {
	            	MetadataMessage am = (MetadataMessage) ((ObjectMessage) message).getObject();
	            	log.info("##### MetatdataMessage message arrived #####");
	            	module.addMessage(am);           	
	            	
	            }
	            catch (JMSException ex) {
	                throw new RuntimeException(ex);
	            }
	        }
	        else {
	            throw new IllegalArgumentException("Message must be of type ObjectMessage serializing MetatdataMessage");
	        }
		
		} catch (Exception e) {
			// MessageListener cannot throw exceptions
			e.printStackTrace();
		}
	}


}
