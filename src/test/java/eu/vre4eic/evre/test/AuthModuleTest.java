package eu.vre4eic.evre.test;

import java.util.Properties;

import eu.vre4eic.evre.core.comm.NodeLinker;
import eu.vre4eic.evre.nodeservice.Settings;
import eu.vre4eic.evre.nodeservice.Utils;
import eu.vre4eic.evre.nodeservice.modules.authentication.AuthModule;

public class AuthModuleTest {
	
	private static AuthModule module;
	
	public static void main(String[] args)  {


		try {
//			Properties defaultSettings = Settings.getProperties();
//			String ZkServer = defaultSettings.getProperty(Settings.ZOOKEEPER_DEFAULT);
//			NodeLinker node = NodeLinker.init(ZkServer);
			
			NodeLinker node = NodeLinker.init("localhost:2181");
			String brokerURL =  node.getMessageBrokerURL();

			module = AuthModule.getInstance(brokerURL);
			System.out.println("############ TOKEN Pluto " + module.checkToken("pluto"));


			while (true) {
				try {
					Thread.sleep(60000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				module.listToken();
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
	
	



}
