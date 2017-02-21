/**
 * 
 */
package eu.vre4eic.evre.core.messages.impl;

import org.springframework.context.annotation.Configuration;

import eu.vre4eic.evre.core.Common.ResponseStatus;
import eu.vre4eic.evre.core.Common.UserRole;
import eu.vre4eic.evre.core.messages.AuthenticationMessage;



/**
 * @author cesare
 *
 */
@Configuration
public class AuthenticationMessageImpl implements AuthenticationMessage {

	private String token;
	private ResponseStatus status;
	private String message;
	private UserRole role;
	String ttl;
	
	public AuthenticationMessageImpl() {
		super();
	}
	public AuthenticationMessageImpl(ResponseStatus status, String message, String token, UserRole role, String ttl) {
		super();
		this.status = status;
		this.message = message;
		this.role=role;
		this.ttl=ttl;
		this.token=token;
		
	}
	
	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}
	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}
	
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public ResponseStatus getStatus() {
		return status;
	}

	@Override
	public void setStatus(ResponseStatus status) {
		this.status=status;
		
	}

	@Override
	public String getTTL() {
		
		return ttl;
	}

	@Override
	public void setTTL(String ttl) {
		 this.ttl=ttl;
		
	}

	@Override
	public UserRole getRole() {
		
		return role;
	}

	@Override
	public void setRole(UserRole role) {
		this.role=role;
		
	}
	
}
