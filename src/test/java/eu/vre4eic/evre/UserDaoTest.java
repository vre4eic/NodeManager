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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import eu.vre4eic.evre.core.impl.EVREUserProfile;
import eu.vre4eic.evre.nodeservice.Utils;
import eu.vre4eic.evre.nodeservice.modules.authentication.AuthModule;
import eu.vre4eic.evre.nodeservice.services.UserController;
import eu.vre4eic.evre.nodeservice.usermanager.dao.UserProfileRepository;

//@ContextConfiguration ("/Test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringMongoConfig.class)

public class UserDaoTest {

	
	@Autowired
	private UserProfileRepository repository;
	
	
	private UserController userWS;
	

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
		
		
	}
	/*
	 * Test the insert of a new User profile
	 */
	@Test
	public final void testInsertUserProfile2() {	
		repository.save(new EVREUserProfile("userId", "userPWD", "Name", eu.vre4eic.evre.core.Common.UserRole.RESEARCHER, 
				"email@domain","snsId", "authId"));
		List<EVREUserProfile> userp = (List<EVREUserProfile>) repository.findAll();
		assertEquals((userp.get(0)).getUserId(), "userId");	
		
	}
	/*
	 * Test the login logout
	 */
	@Test
	public final void testLoginLogout() {	
		
		
		userWS=new UserController();
		//userWS.login("userId", "userPWD");
		Properties property = Utils.getNodeServiceProperties();
		String brokerURL =  property.getProperty("BROKER_URL");
		
		module = AuthModule.getInstance(brokerURL);
		
		// executes a login
		userWS.login("userid", "userPWD");
		//wait for authentication message being dispatched
		
		
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		//check if token is valid
		Boolean auth=module.checkToken("userPWD");
		assertEquals(auth, true);
		
		//logout
		userWS.logout("userPWD");
		//wait for authentication message being dispatched
		
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		//check if token is no longer valid
		auth=module.checkToken("userPWD");
		assertEquals(auth, false);
		
	}
	
}
