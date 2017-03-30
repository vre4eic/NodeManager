package eu.vre4eic.evre.core.comm;

import eu.vre4eic.evre.core.Common.Topics;
import eu.vre4eic.evre.core.messages.AuthenticationMessage;
import eu.vre4eic.evre.core.messages.MetadataMessage;

public class SubscriberFactory {

	static Subscriber<AuthenticationMessage> authenticationSubscriber;
	static Subscriber<MetadataMessage> metadataSubscriber;

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

}
