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
