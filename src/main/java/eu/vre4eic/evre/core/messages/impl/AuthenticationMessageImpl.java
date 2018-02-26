/*******************************************************************************
 * Copyright (c) 2018 VRE4EIC Consortium
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
/**
 * 
 */
package eu.vre4eic.evre.core.messages.impl;

import java.time.LocalDateTime;

import org.springframework.context.annotation.Configuration;



import eu.vre4eic.evre.core.Common.ResponseStatus;
import eu.vre4eic.evre.core.Common.UserRole;
import eu.vre4eic.evre.core.messages.AuthenticationMessage;



/**
 * @author cesare
 *
 */
@Configuration
public class AuthenticationMessageImpl extends MessageImpl implements AuthenticationMessage {

	private String token;
	private UserRole role;
	private LocalDateTime timelimit;
	
	private String zoneId;
	
	private String renewable;
	
	public AuthenticationMessageImpl() {
		super();
	}
	
	// TODO
	// LocaleDate, timeZone,  timeLimit, renewable should be added to constructor !?
	public AuthenticationMessageImpl(ResponseStatus status, String message, String token, UserRole role, LocalDateTime ttl) {
		super(message,status);
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
