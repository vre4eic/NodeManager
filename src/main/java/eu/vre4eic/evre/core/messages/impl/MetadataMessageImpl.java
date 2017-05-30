/**
 * 
 */
package eu.vre4eic.evre.core.messages.impl;



import org.json.JSONObject;

import eu.vre4eic.evre.core.Common.MetadataOperationType;
import eu.vre4eic.evre.core.Common.ResponseStatus;
import eu.vre4eic.evre.core.messages.Message;
import eu.vre4eic.evre.core.messages.MetadataMessage;

/**
 * @author francesco
 *
 */
public class MetadataMessageImpl extends MessageImpl implements MetadataMessage {
	
	
	
	private String token;
	private MetadataOperationType operation;
	private JSONObject jsonMessage;

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
		return token;
	}

	/* (non-Javadoc)
	 * @see eu.vre4eic.evre.core.messages.MetadataMessage#setToken(java.lang.String)
	 */
	@Override
	public MetadataMessage setToken(String token) {
		this.token = token;
		return this;

	}

	/* (non-Javadoc)
	 * @see eu.vre4eic.evre.core.messages.MetadataMessage#getOperation()
	 */
	@Override
	public MetadataOperationType getOperation() {
		return operation;
	}

	/* (non-Javadoc)
	 * @see eu.vre4eic.evre.core.messages.MetadataMessage#setOperation(eu.vre4eic.evre.core.Common.MetadataOperationType)
	 */
	@Override
	public MetadataMessage setOperation(MetadataOperationType op) {
		this.operation = op;
		return this;
	}
	public String toJSON() {
        org.json.JSONObject object = new org.json.JSONObject();
        try{
        object.put("token", this.token);
        object.put("operation", this.operation.toString());
       // object.put("message", this.message);
        object.put("status", ""+this.status);
        object.put("message", jsonMessage.toString());
        }
        catch(Exception e){
        	e.printStackTrace();
        }
        return object.toString();
    }

	@Override
	public void setJsonMessage(JSONObject message) {
		this.jsonMessage=message;
		
	}

	@Override
	public JSONObject getJsonMessage() {
		
		return jsonMessage;
	}


}
