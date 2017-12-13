package eu.vre4eic.evre.nodeservice.nodemanager;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryOneTime;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.server.ServerConfig;
import org.apache.zookeeper.server.ZooKeeperServerMain;
import org.apache.zookeeper.server.admin.AdminServer.AdminServerException;
import org.apache.zookeeper.server.quorum.QuorumPeerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.vre4eic.evre.nodeservice.Settings;
import eu.vre4eic.evre.nodeservice.Utils;

public class ZKServer {


	private static Thread zkService;
	
	public static void  init(){
		if (zkService == null) // Double checked locking pattern
			synchronized (ZKServer.class){
				if (zkService == null) {
					try {
					startService(2181);
					init_eVRE_Env();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			
			} // end synchronized
		
	}


	private static void startService(int clientPort) throws Exception {
		final Logger log = LoggerFactory.getLogger(ZKServer.class);

		try {
			Properties properties = new Properties();
			File file = new File(System.getProperty("user.dir")+ File.separator + "ZKData");
			file.deleteOnExit();
			properties.setProperty("dataDir", file.getAbsolutePath());
			properties.setProperty("clientPort", String.valueOf(clientPort));

			QuorumPeerConfig quorumPeerConfig = new QuorumPeerConfig();
			quorumPeerConfig.parseProperties(properties);
			final ServerConfig configuration = new ServerConfig();
			configuration.readFrom(quorumPeerConfig);


			final ZooKeeperServerMain server = new ZooKeeperServerMain();
			zkService = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						server.runFromConfig(configuration);
					} catch (IOException e) {
						log.error("coordinator start failure", e);
					} catch (AdminServerException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			zkService.setDaemon(true);
			zkService.start();
		}
		catch (Exception e) {
				log.error("Exception running embedded ZooKeeper", e);
		}

	}

	public static void stopService() {
		if  (zkService != null)
			zkService.interrupt();
	}


	private static void init_eVRE_Env() throws Exception {
		CuratorFramework client = CuratorFrameworkFactory
									.newClient("localhost:2181",new RetryOneTime(1));
        client.start();


		try {
			Properties settings = Settings.getProperties();
			String versionPath = settings.getProperty(Settings.VERSION_PATH);
			String versionValue = settings.getProperty(Settings.VERSION_DEFAULT);
			createEntry(versionPath, versionValue, client);

			String tokenTimeoutPath = settings.getProperty(Settings.TOKEN_TIMEOUT_PATH);
			String tokenTimeoutValue = settings.getProperty(Settings.TOKEN_TIMEOUT_DEFAULT);
			createEntry(tokenTimeoutPath, tokenTimeoutValue, client);

			String codeTimeoutPath = settings.getProperty(Settings.CODE_TIMEOUT_PATH);
			String codeTimeoutValue = settings.getProperty(Settings.CODE_TIMEOUT_DEFAULT);
			createEntry(codeTimeoutPath, codeTimeoutValue, client);

			String messageBrokerPath = settings.getProperty(Settings.MESSAGE_BROKER_PATH);
			String messageBrokerValue = settings.getProperty(Settings.MESSAGE_BROKER_DEFAULT);
			createEntry(messageBrokerPath, messageBrokerValue, client);
			
			String profileStoragePath = settings.getProperty(Settings.PROFILES_STORAGE);
			String profileStorageValue = settings.getProperty(Settings.PROFILES_STORAGE_DEFAULT);
			createEntry(profileStoragePath, profileStorageValue, client);
			
			String profileStoragePort = settings.getProperty(Settings.PROFILES_STORAGE_PORT);
			String profileStoragePortValue = settings.getProperty(Settings.PROFILES_STORAGE_PORT_DEFAULT);
			createEntry(profileStoragePort, profileStoragePortValue, client);
			

		}
			
		finally  {
			client.close();
		}

	}
	
	
	private static void createEntry(String path, String value,CuratorFramework client){
		Stat exists;
		try {
			exists = client.checkExists().forPath(path);
			if (exists == null) {
        		client.create()
        				.creatingParentContainersIfNeeded()
        				.forPath(path, value.getBytes());
        		// log
				System.out.println("## Created " +path + "::"+  value);
			}
			else {
				byte[] data = client.getData().forPath(path);
				if (data != null) 
	        		// log
					System.out.println("## Existing " + path + "::"+  new String(data));
			}
				;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 
		
	
	
	public static void main(String[] args) {
		// to test correct initialization in concurrent threads
		
		Thread concurrentInit1 = new Thread(new Runnable() {
			@Override
			public void run() {
				ZKServer.init();
			}
		}, "Init1");

		Thread concurrentInit2 = new Thread(new Runnable() {
			@Override
			public void run() {
					ZKServer.init();
			}
		}, "Init2");

		concurrentInit1.start();
		concurrentInit2.start();
		
		while (true) {
			try {
				Thread.sleep(60000);
			} catch (InterruptedException e) {

				ZKServer.stopService();
			}

		}

	}

}
