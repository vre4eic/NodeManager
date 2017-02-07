package eu.vre4eic.evre.nodeservice.core.messages;


import eu.vre4eic.evre.nodeservice.core.Common.ServiceStatus;
import eu.vre4eic.evre.nodeservice.core.Common.UserRole;

/**
 * This interface defines the message published when a change happens in a service life-cycle
 * @author Cesare
 *
 */
public interface ServiceLCMessage extends Message {

	/**
	 * Gets the identifier of the service. 
	 * @return String - the id identifying the service
	 */
	public String getServiceId();
	
	/**
	 * Sets the the identifier of the service.
	 *
	 * @param id String - the service identifier
	 */
	public void setServiceId(String id);

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
