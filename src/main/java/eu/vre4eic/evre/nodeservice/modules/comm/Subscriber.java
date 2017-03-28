/**
 * 
 */
package eu.vre4eic.evre.nodeservice.modules.comm;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.EnableJms;

import eu.vre4eic.evre.core.Common;
import eu.vre4eic.evre.core.Common.Topics;
import eu.vre4eic.evre.core.messages.Message;
import eu.vre4eic.evre.nodeservice.modules.authentication.AuthListener;
import eu.vre4eic.evre.nodeservice.modules.authentication.AuthModule;
import eu.vre4eic.evre.nodeservice.modules.comm.CommModule;

/**
 * @author francesco
 *
 */

@EnableJms
public class Subscriber {

	private static Logger log = LoggerFactory.getLogger(Subscriber.class);

	private  MessageConsumer messageConsumer;
	private Destination destination;
	private Session session;


	public Subscriber(Topics topic) {
		try {
			session  = CommModule.getInstance().getSession();
			destination = session.createTopic(topic+"?consumer.retroactive=true");
			messageConsumer = session.createConsumer(destination);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	public void setListener(MessageListener listener)  {
		try {
			messageConsumer.setMessageListener(listener);
			log.info(" subscribed to topic:: " + destination.toString());
		} catch (JMSException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
	}




}
