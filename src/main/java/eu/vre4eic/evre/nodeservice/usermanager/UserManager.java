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
package eu.vre4eic.evre.nodeservice.usermanager;

import java.util.List;

import eu.vre4eic.evre.core.EvreEvent;
import eu.vre4eic.evre.core.EvreQuery;
import eu.vre4eic.evre.core.UserCredentials;
import eu.vre4eic.evre.core.UserProfile;
import eu.vre4eic.evre.core.impl.EVREUserProfile;
import eu.vre4eic.evre.core.messages.AuthenticationMessage;
import eu.vre4eic.evre.core.messages.Message;

/**
 * This interface contains methods for managing user profiles. It contains signature of the methods of the UserManager component
 * decribed in the Deliverable 3.1
 * @author cesare
 *
 */
public interface UserManager {
	
	
	public Message createUserProfile(EVREUserProfile profile);
	
	public Message updateUserProfile(String userId, EVREUserProfile profile);
	
	public Message passwordRecovery(String email);
	
	public Message removeUserProfile(String userId);
	
	public EVREUserProfile getUserProfile(String userId);
	
	public EVREUserProfile getUserProfile(UserCredentials credentials);
	

	public List <EVREUserProfile> getUserProfile(EvreQuery query);
	
	public List <EVREUserProfile> getAllUserProfiles();
	
	public Message subscribeEvent(String idUser, List <EvreEvent> events);
	
	/*
	 * Warning: this method was not on the Deliverable. it is used to remove subscription to events.
	 */
	public Message unSubscribeEvent(String idUser, List <EvreEvent> events);
	
	public Message checkEvent(String userId, String eventId);
	
	public List <Message> checkEvent(String userId);
	
	
	public List<EvreEvent> getSubscribedEvents (String userId);
	
	public AuthenticationMessage login (UserCredentials credentials);
	
	public AuthenticationMessage loginMFA (String userId, String password);
	
	public AuthenticationMessage loginMFACode (String token, String code);
	
	public AuthenticationMessage logout(String token);
	
	public Message registerAuthenticator(UserCredentials credentials, String authId);
	
	public Message removeAuthenticator(UserCredentials credentials, String authId);
	
	/*
	 * signature changed from the original deliverable description, need to verify.
	 */
	public Message authenticate(UserCredentials credentials, String authId);
	
	public Message  synchCredentials (String authId, List <UserCredentials> credentials);

}
