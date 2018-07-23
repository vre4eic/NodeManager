package eu.vre4eic.evre.test;

import java.util.concurrent.TimeUnit;

import eu.vre4eic.evre.core.comm.NodeManager;

public class NodeManagerTest {

	public NodeManagerTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args)  {
		try {
			NodeManager.register("localhost:2181",null);


			TimeUnit.SECONDS.sleep(15);		
			System.out.println("##### closing");
			NodeManager.close();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("##### interrupted");
			e.printStackTrace();
			NodeManager.close();
		}
		

	}

}
