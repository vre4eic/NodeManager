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
package eu.vre4eic.evre.nodeservice.usermanager.impl;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.annotation.Id;

import eu.vre4eic.evre.core.Common.UserRole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Configuration
@ApiModel
public class ProfileStatus {
	@Id
	String id;
	String userId;
	UserRole role;
	String token;
	String authId;
	
	
	
	public ProfileStatus() {
		
	}
	
	public ProfileStatus(String userId, UserRole role, String token, String authId) {
		
		this.id=userId;
		this.userId=userId;
		this.role=role;
		this.token=token;
		this.authId=authId;
	}
	
	public String getAuthId() {
		return authId;
	}
	public void setAuthId(String authId) {
		this.authId = authId;
	}
	
	
	
	@ApiModelProperty(position = 1, required = true, value = "userid containing lowercase letters or numbers")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	
	@ApiModelProperty(position = 2, required = true)
	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	@ApiModelProperty(position = 3, required = true)
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	

	@Override
    public String toString() {
        return String.format(
                "User [id=%s, token=%s, role='%s', authId='%s']",
                userId, token, role, authId);
    }

}
