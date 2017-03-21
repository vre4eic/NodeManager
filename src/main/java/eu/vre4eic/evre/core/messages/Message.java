package eu.vre4eic.evre.core.messages;


import java.io.Serializable;

import  eu.vre4eic.evre.core.Common.ResponseStatus;

public interface Message extends Serializable{
	/**
	 * Returns a message related to the request. It is always set for FAILED status
	 * @return String - a text message
	 */
	
	public String getMessage();
	
	/**
	 * Gets the status of the executed request
	 * @return status ResponseStatus - FAILED, SUCCEED, EMPTY_RESULT, WARNING
	 */
	public ResponseStatus getStatus() ;
	
	/**
	 * Sets the status of the executed request
	 * @param status ResponseStatus - FAILED, SUCCEED, EMPTY_RESULT, WARNING
	 */
	public void setStatus(ResponseStatus status) ;

	/**
	 * Sets a message that gives a feedback about the request to the invoker method
	 * @param message String - the message to set
	 */
	public void setMessage(String message);

}
