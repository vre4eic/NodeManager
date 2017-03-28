package eu.vre4eic.evre.core.messages;

import java.time.LocalDateTime;
import eu.vre4eic.evre.core.Common.UserRole;

/**
 * This interface defines the methods to set/get the values returned when an authentication operation is executed by a client.
 * The same values are used to build the <i>message</i> sent to other e-VRE services to notify the authentication event.
 * @author Cesare
 *
 */
public interface AuthenticationMessage extends Message{

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
	public AuthenticationMessage setToken(String token);
	
	/**
	 * Gets the zone time id used to calculate the token's TimeLimit.
	 * @return ZoneId - the zoneId
	 */
	public String  getTimeZone();
	
	/**
	 * Sets the time zone id used to calculate the token's time limit.
	 *
	 * @param string ZoneId - the ZoneId
	 */
	public AuthenticationMessage setTimeZone(String string);
	
	/**
	 * Gets the time to live assigned to the authenticated user. Once the ttl has elapsed the token is no longer valid.
	 * @return String - the token identifying the user
	 */
	public LocalDateTime  getTimeLimit();
	
	/**
	 * Sets the time to live assigned to the authenticated user. Once the ttl has elapsed the token is no longer valid.
	 *
	 * @param ttl String - the lifetime of a token i e-VRE
	 */
	public AuthenticationMessage setTimeLimit(LocalDateTime ttl);


	
	/**
	 * Gets the period of time in minutes of a renewable token, 0 otherwise.
	 * @return String  - the minutes
	 */
	public String  getRenewable();
	
	/**
	 * Sets  the period of time in minutes of a renewable token.
	 *
	 * @param String - minutes
	 */
	public AuthenticationMessage setRenewable(String ttl);

	
	
	
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
	public AuthenticationMessage setRole(UserRole role);
	
}
