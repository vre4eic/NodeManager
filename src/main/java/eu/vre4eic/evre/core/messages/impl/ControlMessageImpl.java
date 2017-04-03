/**
 * 
 */
package eu.vre4eic.evre.core.messages.impl;

import eu.vre4eic.evre.core.Common.ControlOperationType;
import eu.vre4eic.evre.core.Common.ResponseStatus;
import eu.vre4eic.evre.core.messages.ControlMessage;

/**
 * @author francesco
 *
 */
public class ControlMessageImpl extends MessageImpl implements ControlMessage {

	private ControlOperationType op;

	/**
	 * 
	 */
	public ControlMessageImpl() {
		super();
	}

	/**
	 * @param message
	 * @param status
	 */
	public ControlMessageImpl(String message, ResponseStatus status) {
		super(message, status);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see eu.vre4eic.evre.core.messages.ControlMessage#setOperationType(eu.vre4eic.evre.core.Common.ControlOperationType)
	 */
	@Override
	public ControlMessage setOperationType(ControlOperationType op) {
		this.op = op;
		return this;
	}

	@Override
	public ControlOperationType getOperationType() {
		// TODO Auto-generated method stub
		return op;
	}

}
