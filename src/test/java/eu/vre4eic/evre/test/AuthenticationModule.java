package eu.vre4eic.evre.test;

import java.time.LocalDateTime;
import java.util.Hashtable;

import eu.vre4eic.evre.core.messages.impl.AuthenticationMessageImpl;

public class AuthenticationModule {
	
	private static AuthenticationModule instance = null;
	private Hashtable<String, AuthenticationMessageImpl> AuthTable;
	
	protected AuthenticationModule(){
		//initialize data structure for tokens
		AuthTable = new  Hashtable<String, AuthenticationMessageImpl> ();
		
		//retrieve Broker URL
		
		//subscribe Auth_channel
		
	}
	
	public static AuthenticationModule getInstance(){
		if(instance == null) {
	         instance = new AuthenticationModule();
	      }
	      return instance;
	      
	}
	
	
	public boolean checkToken (String tkn) {
		if (AuthTable == null) {
			getInstance();
			return false;
		}
		
		if (AuthTable.containsKey(tkn)) {
			AuthenticationMessageImpl am = AuthTable.get(tkn);
			LocalDateTime now = LocalDateTime.now();
			if (now.isBefore(am.getTimeLimit()))
				return true;
			else {
				AuthTable.remove(tkn);
				return false;			
			}

		}
		return false;				
	}
	
	
	private void doHousekeeping(){
		
		
	}

}
