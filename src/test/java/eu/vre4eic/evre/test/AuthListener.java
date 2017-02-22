package eu.vre4eic.evre.test;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

public class AuthListener implements MessageListener{
	
	@Override
	public void onMessage(Message message) {
		try {
			MapMessage map = (MapMessage)message;
			String token = map.getString("token");
			String ttl = map.getString("ttl");
			System.out.println("Received Token " + token + " with ttl " + ttl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
