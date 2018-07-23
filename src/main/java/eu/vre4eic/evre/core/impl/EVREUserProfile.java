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
package eu.vre4eic.evre.core.impl;



import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import eu.vre4eic.evre.core.Common.UserRole;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

import eu.vre4eic.evre.core.UserProfile;

/**
 * @author cesare
 *
 */

@Configuration
@ApiModel
public class EVREUserProfile implements UserProfile {

	@Id
	String id;
	String userId;
	String organization;
	String password;
	String name;
	UserRole role;
	String email;
	String snsId;
	String authId;
	String organizationURL;
	byte[] salt;
	
	
	public EVREUserProfile() {
		
	}
	
	public EVREUserProfile(String userId, String password, String name, String organization, String organizationURL, UserRole role, String email, 
							String snsId, String authId) {
		
		this.id=userId;
		this.userId=userId;
		this.name=name;
		this.organization=organization;
		this.password=password;
		this.role=role;
		this.email=email;
		this.snsId=snsId;
		this.authId=authId;
		this.organizationURL=organizationURL;
		//add the salt 
		
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

	@JsonIgnore
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
	

	@ApiModelProperty(position = 6, required = true)
	public String getOrganizationURL() {
		return organizationURL;
	}

	public void setOrganizationURL(String organizationUrl) {
		this.organizationURL = organizationUrl;
	}
	@Override
    public String toString() {
        return String.format(
                "User [id=%s, password=%s, name='%s', email='%s']",
                userId, password, name, email);
    }

	@Override
	public String getOrganization() {
		
		return organization;
	}

	@Override
	public void setOrganization(String organization) {
		this.organization=organization;
		
	}
	public byte[] getSalt() {
		return salt;
	}
	public void setSalt(byte[] salt) {
		this.salt = salt;
	}

}
