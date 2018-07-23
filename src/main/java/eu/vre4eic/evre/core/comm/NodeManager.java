package eu.vre4eic.evre.core.comm;

import java.net.InetAddress;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryOneTime;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.vre4eic.evre.nodeservice.Settings;

public class NodeManager{
	
	private static Logger log = LoggerFactory.getLogger(NodeManager.class.getClass());
	private static CuratorFramework client;

			
	public  static void  register(String nodeServiceURL, String identity){
		if (client == null)
			try {
				openConnection(nodeServiceURL);
				doRegistration(identity);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}
	
	private  static void  openConnection(String nodeServiceURL) {
		client = CuratorFrameworkFactory.newClient(nodeServiceURL,new RetryOneTime(1));
		client.start();

	}
	
	private  static void  doRegistration(String identity) throws Exception {
		if (identity == null || identity.isEmpty())
			identity = "anonymous";
		
		String env = "/evre/production";
		String clientId = env+"/clients/"+identity;
		InetAddress ia = InetAddress.getLocalHost();
		String ipAddress = ia.getHostAddress();
		String path = client.create()
//				.withACL()
				.creatingParentContainersIfNeeded()
				.forPath(clientId, ipAddress.getBytes())
				;
		log.info("### Node created as"+path);
	}
	
	
	public  static CuratorFramework  getConnection() {
		if (client == null)
			log.error("Client not registered! use NodeManager.register()");
		return client;
	}
	
	public  static void close(){
			if (client != null) {
				client.close();
				client =null;
			}
	}
	
	

	
}
