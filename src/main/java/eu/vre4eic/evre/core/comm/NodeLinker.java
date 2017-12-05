package eu.vre4eic.evre.core.comm;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryOneTime;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.vre4eic.evre.nodeservice.Settings;
import eu.vre4eic.evre.nodeservice.Utils;

public class NodeLinker {
	
	private static String ZOOKEEPER_DEFAULT = "ZooKeeper_default";
	
	private static Logger log = LoggerFactory.getLogger(NodeLinker.class.getClass());
	private static NodeLinker instance;

	
	private static Properties defaultSettings;
	private static Properties zkSettings;


	private NodeLinker() {
	}

		
	public  static Properties getRemoteProperties() throws Exception{
		if (defaultSettings == null)
			defaultSettings = Settings.getProperties();

		if (zkSettings != null)
			return zkSettings;

		zkSettings = new Properties();

		String ZKServer = defaultSettings.getProperty(Settings.ZOOKEEPER_DEFAULT);
		CuratorFramework client = CuratorFrameworkFactory
				.newClient(ZKServer,new RetryOneTime(1));
		client.start();
		try
		{
			loadZKProperty(Settings.VERSION_PATH,client);
			loadZKProperty(Settings.TOKEN_TIMEOUT_PATH,client);
			loadZKProperty(Settings.CODE_TIMEOUT_PATH,client);
			loadZKProperty(Settings.MESSAGE_BROKER_PATH,client);	
			loadZKProperty(Settings.PROFILES_STORAGE,client);
			loadZKProperty(Settings.PROFILES_STORAGE_PORT,client);
		}
		
		finally
		{
			if (client != null)
				client.close();
		}
		return zkSettings;

	}
	
	private static void loadZKProperty(String property_name, CuratorFramework client) throws Exception{
		String property_path = defaultSettings.getProperty(property_name);
		byte[] data = client.getData().forPath(property_path);
		if (data != null) 
			zkSettings.setProperty(property_path, new String(data));
		
	}
	
	
}
