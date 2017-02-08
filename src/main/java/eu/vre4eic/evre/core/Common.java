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
	 * UserRole: <ul><li>CUSTOMER, <li>OPERATOR, <li>ADMIN, <li>CONTROLLER</ul>
	 *
	 */
	public enum UserRole {
		CUSTOMER, OPERATOR, ADMIN, CONTROLLER
	}
	
	/**
	 * ResponseStatus: <ul><li>FAILED, <li>SUCCEED, <li>EMPTY_RESULT <li>WARNING</ul>
	 *
	 */
	public enum ResponseStatus {
		FAILED, SUCCEED, EMPTY_RESULT, WARNING
	}
	
	/**
	 * ServiceStatus: <ul><li>Start, <li>Run, <li>Error <li>Stop</ul>
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
