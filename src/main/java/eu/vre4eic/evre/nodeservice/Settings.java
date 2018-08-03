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
	
	public static String DEFAULT_SETTINGS = "Settings.properties";
	
	public static String EVRE_TEST = "/evre/test";
	public static String EVRE_PRODUCTION = "/evre/production";
	public static String EVRE_SERVICES = "/services";



	public static String ZOOKEEPER_DEFAULT = "ZooKeeper_default";

	
	public static String VERSION_PATH="Version_path";
	public static String TOKEN_TIMEOUT_PATH="TokenTimeout_path";
	public static String TOKEN_SECRET_PATH="TokenSecret_path";
	public static String CODE_TIMEOUT_PATH="CodeTimeout_path";
	public static String MESSAGE_BROKER_PATH="MessageBroker_path";
	public static String PROFILES_STORAGE="ProfileStorage_path";
	public static String PROFILES_STORAGE_PORT="ProfileStoragePort_path";
	public static String AAAI_LOGIN="AAAI_login";
	public static String AAAI_PWD="AAAI_pwd";
	
	public static String VERSION_DEFAULT="Version_default";
	public static String TOKEN_TIMEOUT_DEFAULT="TokenTimeout_default";
	public static String TOKEN_SECRET_DEFAULT="TokenSecret_default";
	public static String CODE_TIMEOUT_DEFAULT="CodeTimeout_default";
	public static String MESSAGE_BROKER_DEFAULT="MessageBroker_default";
	
	public static String PROFILES_STORAGE_DEFAULT="ProfileStorage_default";
	public static String PROFILES_STORAGE_PORT_DEFAULT="ProfileStoragePort_default";
	
	public static String AAAI_LOGIN_DEFAULT="AAAI_login_default";
	
	public static String AAAI_PWD_DEFAULT="AAAI_pwd_default";
	private static Properties settingProps; 
	
	public Settings(){
	}
	
	public static Properties getProperties(){
		if (settingProps == null)
			settingProps = Utils.getProperties(DEFAULT_SETTINGS);
		return settingProps;
	}

}
