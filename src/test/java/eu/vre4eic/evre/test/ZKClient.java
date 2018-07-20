package eu.vre4eic.evre.test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.ACLProvider;
import org.apache.curator.retry.RetryOneTime;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.server.ServerConfig;
import org.apache.zookeeper.server.ZooKeeperServerMain;
import org.apache.zookeeper.server.admin.AdminServer.AdminServerException;
import org.apache.zookeeper.server.quorum.QuorumPeerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.vre4eic.evre.nodeservice.Settings;
import eu.vre4eic.evre.nodeservice.Utils;

public class ZKClient {

	private Thread zkService;


	public void startService(int clientPort) throws Exception {
		final Logger log = LoggerFactory.getLogger(this.getClass());

		try {
			Properties properties = new Properties();
			File file = new File(System.getProperty("user.dir")+ File.separator + "ZKData");
			file.deleteOnExit();
			properties.setProperty("dataDir", file.getAbsolutePath());
			properties.setProperty("clientPort", String.valueOf(clientPort));
			properties.setProperty("authProvider.1","org.apache.zookeeper.server.auth.SASLAuthenticationProvider");

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

	public void stopService() throws IOException {
		zkService.interrupt();
	}


	public void init_eVRE_Env() throws Exception {
//	     String authenticationString = "adminsecret" + ":" + "bobsecret";
	     String authenticationString = "NodeService" + ":" + "N0d3S3rv1c318";

		CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder()
			.connectString("localhost:2181")
			.retryPolicy(new RetryOneTime(1))
			.authorization("digest", authenticationString.getBytes());
//            .aclProvider(new ACLProvider() {
//                @Override
//                public List<ACL> getDefaultAcl() {
//                    return ZooDefs.Ids.CREATOR_ALL_ACL;
//                }
//
//                @Override
//                public List<ACL> getAclForPath(String path) {
//                    return ZooDefs.Ids.CREATOR_ALL_ACL;
//                }
//            });		
		
		CuratorFramework client = builder.build();

        client.start();


        client.create().withMode(CreateMode.PERSISTENT).forPath("/pip");
        
			client.close();

	}
	
	
	public void createEntry(String path, String value,CuratorFramework client){
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

		ZKClient zkServer = new ZKClient();
		try {		
			//zkServer.startService(2181);
			zkServer.init_eVRE_Env();

			while (true){
				TimeUnit.MILLISECONDS.sleep(50);			
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		finally
		{
			try {
				zkServer.stopService();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
