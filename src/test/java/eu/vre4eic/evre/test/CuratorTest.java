package eu.vre4eic.evre.test;

import java.io.IOException;
import java.util.List;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryOneTime;
import org.apache.curator.test.TestingServer;
import org.apache.zookeeper.data.Stat;

public class CuratorTest {

	public CuratorTest() throws Exception {
		
		
		class ZkTestServers {

			  TestingServer zkServer;
			  
			  public void startzkServer(int port) throws Exception {
			    zkServer = new TestingServer(port);  
			  }
			  
			  public void stopZkServer() throws IOException {
			    zkServer.close();
			  }
			}		
		
		
//		ZkTestServers zkServer = new ZkTestServers();
//		zkServer.startzkServer(2181);
		
		CuratorFramework client = CuratorFrameworkFactory
									.newClient("localhost:2181",new RetryOneTime(1));
		client.start();
		try
		{
			System.out.println(" ###### " + System.getProperty("user.dir"));
			System.out.println(" ###### " + System.getProperty("user.home"));

			
//			String path = client.create()
//					.creatingParentContainersIfNeeded()
//					.forPath("/evre/services/MessageBroker/url", "tcp://v4e-lab.isti.cnr.it:61616".getBytes());
			Stat exists = client.checkExists()
					.forPath("/evre/services/MessageBroker/url");
			if (exists != null) 
				client.setData().inBackground()
				.forPath("/evre/services/MessageBroker/url", "tcp://v4e-lab.isti.cnr.it:33333".getBytes());
			List<String> paths = client.getChildren()
					.forPath("/evre/services");
			System.out.println("####### get data" +  paths);

		}
		finally
		{
			client.close();
//			zkServer.stopZkServer();
		}
	}

	public static void main(String[] args) {
		try {

//			new CuratorTest();
//			while (true){
//				TimeUnit.SECONDS.sleep(500);			
//			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}


	}

}
