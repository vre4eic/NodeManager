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
package eu.vre4eic.evre.nodeservice.nodemanager.impl;

import java.util.Collection;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import eu.vre4eic.evre.core.Common;
import eu.vre4eic.evre.core.comm.NodeLinker;
import eu.vre4eic.evre.nodeservice.Settings;
import eu.vre4eic.evre.nodeservice.nodemanager.ZKServer;
import eu.vre4eic.evre.nodeservice.usermanager.impl.UserManagerImpl;

@Configuration
public class NodeManagerImpl {

	private Logger logger = LoggerFactory.getLogger(NodeManagerImpl.class.getClass());
	
	public NodeManagerImpl() {
		super();
		/*ZKServer.init();
		Properties defaultSettings = Settings.getProperties();
		String ZkServer = defaultSettings.getProperty(Settings.ZOOKEEPER_DEFAULT);
		NodeLinker node = NodeLinker.init(ZkServer);*/
		logger.info("NodeController initialized");

	}
	public Collection <String> getEvreBlocks(){
		return ZKServer.listBlockNames();
	}
}