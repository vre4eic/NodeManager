/**
 * 
 */
package eu.vre4eic.evre.core.messages;

/**
 * @author francesco
 *
 */
public interface MultiFactorMessage extends Message {
	
	public MultiFactorMessage setAuthId(String authId);
		
	public String getAuthId();
	
	public MultiFactorMessage setUserId(String userId);
	
	public String getUserId();
	
	public MultiFactorMessage setCode(String code);
	
	public String getCode();
}
