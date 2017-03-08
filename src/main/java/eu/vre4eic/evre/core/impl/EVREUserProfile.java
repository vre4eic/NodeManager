package eu.vre4eic.evre.core.impl;



import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import eu.vre4eic.evre.core.Common.UserRole;

import org.springframework.context.annotation.Configuration;

import eu.vre4eic.evre.core.UserProfile;

/**
 * @author cesare
 *
 */

@Configuration
@ApiModel
public class EVREUserProfile implements UserProfile {

	String login;
	String password;
	String name;
	UserRole role;
	String email;
	String snsId;
	String authId;
	
	public String getSnsId() {
		return snsId;
	}
	public void setSnsId(String snsId) {
		this.snsId = snsId;
	}
	public String getAuthId() {
		return authId;
	}
	public void setAuthId(String authId) {
		this.authId = authId;
	}
	
	
	public EVREUserProfile() {
		
	}
	@ApiModelProperty(position = 1, required = true, value = "username containing lowercase letters or numbers")
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@ApiModelProperty(position = 2, required = false)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	@ApiModelProperty(position = 3, required = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ApiModelProperty(position = 4, required = true)
	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	@ApiModelProperty(position = 5, required = true)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	

	

}
