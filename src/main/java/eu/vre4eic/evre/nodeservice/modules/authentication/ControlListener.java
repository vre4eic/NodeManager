/*******************************************************************************
 * Copyright (c) 2018 VRE4EIC Consortium
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package eu.vre4eic.evre.nodeservice.modules.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.vre4eic.evre.core.messages.ControlMessage;
import eu.vre4eic.evre.core.comm.MessageListener;

/**
 * 
 * Class used to receive asynchronous messages related to users authenticated by  the system.
 * The authentication message can represent  Login or Logout operations and contains token which must be used by users for each service invocation.
 * @author francesco
 *
 */
public class ControlListener implements MessageListener<ControlMessage>{
	
	private AuthModule module;
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	public  ControlListener(AuthModule authModule) {
		this.module = authModule;
	}
	
	/* (non-Javadoc)
	 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	 */
	@Override
	public void onMessage(ControlMessage message) {

		log.info("##### Control message arrived #####");
		//log.info("token: " + am.getToken());
		//log.info("time limit: "+ am.getTimeLimit());
		switch (message.getOperationType()) {
		case PING:
			System.out.println("########### I'm alive #########  .... pong");			
//			break;
		case PRINT_STATUS:
			module.listToken();			
			break;

		default:
			break;
		}

	}



}
