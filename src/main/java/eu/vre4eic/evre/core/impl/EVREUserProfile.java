package eu.vre4eic.evre.core.impl;



import eu.vre4eic.evre.core.Common.UserRole;

import org.springframework.context.annotation.Configuration;

import eu.vre4eic.evre.core.UserProfile;

/**
 * @author cesare
 *
 */
@Configuration
public class EVREUserProfile implements UserProfile {

	String login;
	String password;
	String name;
	UserRole role;
	String email;
	
	public EVREUserProfile() {
		// TODO Auto-generated constructor stub
	}
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	

}
