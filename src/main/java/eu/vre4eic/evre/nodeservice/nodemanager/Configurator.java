package eu.vre4eic.evre.nodeservice.nodemanager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.zookeeper.AsyncCallback.ChildrenCallback;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.CuratorFrameworkFactory.Builder;
import org.apache.curator.retry.RetryOneTime;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.KeeperException.Code;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;

import eu.vre4eic.evre.nodeservice.Settings;

public class Configurator implements Watcher  {
	ZooKeeper zk ;
	ChildrenCallback workersGetChildrenCallback;
	private CuratorFramework client;
	private Properties settings = Settings.getProperties();
	static List<ACL> aclList;
	private String env;
	
	public Configurator(String env, String nodeServiceURL) throws IOException {
		this.env=  env;
		// Librerie di zookeper
		//zk = new ZooKeeper("146.48.81.196:2181", 15,this);
		//lista.add(new ACL(ZooDefs.Perms.CREATE |ZooDefs.Perms.ADMIN , new Id("ip","146.48.81.196")));
		
		
		// Librerie curator 
		//client = CuratorFrameworkFactory.newClient(nodeServiceURL,new RetryOneTime(1));

		Builder builder = CuratorFrameworkFactory.builder();
		builder.connectString(nodeServiceURL);
		builder.retryPolicy(new RetryOneTime(1));
		builder.authorization("digest", "NodeService:N0d3S3rv1ce18".getBytes());
//		builder.authorization("digest", "MessageBroker:broker".getBytes());

		client = builder.build();
		client.start();
//		createNodeServicePaths();

		createNodeServicePaths();

	}
	
	@Override
	public void process(WatchedEvent event) {
		System.out.println(event.toString());
		
	}

	private List<ACL> getNodeServiceACL(){
		if (aclList == null) {
			// libreria zookeeper
			//zk.addAuthInfo("digest", "NodeService:N0d3S3rv1c318".getBytes());
			aclList = new ArrayList<ACL>();
			aclList.add(new ACL(ZooDefs.Perms.ALL, new Id("auth","")));
		}
		return aclList;
	}
	
	private void createNodeServicePaths(){
//		/evre/<env>/NodeService
//		/evre/<env>/NodeService/version [int.int.int]
//		/evre/<env>/NodeService/config
//		/evre/<env>/NodeService/config/authentication
//		/evre/<env>/NodeService/config/authentication/TokenTimeout [int]
//		/evre/<env>/NodeService/config/authentication/CodeTimeout [int]
//		/evre/<env>/NodeService/config/authentication/secret [string]
//		/evre/<env>/NodeService/MessageBroker/URL [url:port]
//		/evre/<env>/NodeService/ProfileStorage/URL [url:port]
//		/evre/<env>/NodeService/ProfileStorage/credentials [id:pwd]
//		/evre/<env>/NodeService/AAAI/URL [url:port]
//		/evre/<env>/NodeService/AAAI/credentials [id:pwd]
		
		try {
			
			String container = env + Settings.NODE_SERVICE;
			createNode(container,Settings.VERSION_PATH,Settings.VERSION_DEFAULT);
			createNode(container,Settings.TOKEN_TIMEOUT_PATH,Settings.TOKEN_TIMEOUT_DEFAULT);
			createNode(container,Settings.CODE_TIMEOUT_PATH,Settings.CODE_TIMEOUT_DEFAULT);
			createNode(container,Settings.SECRET_PATH,Settings.SECRET_DEFAULT);
			
			createNode(container,Settings.MESSAGE_BROKER_URL_PATH,Settings.MESSAGE_BROKER_URL_DEFAULT);
			createNode(container,Settings.PROFILES_STORAGE_URL_PATH,Settings.PROFILES_STORAGE_URL_DEFAULT);
			createNode(container,Settings.PROFILES_STORAGE_CRED_PATH,Settings.PROFILES_STORAGE_CRED_DEFAULT);
			createNode(container,Settings.AAAI_URL_PATH,Settings.AAAI_URL_DEFAULT);
			createNode(container,Settings.AAAI_CRED_PATH,Settings.AAAI_CRED_DEFAULT);


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void createNode(String container,String path, String value ){
		String relativePath = settings.getProperty(path);
		String absolutePath = container + relativePath;
		String defaultValue = settings.getProperty(value);
		try {
			String returnPath = client.create()
					.creatingParentsIfNeeded()
					.withMode(CreateMode.PERSISTENT)
					.withACL(getNodeServiceACL())
					.forPath(absolutePath,defaultValue.getBytes());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args)  {
		try {
			new Configurator(Settings.EVRE_TEST,"localhost:2181");
			TimeUnit.SECONDS.sleep(120);		
			System.out.println("##### closed");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}






}