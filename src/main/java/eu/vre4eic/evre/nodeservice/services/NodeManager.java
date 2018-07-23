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
package eu.vre4eic.evre.nodeservice.services;



import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eu.vre4eic.evre.core.Common;
import eu.vre4eic.evre.core.comm.NodeLinker;
import eu.vre4eic.evre.core.messages.Message;
import eu.vre4eic.evre.core.messages.impl.ControlMessageImpl;
import eu.vre4eic.evre.nodeservice.Settings;
import eu.vre4eic.evre.nodeservice.modules.authentication.AuthModule;
import eu.vre4eic.evre.nodeservice.nodemanager.ZKServer;
import eu.vre4eic.evre.nodeservice.nodemanager.impl.NodeManagerImpl;
import eu.vre4eic.evre.nodeservice.usermanager.impl.UserManagerImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


/**
 * This class contains methods helping e-VRE services to coordinate their activitiess. 
 * @author Cesare
 *
 */

@RestController
@Api(value = "Node management")

public class NodeManager {

	NodeLinker node;
	
	@Autowired
	private NodeManagerImpl nodeManager;
	private AuthModule authModule;
	//Properties property = new Properties();
	public NodeManager()  {
		super();
		ZKServer.init();
		Properties defaultSettings = Settings.getProperties();
		
		String ZkServer = defaultSettings.getProperty(Settings.ZOOKEEPER_DEFAULT);
		node = NodeLinker.init(ZkServer);
			
		String messageBrokerURL =  node.getMessageBrokerURL();
		authModule = AuthModule.getInstance(messageBrokerURL);
		
		/*InputStream in;
		
		try {
			in = this.getClass().getClassLoader()
					.getResourceAsStream("Nodeservice.properties");
			property.load(in);
			in.close();
		} catch (FileNotFoundException e) {
			
		} catch (IOException e) {
			
		}*/
	}
		
		@ApiOperation(value = "returns info about an e-VRE building block in the node", 
				notes = "A user with a valid Admin token can invoke this web service to get info about a building block running int the e-VRE Node", 
				response = Message.class)

		@RequestMapping(value="/node/getevreblock", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
		public Message getEvreComponentID(@RequestParam(value="token") String token, @RequestParam(value="id") String id) {

			return null;
		}
		
		@ApiOperation(value = "removes an e-VRE building block from the current e-VRE node", 
				notes = "A user with a valid Admin token can invoke this web service to remove an e-VRE building block active in the e-VRE Node", 
				response = Message.class)

		@RequestMapping(value="/node/removeevreblock", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
		public Message removeEvreComponentID(@RequestParam(value="token") String token, @RequestParam(value="id") String id) {

			return null;
		}
		
		@ApiOperation(value = "returns the list of running e-VRE building block in the current e-VRE node", 
				notes = "A user with a valid Admin token can invoke this web service to get the list e-VRE building block active in the e-VRE Node", 
				response = Message.class)

		@RequestMapping(value="/node/getevreblocks", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
		public Message listEvreNodeComponents(@RequestParam(value="token") String token) {

			if (authModule.checkToken(token, Common.UserRole.ADMIN)){
				nodeManager.getEvreBlocks();
				return (new ControlMessageImpl("Test mode on", Common.ResponseStatus.WARNING));
			}
			
			return (new ControlMessageImpl("Error, ivalid user", Common.ResponseStatus.FAILED));
		}

}
