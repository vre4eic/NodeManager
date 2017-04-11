package eu.vre4eic.evre.util;


import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.vre4eic.evre.UserDaoTest;
import eu.vre4eic.evre.core.messages.MultiFactorMessage;
import eu.vre4eic.evre.core.comm.MessageListener;



public class TGBotMFAListener implements MessageListener<MultiFactorMessage>{
	
	private UserDaoTest module;
	private Logger log = LoggerFactory.getLogger(this.getClass());
	private Vector<String > authTable;
	
	public  TGBotMFAListener(UserDaoTest authModule) {
		this.module = authModule;
		authTable=new Vector<String>();
	}
	
	/* (non-Javadoc)
	 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	 */
	@Override
	public void onMessage(MultiFactorMessage message) {

		log.info("##### authentication message arrived #####");

			if ( !authTable.contains(message.getUserId())){
				authTable.add(message.getUserId());
				module.updateMFMQueue(new Integer(message.getAuthId()), message.getCode());     
			}

	}



}
