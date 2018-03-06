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
package eu.vre4eic.evre.core.messages;

/**
 * /**
 * This interface defines the methods  used in Two-factors authentication
 * 
 * @author francesco
 *
 */
public interface MultiFactorMessage extends Message {
	
	
	/**
	 * Sets the temporary id of the user that has requested the 2fa. 
	 * @param authId String - the temporary user id
	 */
	public MultiFactorMessage setAuthId(String authId);
	
	/**
	 * Gets the temporary id of the user that has requested the 2fa. 
	 * @return String - the temporary user id
	 */

	public String getAuthId();
	
	/**
	 * Sets the username of the user that has requested the 2fa. 
	 * @param userId String - the username
	 */
	
	public MultiFactorMessage setUserId(String userId);
	
	/**
	 * Gets the username of the user that has requested the 2fa. 
	 * @return String - the username
	 */
	public String getUserId();
	
	/**
	 * Sets the second-factor sent to the user that has requested the 2fa. 
	 * @param code String - the second-factor
	 */
	public MultiFactorMessage setCode(String code);
	
	
	/**
	 * Gets the second-factor sent to the user that has requested the 2fa. 
	 * @return String - the second-factor
	 */
	public String getCode();
}
