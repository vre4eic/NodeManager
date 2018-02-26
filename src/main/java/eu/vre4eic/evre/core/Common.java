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
/**
 * 
 */
package eu.vre4eic.evre.core;

/**
 * Enum types common to all classes
 * @author Cesare
 *
 */
public class Common {
	
	/**
	 * UserRole: <ul><li>RESEARCHER</li> <li>OPERATOR</li> <li>ADMIN</li> <li>CONTROLLER</li></ul>
	 *
	 */
	public enum UserRole {
		RESEARCHER, OPERATOR, ADMIN, CONTROLLER
	}
	
	/**
	 * ResponseStatus: <ul><li>SUCCEED</li> <li>EMPTY_RESULT</li> <li>WARNING</li> <li>FAILED</li><li>IN_PROGRESS</li></ul>
	 *
	 */
	public enum ResponseStatus {
		SUCCEED, EMPTY_RESULT, WARNING, FAILED, IN_PROGRESS
	}
	
	/**
	 * ServiceStatus: <ul><li>Start</li> <li>Run</li> <li>Error</li> <li>Stop</li></ul>
	 *
	 */
	public enum ServiceStatus {
		START, RUN, ERROR, STOP
	}
	
	/**
	 * MetadataOperationType: <ul><li>Read</li> <li>Query</li> <li>Insert</li><li>Update</li><li>Delete</li></ul>
	 */
	public enum MetadataOperationType{
		READ,QUERY,INSERT,UPDATE, DELETE
	}
	/**
	 * NotificationType: <ul><li>Authentication</li> <li>Metadata</li><li>UserProfile</li> <li>Service</li></ul>
	 */
	public enum NotificationType{
		AUTHENTICATION, METADATA,USERPROFILE,SERVICE
	}
	
		
	public enum Topics {
		AUTH_Channel, METADATA_OP_Channel, CONTROL_Channel, MFA_Channel;

		
	}
	
	public enum ControlOperationType {
		PRINT_STATUS,PING,SHOW_WARNING;
		
	}

	
}
