package eu.vre4eic.evre.core.impl;



import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import eu.vre4eic.evre.core.Common.UserRole;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.annotation.Id;

import eu.vre4eic.evre.core.UserProfile;

/**
 * @author cesare
 *
 */

@Configuration
@ApiModel
public class EVREUserProfile implements UserProfile {

	@Id
	String userId;
	String password;
	String name;
	UserRole role;
	String email;
	String snsId;
	String authId;
	
	
	public EVREUserProfile() {
		
	}
	
	public EVREUserProfile(String userId, String password, String name, UserRole role, String email, 
							String snsId, String authId) {
		
		this.userId=userId;
		this.name=name;
		this.password=password;
		this.role=role;
		this.email=email;
		this.snsId=snsId;
		this.authId=authId;
		
	}
	
	public EVREUserProfile(String userId, String name, String email) {
		
		this.userId=userId;
		this.name=name;
		this.email=email;
		
	}

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
	
	
	
	@ApiModelProperty(position = 1, required = true, value = "username containing lowercase letters or numbers")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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
	

	@Override
    public String toString() {
        return String.format(
                "User [id=%s, name='%s', email='%s']",
                userId, name, email);
    }
	

}
