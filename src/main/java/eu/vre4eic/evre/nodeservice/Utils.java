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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.vre4eic.evre.core.comm.NodeLinker;

public class Utils {
	
	
	private static Logger log = LoggerFactory.getLogger(Utils.class.getClass());
	
	public Utils(){
	}
	
	public static Properties getProperties(String resourceName){
		InputStream in;
		Properties props = new Properties();
		try {
			in = Utils.class.getClassLoader().getResourceAsStream(resourceName);
			props.load(in);
			in.close();
		} catch (FileNotFoundException e) {
			log.error(e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return props;
		
	}

	
//	public static Properties getNodeServiceProperties () {
//		if (nodeServiceProps == null)
//			try {
//				nodeServiceProps = NodeLinker.getRemoteProperties();
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				nodeServiceProps = getProperties(DEFAULT_PROPERTIES);
//			}
//		return nodeServiceProps;
//	}
	

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
