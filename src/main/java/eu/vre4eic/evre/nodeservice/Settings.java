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
package eu.vre4eic.evre.nodeservice;

import java.util.Properties;

public class Settings {
	

	public static final String DEFAULT_SETTINGS = "Settings.properties";
	
	public static final String EVRE_TEST = "/evre/test";
	public static final String EVRE_PRODUCTION = "/evre/production";
	public static final String NODE_SERVICE = "/NodeService";
	public static final String SERVICES = "/Services";



	public static final String ZOOKEEPER_DEFAULT = "ZooKeeper_default";

	
	public static final String VERSION_PATH="Version_path";
	public static final String VERSION_DEFAULT="Version_default";

	public static final String TOKEN_TIMEOUT_PATH="TokenTimeout_path";
	public static final String TOKEN_TIMEOUT_DEFAULT="TokenTimeout_default";
	public static final String CODE_TIMEOUT_PATH="CodeTimeout_path";
	public static final String CODE_TIMEOUT_DEFAULT="CodeTimeout_default";

	public static final String SECRET_PATH = "Secret_path";;
	public static final String SECRET_DEFAULT = "Secret_default";;

	public static final String MESSAGE_BROKER_URL_PATH="MessageBrokerURL_path";
	public static final String MESSAGE_BROKER_URL_DEFAULT="MessageBrokerURL_default";

	public static final String PROFILES_STORAGE_URL_PATH="ProfileStorageURL_path";
	public static final String PROFILES_STORAGE_URL_DEFAULT="ProfileStorageURL_default";
	public static final String PROFILES_STORAGE_CRED_PATH="ProfileStorageCred_path";
	public static final String PROFILES_STORAGE_CRED_DEFAULT="ProfileStorageCred_default";
	
	public static final String AAAI_URL_PATH="AAAI_URL_path";
	public static final String AAAI_URL_DEFAULT="AAAI_URL_default";
	public static final String AAAI_CRED_PATH="AAAI_Cred_path";
	public static final String AAAI_CRED_DEFAULT="AAAI_Cred_default";
	
		
	private static Properties settingProps; 
	
	public Settings(){
	}
	
	public static Properties getProperties(){
		if (settingProps == null)
			settingProps = Utils.getProperties(DEFAULT_SETTINGS);
		return settingProps;
	}

}
