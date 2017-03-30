package eu.vre4eic.evre.core.comm;

import eu.vre4eic.evre.core.Common.Topics;
import eu.vre4eic.evre.core.messages.AuthenticationMessage;
import eu.vre4eic.evre.core.messages.MetadataMessage;

public class PublisherFactory {

	static Publisher<AuthenticationMessage> authenticationPublisher;
	static Publisher<MetadataMessage> metadataPublisher;

	public static  Publisher<AuthenticationMessage> getAuthenticationPublisher() {
		if ( authenticationPublisher == null)
			authenticationPublisher = new Publisher<AuthenticationMessage>(Topics.AUTH_Channel);
		return authenticationPublisher;	
	}
	
	public static  Publisher<MetadataMessage> getMetatdaPublisher() {
		if ( metadataPublisher == null)
			metadataPublisher = new Publisher<MetadataMessage>(Topics.METADATA_OP_Channel);
		return metadataPublisher;	
	}

}
