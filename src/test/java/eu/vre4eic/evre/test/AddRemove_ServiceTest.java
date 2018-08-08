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
package eu.vre4eic.evre.test;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import eu.vre4eic.evre.core.Common.Topics;
import eu.vre4eic.evre.core.comm.MessageListener;
import eu.vre4eic.evre.core.comm.NodeLinker;
import eu.vre4eic.evre.core.comm.Subscriber;
import eu.vre4eic.evre.core.messages.ControlMessage;
import eu.vre4eic.evre.core.messages.LifeCycleMessage;
import eu.vre4eic.evre.nodeservice.modules.authentication.AuthModule;

public class AddRemove_ServiceTest {
	
	
	public static void main(String[] args)  {


		try {
//			Properties defaultSettings = Settings.getProperties();
//			String ZkServer = defaultSettings.getProperty(Settings.ZOOKEEPER_DEFAULT);
//			NodeLinker node = NodeLinker.init(ZkServer);
			
//			NodeLinker node = NodeLinker.init("v4e-lab.isti.cnr.it:2181");
			NodeLinker node = NodeLinker.init("localhost:2281");

			node.addService("pippo", "description", "http://entrypoit.it");
			TimeUnit.SECONDS.sleep(15);		
			node.removeService("pippo");

			while (true) {
				try {
					Thread.sleep(60000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
	
	



}
