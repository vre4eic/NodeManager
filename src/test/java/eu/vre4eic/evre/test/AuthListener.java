package eu.vre4eic.evre.test;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import eu.vre4eic.evre.core.messages.AuthenticationMessage;
import eu.vre4eic.evre.core.messages.impl.AuthenticationMessageImpl;

public class AuthListener implements MessageListener{
	
	@Override
	public void onMessage(Message message) {
		try {
//			MapMessage map = (MapMessage)message;
//			String token = map.getString("token");
//			String ttl = map.getString("ttl");
//			System.out.println("Received Token " + token + " with ttl " + ttl);

	        if (message instanceof ObjectMessage) {
	            try {
	            	AuthenticationMessageImpl am = (AuthenticationMessageImpl) ((ObjectMessage) message).getObject();
	            	System.out.println("##### authentication message arrived #####");
	            	System.out.println("token: " + am.getToken());
	            	System.out.println("time limit: "+ am.getTimeLimit());
	            }
	            catch (JMSException ex) {
	                throw new RuntimeException(ex);
	            }
	        }
	        else {
	            throw new IllegalArgumentException("Message must be of type TextMessage");
	        }
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
