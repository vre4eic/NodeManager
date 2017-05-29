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
	//private Vector<String > authTable;
	
	public  TGBotMFAListener(UserDaoTest authModule) {
		this.module = authModule;
		
	}
	
	
	@Override
	public void onMessage(MultiFactorMessage message) {

		log.info("##### MultiFactorMessage message arrived #####");

		module.updateMFMQueue(new Integer(message.getAuthId()), message.getCode());     
			

	}



}
