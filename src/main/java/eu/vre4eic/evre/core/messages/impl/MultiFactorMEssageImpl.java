/**
 * 
 */
package eu.vre4eic.evre.core.messages.impl;

import eu.vre4eic.evre.core.Common.ResponseStatus;
import eu.vre4eic.evre.core.messages.MultiFactorMessage;

/**
 * @author francesco
 *
 */
public class MultiFactorMEssageImpl extends MessageImpl implements MultiFactorMessage {

	private String authId;
	private String userId;

	/**
	 * 
	 */
	public MultiFactorMEssageImpl() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param status
	 */
	public MultiFactorMEssageImpl(String message, ResponseStatus status) {
		super(message, status);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see eu.vre4eic.evre.core.messages.MultiFactorMessage#setAuthId(java.lang.String)
	 */
	@Override
	public MultiFactorMessage setAuthId(String authId) {
		this.authId = authId;
		return this;
	}

	/* (non-Javadoc)
	 * @see eu.vre4eic.evre.core.messages.MultiFactorMessage#getAuthId()
	 */
	@Override
	public String getAuthId() {
		return authId;
	}

	/* (non-Javadoc)
	 * @see eu.vre4eic.evre.core.messages.MultiFactorMessage#setUserId(java.lang.String)
	 */
	@Override
	public MultiFactorMessage setUserId(String userId) {
		this.userId = userId;
		return this;
	}

	/* (non-Javadoc)
	 * @see eu.vre4eic.evre.core.messages.MultiFactorMessage#getUserId()
	 */
	@Override
	public String getUserId() {
		return userId;
	}

}
