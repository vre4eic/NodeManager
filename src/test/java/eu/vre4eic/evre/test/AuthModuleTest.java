package eu.vre4eic.evre.test;

import java.time.Duration;
import java.time.LocalDateTime;

import javax.jms.JMSException;
import eu.vre4eic.evre.nodeservice.modules.authentication.AuthModule;

public class AuthModuleTest {
	
	private static AuthModule module;
	
	public static void main(String[] args)  {
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
