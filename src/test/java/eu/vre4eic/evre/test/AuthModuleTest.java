package eu.vre4eic.evre.test;

import javax.jms.JMSException;
import eu.vre4eic.evre.nodeservice.modules.authentication.AuthModule;

public class AuthModuleTest {
	
	private static AuthModule module;
	
	public static void main(String[] args) throws JMSException {
		module = AuthModule.getInstance();
	}
	
	



}
