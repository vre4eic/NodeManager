/**
 * 
 */
package eu.vre4eic.evre.core.messages.impl;

import eu.vre4eic.evre.core.Common.MetadataOperationType;
import eu.vre4eic.evre.core.Common.ResponseStatus;
import eu.vre4eic.evre.core.messages.Message;
import eu.vre4eic.evre.core.messages.MetadataMessage;

/**
 * @author francesco
 *
 */
public class MetadataMessageImpl extends MessageImpl implements MetadataMessage {
	
	
	
	public  MetadataMessageImpl() {
		super();
	}
	
	public  MetadataMessageImpl(String message, ResponseStatus status) {
		super(message,status);
	}
	

	/* (non-Javadoc)
	 * @see eu.vre4eic.evre.core.messages.MetadataMessage#getToken()
	 */
	@Override
	public String getToken() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see eu.vre4eic.evre.core.messages.MetadataMessage#setToken(java.lang.String)
	 */
	@Override
	public void setToken(String token) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see eu.vre4eic.evre.core.messages.MetadataMessage#getOperation()
	 */
	@Override
	public MetadataOperationType getOperation() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see eu.vre4eic.evre.core.messages.MetadataMessage#setOperation(eu.vre4eic.evre.core.Common.MetadataOperationType)
	 */
	@Override
	public void setOperation(MetadataOperationType op) {
		// TODO Auto-generated method stub

	}


}
