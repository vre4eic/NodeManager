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
import java.util.concurrent.TimeUnit;

import org.apache.curator.test.TestingServer;

public class CuratorServer {

	TestingServer zkServer;

	public void startzkServer(int port) throws Exception {
		zkServer = new TestingServer(port);  
		System.out.println("##### Connection String  " + zkServer.getConnectString());
		System.out.println("##### Temp Directory  " + zkServer.getTempDirectory());
		
	}

	public void stopZkServer() throws IOException {
		zkServer.close();
	}


	public static void main(String[] args) {
		
		CuratorServer zkServer = new CuratorServer();
		try {		
			zkServer.startzkServer(2181);
			
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
				zkServer.stopZkServer();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
