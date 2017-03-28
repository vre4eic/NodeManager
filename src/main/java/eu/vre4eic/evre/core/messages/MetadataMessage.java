package eu.vre4eic.evre.core.messages;

import eu.vre4eic.evre.core.Common.MetadataOperationType;

/**
 * This interface defines the methods  to set/get values used to notify to e-VRE services that an operation has been executed
 * by the Metadata service
 * @author Cesare
 *
 */
public interface MetadataMessage extends Message {

	/**
	 * Gets the token of the user that has executed the operation. 
	 * @return String - the token identifying the user
	 */
	public String getToken();
	/**
	 * Sets the token of the user that has requested the operation. 
	 * @param token String - the user token
	 */
	public MetadataMessage setToken(String token);

	/**
	 * Returns the operation type
	 * 
	 * @return MetadataOperationType - the operation
	 */
	public MetadataOperationType getOperation();

	/**
	 * Sets the operation type 
	 * 
	 * @param op MetadataOperationType - the type of the operation
	 */
	public MetadataMessage setOperation(MetadataOperationType op);
	
	
}
