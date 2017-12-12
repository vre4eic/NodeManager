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

import java.util.Properties;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

import eu.vre4eic.evre.core.comm.NodeLinker;

@Configuration
public class MongoConfig extends AbstractMongoConfiguration {
	
  // private String profilesStorage=Utils.getNodeServiceProperties().getProperty("PROFILES_STORAGE");
  // private int profilesStoragePort=Integer.valueOf(Utils.getNodeServiceProperties().getProperty("PROFILES_STORAGE_PORT"));

    @Override
    protected String getDatabaseName() {
        return "evre";
    }
  
    @Override
    public Mongo mongo() throws Exception {
        //return new MongoClient("127.0.0.1", 27017);
    	
		Properties defaultSettings = Settings.getProperties();
		String ZkServer = defaultSettings.getProperty(Settings.ZOOKEEPER_DEFAULT);
		NodeLinker node = NodeLinker.init(ZkServer);		

        return new MongoClient(node.getProfileStorage(), node.getProfileStoragePort());
    }
  
    @Override
    protected String getMappingBasePackage() {
        return "eu.vre4eic.evre";
    }
}