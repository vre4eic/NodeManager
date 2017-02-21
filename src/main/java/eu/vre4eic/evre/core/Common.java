/**
 * 
 */
package eu.vre4eic.evre.core;

/**
 * Enum definitions common to all classes
 * @author Cesare
 *
 */
public class Common {
	/**
	 * UserRole: <ul><li>RESEARCHER</li> <li>OPERATOR</li> <li>ADMIN</li> <li>CONTROLLER</li></ul>
	 *
	 */
	public enum UserRole {
		RESEARCHER, OPERATOR, ADMIN, CONTROLLER
	}
	
	/**
	 * ResponseStatus: <ul><li>SUCCEED</li> <li>EMPTY_RESULT</li> <li>WARNING</li> <li>FAILED</li></ul>
	 *
	 */
	public enum ResponseStatus {
		SUCCEED, EMPTY_RESULT, WARNING, FAILED,
	}
	
	/**
	 * ServiceStatus: <ul><li>Start</li> <li>Run</li> <li>Error</li> <li>Stop</li></ul>
	 *
	 */
	public enum ServiceStatus {
		START, RUN, ERROR, STOP
	}
	
	/**
	 * MetadataOperationType: <ul><li>Read</li> <li>Query</li> <li>Insert</li><li>Update</li><li>Delete</li></ul>
	 */
	public enum MetadataOperationType{
		READ,QUERY,INSERT,UPDATE, DELETE
	}
	/**
	 * NotificationType: <ul><li>Authentication</li> <li>Metadata</li><li>UserProfile</li><li>Service</li></ul>
	 */
	public enum NotificationType{
		AUTHENTICATION, METADATA,USERPROFILE,SERVICE
	}
	
}
