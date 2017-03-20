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
import org.apache.activemq.command.ActiveMQMessage;
import org.apache.activemq.command.CommandTypes;
import org.apache.activemq.command.ConsumerId;
import org.apache.activemq.command.ConsumerInfo;
import org.apache.activemq.command.DataStructure;
import org.apache.activemq.command.RemoveInfo;

import eu.vre4eic.evre.core.Common;
import eu.vre4eic.evre.nodeservice.modules.comm.CommModule;

/**
 * @author francesco
 *
 */
public class AdvisoryModule implements MessageListener {
	
	private static AdvisoryModule instance = null;
	public AdvisoryModule(){

		Session session =CommModule.getInstance().getSession();
		try {			
			ActiveMQDestination destination = (ActiveMQDestination)session.createTopic(Common.AUTH_CHANNEL);

			Destination consumerTopic = AdvisorySupport.getConsumerAdvisoryTopic(destination);
			System.out.println("Subscribing to advisory " + consumerTopic);
			MessageConsumer consumerAdvisory = session.createConsumer(consumerTopic);
			consumerAdvisory.setMessageListener(this);

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
		System.out.println("############ ADVISORY #############");
//		System.out.println(message);
		ActiveMQMessage msg = (ActiveMQMessage) message;
		DataStructure ds = msg.getDataStructure();
		if (ds != null) {
			switch (ds.getDataStructureType()) {
			case CommandTypes.CONSUMER_INFO:
				ConsumerInfo consumerInfo = (ConsumerInfo) ds;
				System.out.println("Consumer '" + consumerInfo.getConsumerId()
						+ "' subscribed to '" + consumerInfo.getDestination()
						+ "'");
				break;
			case CommandTypes.REMOVE_INFO:
				RemoveInfo removeInfo = (RemoveInfo) ds;
				ConsumerId consumerId = ((ConsumerId) removeInfo.getObjectId());
				System.out
						.println("Consumer '" + consumerId + "' unsubscribed");
				break;
			default:
				System.out.println("Unkown data structure type");
			}
		} else {
			System.out.println("No data structure provided");
		}
	}		
	}


