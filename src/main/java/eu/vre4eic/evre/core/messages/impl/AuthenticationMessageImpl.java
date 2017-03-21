/**
 * 
 */
package eu.vre4eic.evre.core.messages.impl;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.context.annotation.Configuration;

import eu.vre4eic.evre.core.Common.ResponseStatus;
import eu.vre4eic.evre.core.Common.UserRole;
import eu.vre4eic.evre.core.messages.AuthenticationMessage;



/**
 * @author cesare
 *
 */
@Configuration
public class AuthenticationMessageImpl implements AuthenticationMessage, Serializable {

	private String token;
	private ResponseStatus status;
	private String message;
	private UserRole role;
	private LocalDateTime timelimit;
	private String zoneId;
	private String renewable;
	
	public AuthenticationMessageImpl() {
		super();
	}
	
	public AuthenticationMessageImpl(ResponseStatus status, String message, String token, UserRole role, LocalDateTime ttl) {
		super();
		this.status = status;
		this.message = message;
		this.role=role;
		this.timelimit=ttl;
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
	 * @return 
	 */
	public AuthenticationMessage setToken(String token) {
		this.token = token;
		return this;
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
	public AuthenticationMessage setMessage(String message) {
		this.message = message;
		return this;
	}
	
	@Override
	public ResponseStatus getStatus() {
		return status;
	}

	@Override
	public AuthenticationMessage setStatus(ResponseStatus status) {
		this.status=status;
		return this;
		
	}


	@Override
	public UserRole getRole() {
		
		return role;
	}

	@Override
	public AuthenticationMessage setRole(UserRole role) {
		this.role=role;
		return this;
		
	}
	@Override
	public LocalDateTime getTimeLimit() {
		return this.timelimit;
	}
	@Override
	public AuthenticationMessage setTimeLimit(LocalDateTime ttl) {
		this.timelimit= ttl;
		return this;
		
	}

	@Override
	public String getTimeZone() {
		// TODO Auto-generated method stub
		return zoneId;
	}

	@Override
	public AuthenticationMessage setTimeZone(String zoneId) {
		this.zoneId=zoneId;
		return this;
		
	}

	@Override
	public String getRenewable() {
		// TODO Auto-generated method stub
		return renewable;
	}

	@Override
	public AuthenticationMessage setRenewable(String minutes) {
		this.renewable= minutes;
		return this;
	}
	
}
