package eu.vre4eic.evre.test;

import javax.jms.JMSException;

import eu.vre4eic.evre.core.Common.MetadataOperationType;
import eu.vre4eic.evre.core.Common.ResponseStatus;
import eu.vre4eic.evre.core.Common.Topics;
import eu.vre4eic.evre.core.messages.MetadataMessage;
import eu.vre4eic.evre.core.messages.impl.MetadataMessageImpl;
import eu.vre4eic.evre.core.comm.Publisher;
import eu.vre4eic.evre.core.comm.PublisherFactory;;

public class MetadataTest {
	
	public static void main(String[] args)  {
		
//		System.out.println(Topics.AUTH_Channel);
		Publisher<MetadataMessage> mdp =  PublisherFactory.getMetatdaPublisher();
		MetadataMessage mdm = new MetadataMessageImpl("pluto message", ResponseStatus.SUCCEED)
				.setToken("pluto")
				.setOperation(MetadataOperationType.QUERY);

		try {
			mdp.publish(mdm);
			Thread.sleep(2000);
			mdm.setOperation(MetadataOperationType.INSERT);
			mdp.publish(mdm);
			Thread.sleep(2000);
			mdm.setOperation(MetadataOperationType.UPDATE);
			mdp.publish(mdm);
			
			Thread.sleep(3000);
			mdm.setToken("pippo").setOperation(MetadataOperationType.QUERY).setMessage("pippo message");
			mdp.publish(mdm);			
			Thread.sleep(2000);
			mdm.setOperation(MetadataOperationType.INSERT);
			mdp.publish(mdm);
			Thread.sleep(2000);
			mdm.setOperation(MetadataOperationType.UPDATE);
			mdp.publish(mdm);
			
			
			
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		for (Topics topic : Topics.values()) {
			System.out.println(topic.name());
		}

	}
	

}
