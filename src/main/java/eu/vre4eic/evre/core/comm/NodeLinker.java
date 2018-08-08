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

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

import org.apache.curator.utils.CloseableUtils;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceProvider;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import eu.vre4eic.evre.core.Common;
import eu.vre4eic.evre.core.Common.ControlOperationType;
import eu.vre4eic.evre.core.Common.ResponseStatus;
import eu.vre4eic.evre.core.Common.Topics;
import eu.vre4eic.evre.core.messages.ControlMessage;
import eu.vre4eic.evre.core.messages.LifeCycleMessage;
import eu.vre4eic.evre.core.messages.impl.LifeCycleMessageImpl;
import eu.vre4eic.evre.nodeservice.Settings;
import eu.vre4eic.evre.nodeservice.Utils;



public class NodeLinker {

	private static Logger log = LoggerFactory.getLogger(NodeLinker.class.getClass());
	private static NodeLinker instance;

	private static Properties defaultSettings;
	private static Properties zkSettings;

	//private static final String PATH = "/discovery/services";
	static CuratorFramework client = null;
	static ServiceDiscovery<InstanceDetails> serviceDiscovery = null;
	static Map<String, ServiceProvider<InstanceDetails>> providers = Maps.newHashMap();
	
	private static List<EvreService> servers = Lists.newArrayList();
	Publisher<LifeCycleMessage> lifeCyclePublisher;

	private NodeLinker(String nodeServiceURL) {

		loadRemoteProperties(nodeServiceURL);
		lifeCyclePublisher = new Publisher<LifeCycleMessage>(getMessageBrokerURL(),Topics.CONTROL_Channel);

	}

	public static NodeLinker init(String nodeServiceURL) {
		if (instance == null)
			try {
				instance = new NodeLinker(nodeServiceURL);
			} catch (Exception e) {
				log.error(e.getMessage());
				e.printStackTrace();
			}
		return instance;
	}

	public static NodeLinker getInstance() {
		if (instance == null)
			log.error("NodeLinker not initialised");
		return instance;
	}

	// close the connection with then node master.

	public static void close() {

		for (ServiceProvider<InstanceDetails> cache : providers.values()) {
			CloseableUtils.closeQuietly(cache);
		}

		CloseableUtils.closeQuietly(serviceDiscovery);
		if (client != null)
			CloseableUtils.closeQuietly(client);
	}

	
	private static void loadCredentials(){
		if (defaultSettings == null)
			defaultSettings = Settings.getProperties();
		
// Old store configuration
//		String CredentialPath = defaultSettings.getProperty(Settings.CREDENTIALS);
//		Properties credentials = Utils.getProperties(CredentialPath);
//		String ksLocation = credentials.getProperty(Settings.KS_LOCATION);
//		String tsLocation = credentials.getProperty(Settings.TS_LOCATION);
//		String ksPwd = credentials.getProperty(Settings.KS_PWD);
//		String tsPwd = credentials.getProperty(Settings.TS_PWD);

		// properties to use SSL 
		System.setProperty("zookeeper.clientCnxnSocket", "org.apache.zookeeper.ClientCnxnSocketNetty");
		System.setProperty("zookeeper.client.secure", "true");
		
//		System.setProperty("zookeeper.ssl.keyStore.location", "C:\\Users\\francesco\\git\\NodeService-CP\\clientKS");
		
		// properties to access KeyStore e TrustStore del client
		String userHome = System.getProperty("user.home");
		System.setProperty("zookeeper.ssl.keyStore.location", userHome + "\\.keystore");
		System.setProperty("zookeeper.ssl.keyStore.password","clientKS");
		String javaHome = System.getProperty("java.home");
		System.setProperty("zookeeper.ssl.trustStore.location",javaHome + "\\lib\\security\\cacerts");
		System.setProperty("zookeeper.ssl.trustStore.password","changeit");

	}
	
	private static void loadRemoteProperties(String nodeServiceURL) {

		if (defaultSettings == null)
			defaultSettings = Settings.getProperties();

		if (nodeServiceURL == null) {
			nodeServiceURL = defaultSettings.getProperty(Settings.ZOOKEEPER_DEFAULT);
		}

		zkSettings = new Properties();

		// client = CuratorFrameworkFactory.newClient(nodeServiceURL,new
		// RetryOneTime(1));
		loadCredentials();
		client = CuratorFrameworkFactory.newClient(nodeServiceURL, new ExponentialBackoffRetry(1000, 3));
		client.start();
		try {
			saveZKProperty(Settings.VERSION_PATH, client);
			saveZKProperty(Settings.TOKEN_TIMEOUT_PATH, client);
			saveZKProperty(Settings.TOKEN_SECRET_PATH, client);
			saveZKProperty(Settings.CODE_TIMEOUT_PATH, client);
			saveZKProperty(Settings.MESSAGE_BROKER_PATH, client);
			saveZKProperty(Settings.PROFILES_STORAGE, client);
			saveZKProperty(Settings.PROFILES_STORAGE_PORT, client);
			saveZKProperty(Settings.AAAI_LOGIN, client);
			saveZKProperty(Settings.AAAI_PWD, client);
		}

		finally {
			JsonInstanceSerializer<InstanceDetails> serializer = new JsonInstanceSerializer<InstanceDetails>(
					InstanceDetails.class);
			serviceDiscovery = ServiceDiscoveryBuilder.builder(InstanceDetails.class).client(client).basePath(Common.EvreServicePATH)
					.serializer(serializer).build();
			try {
				serviceDiscovery.start();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// if (client != null)
			// client.close();
		}
	}

	private static void saveZKProperty(String property_name, CuratorFramework client) {
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

	public String getEvreVersion() {
		String versionPath = defaultSettings.getProperty(Settings.VERSION_PATH);
		String version = zkSettings.getProperty(versionPath);
		return version;

	}

	public String getMessageBrokerURL() {
		String messageBrokerPath = defaultSettings.getProperty(Settings.MESSAGE_BROKER_PATH);
		String brokerURL = zkSettings.getProperty(messageBrokerPath);
		return brokerURL;

	}

	public String getProfileStorage() {
		String profilesStorage = defaultSettings.getProperty(Settings.PROFILES_STORAGE);
		String address = zkSettings.getProperty(profilesStorage);
		return address;

	}

	public int getProfileStoragePort() {
		String profilesStoragePort = defaultSettings.getProperty(Settings.PROFILES_STORAGE_PORT);
		String port = zkSettings.getProperty(profilesStoragePort);
		return Integer.valueOf(port);

	}

	public int getTokenTimeout() {
		String tokenTimeout = defaultSettings.getProperty(Settings.TOKEN_TIMEOUT_PATH);
		return Integer.valueOf(zkSettings.getProperty(tokenTimeout));
	}

	public String getTokenSecret() {
		String tokenSecret = defaultSettings.getProperty(Settings.TOKEN_SECRET_PATH);
		return zkSettings.getProperty(tokenSecret);
	}

	public int getCodeTimeout() {
		String codeTimeout = defaultSettings.getProperty(Settings.CODE_TIMEOUT_PATH);
		return Integer.valueOf(zkSettings.getProperty(codeTimeout));
	}

	public String getAAAIUser() {
		String user = defaultSettings.getProperty(Settings.AAAI_LOGIN_DEFAULT);
		return user;
	}

	public String getAAAIPwd() {
		String pwd = defaultSettings.getProperty(Settings.AAAI_PWD_DEFAULT);
		return pwd;
	}

	public Properties getProperties() throws Exception {
		return zkSettings;

	}

	// Cesare
	public boolean addService(String name, String description, String entrypoint) {

		String serviceName = name;
		EvreService server;
		try {
			server = new EvreService(client, Common.EvreServicePATH, serviceName, description.toString(), entrypoint);
			servers.add(server);
			server.start();
		} catch (Exception e) {
			log.error("Cannot publish the service: "+serviceName);
			e.printStackTrace();
			return false;
		}

		System.out.println(serviceName + " added");
		
	    // lifecycle publishing
		LifeCycleMessage lcm = new LifeCycleMessageImpl("Life Cycle Message", ResponseStatus.SUCCEED,ControlOperationType.SERVICE_STARTED)
									.setServiceName(name)
									.setEntryPoint(entrypoint);
		lifeCyclePublisher.publish(lcm);
		return true;
	}

	public boolean removeService(String name) {

		String serviceName = name;
		EvreService server = Iterables.find
			    (
			        servers,
			        new Predicate<EvreService>()
			        {
			            @Override
			            public boolean apply(EvreService server)
			            {
			                return server.getThisInstance().getName().endsWith(serviceName);
			            }
			        },
			        null
			    );
			    if ( server == null )
			    {
			    	log.error("No servers found named: " + serviceName);
			        System.err.println("No servers found named: " + serviceName);
			        return false;
			    }
			    servers.remove(server);
			    CloseableUtils.closeQuietly(server);
			    System.out.println("Removed a random instance of: " + serviceName);
			    
			    // lifecycle publishing
			    String ep = server.getThisInstance().getPayload().getEntrypoint();
				LifeCycleMessage lcm = new LifeCycleMessageImpl("Life Cycle Message", ResponseStatus.SUCCEED,ControlOperationType.SERVICE_STOPPED)
						.setServiceName(name)
						.setEntryPoint(ep);
				lifeCyclePublisher.publish(lcm);

			    return true;
	}
}
