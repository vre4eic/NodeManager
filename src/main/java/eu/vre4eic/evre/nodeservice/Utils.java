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
package eu.vre4eic.evre.nodeservice;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utils {
	
	private static Utils instance;
	private static String NS_PROPERTIES = "Nodeservice.properties";
	private static Properties property;
	
	private static Logger log = LoggerFactory.getLogger(getInstance().getClass());
	
	private Utils(){
		loadProperties(NS_PROPERTIES);
	}
	
	private void loadProperties(String resourceName){
		InputStream in;
		property = new Properties();
		try {
			in = this.getClass().getClassLoader().getResourceAsStream(resourceName);
			property.load(in);
			in.close();
		} catch (FileNotFoundException e) {
			log.error(e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		
	}

	
	public static Properties getNodeServiceProperties () {
		if (instance == null) 
			instance = new Utils();
		return property;
	}
	
	public static Utils getInstance(){
		if (instance == null) 
			instance = new Utils();
		return instance;
	
	}

	

	public static String generateToken() {
		UUID uniqueKey = UUID.randomUUID();
		return uniqueKey.toString();
	}
	
	public static String generateCode() {
		int code = RandomUtils.nextInt(100000);
		String codeStr = String.valueOf(code);	
		return StringUtils.leftPad(codeStr, 5, "0");
	}

	
	

}
