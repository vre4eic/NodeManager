package eu.vre4eic.evre.test;

import javax.jms.JMSException;

import eu.vre4eic.evre.core.Common.MetadataOperationType;
import eu.vre4eic.evre.core.Common.ResponseStatus;
import eu.vre4eic.evre.core.Common.Topics;
import eu.vre4eic.evre.core.messages.MetadataMessage;
import eu.vre4eic.evre.core.messages.impl.MetadataMessageImpl;
import eu.vre4eic.evre.core.comm.Publisher;
import eu.vre4eic.evre.core.comm.PublisherFactory;;

public class MetadataMessagePublishTest {
	
	public static void main(String[] args)  {
		
		//get the publisher for metadata messages
		Publisher<MetadataMessage> mdp =  PublisherFactory.getMetatdaPublisher();
		
		// create the metadatamessage for a query operation
		MetadataMessage mdm = new MetadataMessageImpl(" description", ResponseStatus.SUCCEED)
				.setToken("tokenid")
				.setOperation(MetadataOperationType.QUERY);

		try {
			
			//publish the message
			
			mdp.publish(mdm);
			
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
		} 
		System.exit(0);

	}
	

}
