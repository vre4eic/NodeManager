package eu.vre4eic.evre.core;

import eu.vre4eic.evre.core.Common.UserRole;

/**
 * This interface describes the user profile (will be modified, check CERIF  specs)
 * 
 * @author Cesare
 *
 */
public interface UserProfile {

	public String getUserId();

	public void setUserId(String userId) ;

	public String getPassword() ;

	public void setPassword(String password) ;
	public String getName() ;

	public void setName(String name);

	public UserRole getRole() ;

	public void setRole(UserRole role) ;

	public String getEmail() ;

	public void setEmail(String email) ;
	
	public String getSnsId ();

	public void setSnsId(String commId) ;
	
	public String getAuthId ();

	public void setAuthId(String commId) ;
	
}
