package eu.vre4eic.evre.core.messages;

import eu.vre4eic.evre.core.Common.ControlOperationType;

public interface ControlMessage extends Message {
	
	public ControlMessage setOperationType(ControlOperationType op);
	
	public ControlOperationType getOperationType();

}
