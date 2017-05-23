package eu.vre4eic.evre.test;

import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryOneTime;
import org.apache.zookeeper.CreateMode;

public class CuratorTest {

	public CuratorTest() throws Exception {
		CuratorFramework client = CuratorFrameworkFactory
									.newClient("localhost:2181",new RetryOneTime(1));
		client.start();
		try
		{
			String path = client.create()
							.creatingParentContainersIfNeeded()
							.forPath("/evre/services/MessageBroker/url", "tcp://v4e-lab.isti.cnr.it:61616".getBytes());
		}
		finally
		{
			client.close();
		}
	}

	public static void main(String[] args) {
		try {		
			new CuratorTest();
			while (true){
				TimeUnit.MILLISECONDS.sleep(50);			
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}


	}

}
