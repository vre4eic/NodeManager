package eu.vre4eic.evre.core.comm;

import eu.vre4eic.evre.core.Common.Topics;
import eu.vre4eic.evre.core.messages.AuthenticationMessage;
import eu.vre4eic.evre.core.messages.ControlMessage;
import eu.vre4eic.evre.core.messages.MetadataMessage;
import eu.vre4eic.evre.core.messages.MultiFactorMessage;

public class SubscriberFactory {

	static Subscriber<AuthenticationMessage> authenticationSubscriber;
	static Subscriber<MetadataMessage> metadataSubscriber;
	static Subscriber<ControlMessage> controlSubscriber;
	static Subscriber<MultiFactorMessage> mfaSubscriber;

	public static  Subscriber<AuthenticationMessage> getAuthenticationSubscriber() {
		if ( authenticationSubscriber == null)
			authenticationSubscriber = new Subscriber<AuthenticationMessage>(Topics.AUTH_Channel);
		return authenticationSubscriber;	
	}
	
	public static  Subscriber<MetadataMessage> getMetadataSubscriber() {
		if ( metadataSubscriber == null)
			metadataSubscriber = new Subscriber<MetadataMessage>(Topics.METADATA_OP_Channel);
		return metadataSubscriber;	
	}
	
	public static  Subscriber<ControlMessage> getControlSubscriber() {
		if ( controlSubscriber == null)
			controlSubscriber = new Subscriber<ControlMessage>(Topics.CONTROL_Channel);
		return controlSubscriber;	
	}
	public static  Subscriber<MultiFactorMessage> getMFASubscriber() {
		if ( controlSubscriber == null)
			mfaSubscriber = new Subscriber<MultiFactorMessage>(Topics.MFA_Channel);
		return mfaSubscriber;	
	}

}
