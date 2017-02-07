package eu.vre4eic.evre.nodeservice.core.messages;

import eu.vre4eic.evre.nodeservice.core.Common;
import eu.vre4eic.evre.nodeservice.core.Common.ServiceStatus;
import eu.vre4eic.evre.nodeservice.core.Common.UserRole;

/**
 * This message is published when an event happens in a service life-cycle
 * @author Cesare
 *
 */
public interface ServiceLCMessage extends Message {

	/**
	 * Gets the identifier of the service. 
	 * @return String - the token identifying the user
	 */
	public String getServiceId();
	
	/**
	 * Sets the the identifier of the service.
	 *
	 * @param token String - the user token
	 */
	public void setServiceId(String token);

	/**
	 * Returns the new life-cycle status
	 * 
	 * @return ServiceStatus - the current service status
	 * @see UserRole
	 */
	public ServiceStatus getStatus();

	/**
	 * Sets the the current service status 
	 * 
	 * @param status ServiceStatus - the service status
	 * @see UserRole
	 */
	public void setStatus(ServiceStatus status);
	
}
