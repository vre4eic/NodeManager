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
package eu.vre4eic.evre;

import static org.junit.Assert.assertEquals;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import eu.vre4eic.evre.core.Common;
import eu.vre4eic.evre.core.UserCredentials;
import eu.vre4eic.evre.core.Common.MetadataOperationType;
import eu.vre4eic.evre.core.Common.ResponseStatus;
import eu.vre4eic.evre.core.Common.Topics;
import eu.vre4eic.evre.core.comm.NodeLinker;
import eu.vre4eic.evre.core.comm.Publisher;
import eu.vre4eic.evre.core.comm.PublisherFactory;
import eu.vre4eic.evre.core.impl.EVREUserProfile;
import eu.vre4eic.evre.core.impl.UserCredentialsImpl;
import eu.vre4eic.evre.core.messages.Message;
import eu.vre4eic.evre.core.messages.MetadataMessage;
import eu.vre4eic.evre.core.messages.impl.MetadataMessageImpl;
import eu.vre4eic.evre.nodeservice.Settings;
import eu.vre4eic.evre.nodeservice.modules.authentication.AuthModule;
import eu.vre4eic.evre.nodeservice.usermanager.dao.UserProfileRepository;
import eu.vre4eic.evre.nodeservice.usermanager.impl.UserManagerImpl;

//@ContextConfiguration ("/Test-context.xml")
@ComponentScan
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringMongoConfig.class, UserManagerImpl.class}, 
loader = SpringBootContextLoader.class)

public class MetadataDaoTest {

	
	
	
	@Autowired
	private UserProfileRepository repository;
	
	@Autowired
	private UserManagerImpl userMI;
	

	private AuthModule module;
	
	//@Autowired
	//private CustomerPreferences customerPreference;
	
	@Before
	public void setUp() throws Exception {
		repository.deleteAll();
	}

	
/*
 * Test the insert of a new User profile
 */
	@Test
	public final void testPublishMetadataMessage() {
		
		Publisher<MetadataMessage> mdp =  PublisherFactory.getMetatdaPublisher();
		MetadataMessage mdm = new MetadataMessageImpl("test message", ResponseStatus.SUCCEED)
				.setToken("testtoken")
				.setOperation(MetadataOperationType.QUERY);
			mdp.publish(mdm);
		
		
		assertEquals(Topics.values().length, 4);
		//assertEquals((userp.get(0)).getUserId(), "userId");
		
		
	}
	
	/*
	 * Test the login logout
	 */
	//@Test
	public final void testLoginLogout() {	
		
		Properties defaultSettings = Settings.getProperties();
		String ZkServer = defaultSettings.getProperty(Settings.ZOOKEEPER_DEFAULT);
		NodeLinker node = NodeLinker.init(ZkServer);		
		String messageBrokerURL =  node.getMessageBrokerURL();
		
		module = AuthModule.getInstance(messageBrokerURL);
		//save a user profile
		Message mes=userMI.createUserProfile(new EVREUserProfile("userId", "userPWD", "Name", "my_organization", "myURL://bean", eu.vre4eic.evre.core.Common.UserRole.RESEARCHER, 
				"email@domain","snsId", "authId"));
		assertEquals(Common.ResponseStatus.SUCCEED, mes.getStatus());
		// execute a login
		UserCredentials uc= new UserCredentialsImpl("userId", "userPWD");
	
		userMI.login(uc);
		//wait for authentication message being dispatched
		
		
		try {
			TimeUnit.MILLISECONDS.sleep(50);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		//check if token is valid
		Boolean auth=module.checkToken("userPWD");
		assertEquals(auth, true);
		
		//logout
		userMI.logout("userPWD");
		//wait for authentication message being dispatched
		
		try {
			
			TimeUnit.MILLISECONDS.sleep(50);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		//check if token is no longer valid
		auth=module.checkToken("userPWD");
		assertEquals(auth, false);
		
	}
	@Test
		public final void testCreateMetadataMessage() {
		
		MetadataMessageImpl mm = new MetadataMessageImpl();
		
		JSONArray ja= new JSONArray();
		JSONObject jo= new JSONObject();
		try {
			jo.put("milliseconds", "12");
			jo.put("modified", "2017");
			ja.put(jo);
			JSONObject mainObj = new JSONObject();
			mainObj.put("data", jo);
			mm.setJsonMessage(mainObj);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		mm.setMessage("test");
		mm.setOperation(Common.MetadataOperationType.INSERT);
		mm.setStatus(Common.ResponseStatus.SUCCEED);
		mm.setToken("mytoken");
		System.out.println(mm.toJSON());
		assertEquals(mm.getToken(), "mytoken");
		
			
		}
	
}
