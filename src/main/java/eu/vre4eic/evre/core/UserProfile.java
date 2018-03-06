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
package eu.vre4eic.evre.core;

import com.fasterxml.jackson.annotation.JsonIgnore;

import eu.vre4eic.evre.core.Common.UserRole;

/**
 * This interface describes the user profile.
 * 
 * @author Cesare
 *
 */
public interface UserProfile extends UserCredentials{

	
	
	public String getName() ;

	public void setName(String name);
	
	public String getOrganization();

	public void setOrganization(String organization);
	
	public UserRole getRole() ;

	public void setRole(UserRole role) ;

	public String getEmail() ;

	public void setEmail(String email) ;
	
	@JsonIgnore
	public String getSnsId ();

	public void setSnsId(String commId) ;
	@JsonIgnore
	public String getAuthId ();

	public void setAuthId(String commId) ;
	
	
}
