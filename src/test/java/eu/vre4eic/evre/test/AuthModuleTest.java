package eu.vre4eic.evre.test;

import java.util.Properties;

import eu.vre4eic.evre.nodeservice.Utils;
import eu.vre4eic.evre.nodeservice.modules.authentication.AuthModule;

public class AuthModuleTest {
	
	private static AuthModule module;
	
	public static void main(String[] args)  {
		
		Properties property = Utils.getNodeServiceProperties();
		String brokerURL =  property.getProperty("BROKER_URL");
		
		module = AuthModule.getInstance();
		
		
		while (true) {
			try {
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			module.listToken();
		}
	}
	
	



}
