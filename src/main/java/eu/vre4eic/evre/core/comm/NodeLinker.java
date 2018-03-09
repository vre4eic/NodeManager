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
package eu.vre4eic.evre.core.comm;

import java.util.Properties;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryOneTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.vre4eic.evre.nodeservice.Settings;
import eu.vre4eic.evre.nodeservice.nodemanager.ZKServer;

public class NodeLinker {
	
	private static Logger log = LoggerFactory.getLogger(NodeLinker.class.getClass());
	private static NodeLinker instance;

	

	private static Properties defaultSettings;
	private static Properties zkSettings;


	
	private NodeLinker(String nodeServiceURL) {

		loadRemoteProperties(nodeServiceURL);
	}

		
	public  static NodeLinker  init(String nodeServiceURL){
		if (instance == null)
			try {
				instance = new NodeLinker(nodeServiceURL);
			} catch (Exception e) {
				log.error(e.getMessage());
				e.printStackTrace();
			}
		return instance;
	}
	
	public  static NodeLinker  getInstance() {
		if (instance == null)
			log.error("NodeLinker not initialised");
		return instance;
	}
	
	private  static void loadRemoteProperties(String nodeServiceURL){

		if (defaultSettings == null)
			defaultSettings = Settings.getProperties();
		
		if (nodeServiceURL == null){
			nodeServiceURL = defaultSettings.getProperty(Settings.ZOOKEEPER_DEFAULT);
		}


		zkSettings = new Properties();
		
		CuratorFramework client = CuratorFrameworkFactory
				.newClient(nodeServiceURL,new RetryOneTime(1));
		client.start();
		try
		{
			saveZKProperty(Settings.VERSION_PATH,client);
			saveZKProperty(Settings.TOKEN_TIMEOUT_PATH,client);
			saveZKProperty(Settings.CODE_TIMEOUT_PATH,client);
			saveZKProperty(Settings.MESSAGE_BROKER_PATH,client);
			saveZKProperty(Settings.PROFILES_STORAGE,client);
			saveZKProperty(Settings.PROFILES_STORAGE_PORT,client);
			saveZKProperty(Settings.AAAI_LOGIN,client);
			saveZKProperty(Settings.AAAI_PWD,client);
		}
		
		finally
		{
			if (client != null)
				client.close();
		}
	}
	
	private static void saveZKProperty(String property_name, CuratorFramework client){
		String property_path = defaultSettings.getProperty(property_name);

		try {
			byte[] data = client.getData().forPath(property_path);
			if (data != null) 
				zkSettings.setProperty(property_path, new String(data));
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	public  String getEvreVersion() {
		String versionPath = defaultSettings.getProperty(Settings.VERSION_PATH);
		String version =  zkSettings.getProperty(versionPath);
		return version;
		
	}
	
	public  String getMessageBrokerURL() {
		String messageBrokerPath = defaultSettings.getProperty(Settings.MESSAGE_BROKER_PATH);
		String brokerURL =  zkSettings.getProperty(messageBrokerPath);
		return brokerURL;
		
	}
	
	public  String getProfileStorage() {
		String profilesStorage = defaultSettings.getProperty(Settings.PROFILES_STORAGE);
		String address =  zkSettings.getProperty(profilesStorage);
		return address;
	
	}
	
	public  int getProfileStoragePort() {
		String profilesStoragePort = defaultSettings.getProperty(Settings.PROFILES_STORAGE_PORT);
		String port =  zkSettings.getProperty(profilesStoragePort);
		return Integer.valueOf(port);
	
	}
	
	public  int getTokenTimeout() {
		String tokenTimeout = defaultSettings.getProperty(Settings.TOKEN_TIMEOUT_PATH);
		return Integer.valueOf(zkSettings.getProperty(tokenTimeout));	
	}
	
	public  int getCodeTimeout() {
		String codeTimeout = defaultSettings.getProperty(Settings.CODE_TIMEOUT_PATH);
		return Integer.valueOf(zkSettings.getProperty(codeTimeout));	
	}

	public  String getAAAIUser() {
		String user = defaultSettings.getProperty(Settings.AAAI_LOGIN_DEFAULT);
		return user;	
	}

	public  String getAAAIPwd() {
		String pwd = defaultSettings.getProperty(Settings.AAAI_PWD_DEFAULT);
		return pwd;	
	}

	
	public  Properties getProperties() throws Exception {
		return zkSettings;
		
	}

	
}
