package eu.vre4eic.evre.core;


/**
 * This interface describes user credentials (to be defined)
 * 
 * @author Cesare
 *
 */
public interface UserCredentials {
	public String getUserId();

	public void setUserId(String userId) ;

	public String getPassword() ;

	public void setPassword(String password) ;
}
