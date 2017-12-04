package eu.vre4eic.evre.test;

import java.util.Properties;

import eu.vre4eic.evre.nodeservice.Settings;
import eu.vre4eic.evre.nodeservice.Utils;
import eu.vre4eic.evre.nodeservice.modules.authentication.AuthModule;

public class AuthModuleTest {
	
	private static AuthModule module;
	
	public static void main(String[] args)  {

		Properties nodeSettings = Utils.getNodeServiceProperties();
		Properties defaultSettings = Settings.getProperties();
		String messageBrokerPath = defaultSettings.getProperty(Settings.MESSAGE_BROKER_PATH);
		
		String brokerURL =  nodeSettings.getProperty(messageBrokerPath);

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
	}
	
	



}
