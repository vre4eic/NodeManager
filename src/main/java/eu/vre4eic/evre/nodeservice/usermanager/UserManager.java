/**
 * 
 */
package eu.vre4eic.evre.nodeservice.usermanager;

import java.util.List;

import eu.vre4eic.evre.core.EvreEvent;
import eu.vre4eic.evre.core.EvreQuery;
import eu.vre4eic.evre.core.UserCredentials;
import eu.vre4eic.evre.core.UserProfile;
import eu.vre4eic.evre.core.messages.Message;

/**
 * This interface contains methods for managing users. It contains signature of the methods of the ResourceManager component
 * decribed in the Deliverable 3.1
 * @author cesare
 *
 */
public interface UserManager {
	
	
	public Message createUserProfile(UserProfile profile);
	
	public Message updateUserProfile(String userId, UserProfile profile);
	
	public Message removeUserProfile(String userId);
	
	public UserProfile getUserProfile(String userId);
	
	public UserProfile getUserProfile(UserCredentials credentials);
	

	public List <UserProfile> getUserProfile(EvreQuery query);
	
	public Message subscribeEvent(String idUser, List <EvreEvent> events);
	
	/*
	 * Warning: this method was not on the Deliverable. it is used to remove subscription to events.
	 */
	public Message unSubscribeEvent(String idUser, List <EvreEvent> events);
	
	public Message checkEvent(String userId, String eventId);
	
	public List <Message> checkEvent(String userId);
	
	
	public List<EvreEvent> getSubscribedEvents (String userId);
	
	public Message login (UserCredentials credentials);
	
	public Message login (String authId, String deviceId);
	
	public Message logout(String token);
	
	public Message registerAuthenticator(UserCredentials credentials, String authId);
	
	public Message removeAuthenticator(UserCredentials credentials, String authId);
	
	/*
	 * signature changed from the original deliverable description, need to verify.
	 */
	public Message authenticate(UserCredentials credentials, String authId);
	
	public Message  synchCredentials (String authId, List <UserCredentials> credentials);

}
