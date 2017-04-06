package eu.vre4eic.evre.core.comm;

import eu.vre4eic.evre.core.Common.Topics;
import eu.vre4eic.evre.core.messages.AuthenticationMessage;
import eu.vre4eic.evre.core.messages.ControlMessage;
import eu.vre4eic.evre.core.messages.MetadataMessage;
import eu.vre4eic.evre.core.messages.MultiFactorMessage;

public class PublisherFactory {

	static Publisher<AuthenticationMessage> authenticationPublisher;
	static Publisher<MetadataMessage> metadatalPublisher;
	static Publisher<ControlMessage> controlPublisher;
	static Publisher<MultiFactorMessage> mfaPublisher;

	public static  Publisher<AuthenticationMessage> getAuthenticationPublisher() {
		if ( authenticationPublisher == null)
			authenticationPublisher = new Publisher<AuthenticationMessage>(Topics.AUTH_Channel);
		return authenticationPublisher;	
	}

	public static  Publisher<AuthenticationMessage> getAuthenticationPublisher(String brokerURL) {
		if ( authenticationPublisher == null)
			authenticationPublisher = new Publisher<AuthenticationMessage>(brokerURL, Topics.AUTH_Channel);
		return authenticationPublisher;	
	}
	
	public static  Publisher<MetadataMessage> getMetatdaPublisher() {
		if ( metadatalPublisher == null)
			metadatalPublisher = new Publisher<MetadataMessage>(Topics.METADATA_OP_Channel);
		return metadatalPublisher;	
	}

	public static  Publisher<ControlMessage> getControlPublisher() {
		if ( controlPublisher == null)
			controlPublisher = new Publisher<ControlMessage>(Topics.CONTROL_Channel);
		return controlPublisher;	
	}
	public static  Publisher<MultiFactorMessage> getMFAPublisher() {
		if ( controlPublisher == null)
			mfaPublisher = new Publisher<MultiFactorMessage>(Topics.MFA_Channel);
		return mfaPublisher;	
	}

}
