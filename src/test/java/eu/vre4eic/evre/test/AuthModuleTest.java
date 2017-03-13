package eu.vre4eic.evre.test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Properties;
import java.util.TimeZone;

import eu.vre4eic.evre.nodeservice.Utils;
import eu.vre4eic.evre.nodeservice.modules.authentication.AuthModule;

public class AuthModuleTest {
	
	private static AuthModule module;
	
	public static void main(String[] args)  {
		
		Properties property = Utils.getNodeServiceProperties();
		String brokerURL =  property.getProperty("BROKER_URL");
		
		module = AuthModule.getInstance(brokerURL);
		String z = ZoneId.systemDefault().getId();
		ZoneId z1 = ZoneId.of(z);
		System.out.println(z1.getId());
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
