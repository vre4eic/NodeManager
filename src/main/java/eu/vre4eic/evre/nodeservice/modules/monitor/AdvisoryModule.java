/**
 * 
 */
package eu.vre4eic.evre.nodeservice.modules.monitor;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;

import org.apache.activemq.advisory.AdvisorySupport;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ActiveMQTopic;

import eu.vre4eic.evre.core.Common;
import eu.vre4eic.evre.nodeservice.modules.comm.CommModule;

/**
 * @author francesco
 *
 */
public class AdvisoryModule implements MessageListener {
	
	private static AdvisoryModule instance = null;
	private ActiveMQDestination destination;

	public AdvisoryModule(){

		Session session =CommModule.getInstance().getSession();
		Destination destination;
		ActiveMQTopic advisoryDestination;
		MessageConsumer consumer;
		try {
			destination = session.createTopic(Common.AUTH_CHANNEL);
			//advisoryDestination = AdvisorySupport.getProducerAdvisoryTopic(destination);
			advisoryDestination = AdvisorySupport.getFullAdvisoryTopic(destination);
			consumer = session.createConsumer(advisoryDestination);
			consumer.setMessageListener(this);
		} catch (JMSException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
	
	public static AdvisoryModule getInstance() {
		if(instance == null) {
	         instance = new AdvisoryModule();
	      }
	      return instance;
	      
	}

	@Override
	public void onMessage(Message message) {
		System.out.println("ADVISORY");
		System.out.println(message);
		// TODO Auto-generated method stub
		
	}


}
