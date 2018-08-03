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

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryOneTime;
import org.apache.curator.test.TestingServer;
import org.apache.zookeeper.data.Stat;

public class CuratorTest {

	public CuratorTest() throws Exception {
		
		
		System.setProperty("zookeeper.clientCnxnSocket", "org.apache.zookeeper.ClientCnxnSocketNetty");
		System.setProperty("zookeeper.client.secure", "true");
		
//		System.setProperty("zookeeper.ssl.keyStore.location", "C:\\Users\\francesco\\git\\NodeService-CP\\clientKS");
		System.setProperty("zookeeper.ssl.keyStore.location", "clientKS");
		System.setProperty("zookeeper.ssl.keyStore.password","clientKS");
//		System.setProperty("zookeeper.ssl.trustStore.location","C:\\Users\\francesco\\git\\NodeService-CP\\clientTS");
		System.setProperty("zookeeper.ssl.trustStore.location","clientTS");
		System.setProperty("zookeeper.ssl.trustStore.password","clientTS");

		CuratorFramework client = CuratorFrameworkFactory
									.newClient("localhost:2281",new RetryOneTime(1));
		client.start();
		try
		{
			System.out.println(" ###### user.dir " + System.getProperty("user.dir"));
			System.out.println(" ###### user.home " + System.getProperty("user.home"));

			
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
			System.out.println("####### get paths /evre/services " +  paths);
			byte[] data = client.getData()
					.forPath("/t");
			System.out.println("####### get data /t " +  new String (data));

		}
		finally
		{
			client.close();
//			zkServer.stopZkServer();
		}
	}

	public static void main(String[] args) {
		try {

			new CuratorTest();
			while (true){
				TimeUnit.SECONDS.sleep(500);			
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}


	}

}
