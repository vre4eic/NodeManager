/*******************************************************************************
 * Copyright (c) 2018 VRE4EIC Consortium
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
