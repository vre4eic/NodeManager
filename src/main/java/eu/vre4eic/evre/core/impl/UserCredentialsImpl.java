package eu.vre4eic.evre.core.impl;

import org.springframework.context.annotation.Configuration;

import eu.vre4eic.evre.core.UserCredentials;

@Configuration
public class UserCredentialsImpl implements UserCredentials {
	
	String userId;
	String password;

	public UserCredentialsImpl() {
		
	}
	
	public UserCredentialsImpl(String userId, String password) {
		this.userId=userId;
		this.password=password;
	}

	@Override
	public String getUserId() {
		
		return this.userId;
	}

	@Override
	public void setUserId(String userId) {
		this.userId=userId;

	}

	@Override
	public String getPassword() {
		
		return this.password;
	}

	@Override
	public void setPassword(String password) {
		this.password=password;

	}

}
