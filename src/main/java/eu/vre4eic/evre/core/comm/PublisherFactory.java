/*******************************************************************************
 * Copyright (c) 2017 VRE4EIC Consortium
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
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
