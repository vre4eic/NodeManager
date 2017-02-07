package eu.vre4eic.evre.nodeservice.core.messages;

import eu.vre4eic.evre.nodeservice.core.Common.UserRole;

/**
 * This message is published when a user is authenticated or logs out
 * @author Cesare
 *
 */
public interface AuthenticationMessage extends Message {

	/**
	 * Gets the token assigned to the authenticated user. 
	 * @return String - the token identifying the user
	 */
	public String getToken();
	/**
	 * Sets the token assigned to the authenticated user. The token is assigned by the AAAI service.
	 *
	 * @param token String - the user token
	 */
	public void setToken(String token);
	/**
	 * Gets the time to live assigned to the authenticated user. Once the ttl has elapsed the token is no longer valid.
	 * @return String - the token identifying the user
	 */
	public String getTTL();
	/**
	 * Sets the time to live assigned to the authenticated user. Once the ttl has elapsed the token is no longer valid.
	 *
	 * @param ttl String - the lifetime of a token i e-VRE
	 */
	public void setTTL(String ttl);


	/**
	 * Returns the userRole
	 * 
	 * @return UserRole - the role
	 * @see UserRole
	 */
	public UserRole getRole();

	/**
	 * Sets the UserRole 
	 * 
	 * @param role UserRole - the role to set
	 * @see UserRole
	 */
	public void setRole(UserRole role);
	
}
