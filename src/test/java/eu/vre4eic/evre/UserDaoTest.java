package eu.vre4eic.evre;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import eu.vre4eic.evre.core.Common;
import eu.vre4eic.evre.core.UserCredentials;
import eu.vre4eic.evre.core.UserProfile;
import eu.vre4eic.evre.core.impl.EVREUserProfile;
import eu.vre4eic.evre.core.impl.UserCredentialsImpl;
import eu.vre4eic.evre.core.messages.Message;
import eu.vre4eic.evre.nodeservice.Utils;
import eu.vre4eic.evre.nodeservice.modules.authentication.AuthModule;
import eu.vre4eic.evre.nodeservice.services.UserController;
import eu.vre4eic.evre.nodeservice.usermanager.dao.UserProfileRepository;
import eu.vre4eic.evre.nodeservice.usermanager.impl.UserManagerImpl;

//@ContextConfiguration ("/Test-context.xml")
@ComponentScan
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringMongoConfig.class, UserManagerImpl.class}, 
loader = SpringBootContextLoader.class)

public class UserDaoTest {

	
	
	
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
	public final void testInsertUserProfile() {
		
		repository.save(new EVREUserProfile("userId", "userPWD", "Name", eu.vre4eic.evre.core.Common.UserRole.RESEARCHER, 
				"email@domain","snsId", "authId"));
		List<EVREUserProfile> userp = (List<EVREUserProfile>) repository.findAll();
		
		assertEquals(userp.size(), 1);
		assertEquals((userp.get(0)).getUserId(), "userId");
		
		
	}
	/*
	 * Test the insert of a new User profile
	 */
	@Test
	public final void testInsertUserProfile2() {	
		repository.save(new EVREUserProfile("userId", "userPWD", "Name", eu.vre4eic.evre.core.Common.UserRole.RESEARCHER, 
				"email@domain","snsId", "authId"));
		List<EVREUserProfile> userp = (List<EVREUserProfile>) repository.findByPassword("userPWD");
		assertEquals(userp.get(0).getUserId(), "userId");
		
		
	}
	/*
	 * Test the login logout
	 */
	@Test
	public final void testLoginLogout() {	
		
		Properties property = Utils.getNodeServiceProperties();
		String brokerURL =  property.getProperty("BROKER_URL");
		
		module = AuthModule.getInstance(brokerURL);
		//save a user profile
		Message mes=userMI.createUserProfile(new EVREUserProfile("userId", "userPWD", "Name", eu.vre4eic.evre.core.Common.UserRole.RESEARCHER, 
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
	public final void testUpdateUserProfile() {	
		
		Properties property = Utils.getNodeServiceProperties();
		String brokerURL =  property.getProperty("BROKER_URL");
		
		module = AuthModule.getInstance(brokerURL);
		//save a user profile
		Message mes=userMI.createUserProfile(new EVREUserProfile("userId", "userPWD", "Name", eu.vre4eic.evre.core.Common.UserRole.RESEARCHER, 
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
		mes=userMI.updateUserProfile("userId", new EVREUserProfile("userId", "userPWDupdate", "Nameupdate", eu.vre4eic.evre.core.Common.UserRole.CONTROLLER, 
				"email@domain","snsId", "authId"));
		assertEquals(Common.ResponseStatus.SUCCEED, mes.getStatus());
		EVREUserProfile eup= userMI.getUserProfile("userId");
		assertEquals("userPWDupdate", eup.getPassword());
		
	}
}
