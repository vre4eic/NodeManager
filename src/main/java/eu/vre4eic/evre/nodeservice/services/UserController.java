package eu.vre4eic.evre.nodeservice.services;



import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;











import javax.jms.JMSException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eu.vre4eic.evre.core.Common;
import eu.vre4eic.evre.core.EvreEvent;
import eu.vre4eic.evre.core.UserProfile;
import eu.vre4eic.evre.core.impl.EVREUserProfile;
import eu.vre4eic.evre.core.messages.AuthenticationMessage;
import eu.vre4eic.evre.core.messages.Message;
import eu.vre4eic.evre.core.messages.MultiFactorMessage;
import eu.vre4eic.evre.core.messages.impl.AuthenticationMessageImpl;
import eu.vre4eic.evre.core.messages.impl.MessageImpl;
import eu.vre4eic.evre.core.messages.impl.MultiFactorMessageImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import eu.vre4eic.evre.core.Common.UserRole;
import eu.vre4eic.evre.nodeservice.Utils;
import eu.vre4eic.evre.core.comm.Publisher;
import eu.vre4eic.evre.core.comm.PublisherFactory;
import eu.vre4eic.evre.nodeservice.usermanager.dao.UserProfileRepository;
import eu.vre4eic.evre.nodeservice.usermanager.impl.UserManagerImpl;


/**
 * This class contains methods for managing users. 
 * @author Cesare
 *
 */


@RestController
@Api(value = "User management")

public class UserController {

	@Autowired
	private UserProfileRepository repository;

	@Autowired
	private UserManagerImpl userManager;


	public UserController()  {
		super();

	}

	@ApiOperation(value = "Creates a user profile on e-VRE", 
			notes = "Creates a  user profile on e-VRE", response = Message.class)
	@RequestMapping(value="/user/createprofile", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)

	public  Message createUserProfile(@RequestParam(value="userid") String userId, @RequestParam(value="name") String name, 
			@RequestParam(value="email") String email, @RequestParam(value="organization") String organization, @RequestParam(value="role") UserRole role, 
			@RequestParam(value="password") String password) {


		userManager.createUserProfile(new EVREUserProfile(userId, password, name, organization, role, email, "0", "0"));
		//repository.save(new EVREUserProfile(userId, password, name, role, email,snsId, authId));
		System.out.println("UC: Users found with findAll():");
		System.out.println("-------------------------------");
		for (UserProfile userp : repository.findAll()) {
			System.out.println(userp);
		}
		System.out.println();
		System.out.println("-------------------------------");
		System.out.println(repository.findByRole(Common.UserRole.RESEARCHER));

		return( new MessageImpl("Operation completed", Common.ResponseStatus.SUCCEED));

	}

	@ApiOperation(value = "Updates the information in a profile of a user ", 
			notes = "Updates the profile of the user identified by 'userid'. It can be invoked by a user to update her/his profile or by an adminstrator to update a profile of one registered user", 
			response = Message.class)

	@RequestMapping(value="/user/updateprofile", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Message updateUserProfile(@RequestParam(value="token") String token, @RequestParam(value="userid") String userId, @RequestParam(value="name") String name, 
			@RequestParam(value="email") String email, @RequestParam(value="organization") String organization, @RequestParam(value="role") UserRole role, 
			@RequestParam(value="password") String password){

		if (repository.findOne(userId)!=null){
			repository.save(new EVREUserProfile(userId, password, name, organization, role, email,"0", "0"));

			return( new MessageImpl("Operation completed", Common.ResponseStatus.SUCCEED));
		}


		return( new MessageImpl("User Profile not found", Common.ResponseStatus.FAILED));
	}

	@ApiOperation(value = "Removes the profile of a user from e-VRE", 
			notes = "A user with a valid token can invoke this web service to remove her/his profile from e-VRE", 
			response = Message.class)

	@RequestMapping(value="user/removemyprofile", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Message unRegister(@RequestParam(value="token") String token) {

		return null;
	}

	@ApiOperation(value = "Authenticates a user ", 
			notes = "Authenticates a user on e-VRE system. Returns the token that will be used by user client in the interactions with the e-VRE services.", 
			response = AuthenticationMessage.class)
	@RequestMapping(value="/user/login", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)

	public AuthenticationMessage login(@ApiParam(name = "username", value = "Alphanumeric string", required = true) @RequestParam(value="username") String username, @ApiParam(name = "pwd", value = "Alphanumeric string", required = true) @RequestParam(value="pwd") String pwd) {
		Publisher<AuthenticationMessage> p =  PublisherFactory.getAuthenticationPublisher();
		AuthenticationMessage m = new AuthenticationMessageImpl();

		String token = pwd;

		m.setToken(token);

		// to do clock synchronization
		String TTL = Utils.getNodeServiceProperties().getProperty("TOKEN_TIMEOUT");
		LocalDateTime timeLimit = LocalDateTime.now().plusMinutes(Integer.valueOf(TTL));
		m.setTimeZone(ZoneId.systemDefault().getId())
		.setTimeLimit(timeLimit)
		.setRenewable(TTL);

		// fake Role
		m.setRole(UserRole.OPERATOR);

		// publish message on authentication topic
		p.publish(m);



		return m;
	}

	@ApiOperation(value = "Authenticates a user with a <i>Two-factor authentication</i> procedure ", 
			notes = "Authenticates a user on e-VRE system. Returns the URL to a WS that must be used to verify the second authentication factor.", 
			response = Message.class)
	@RequestMapping(value="/user/loginmfa", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)

	public Message loginMfa(@ApiParam(name = "username", value = "Alphanumeric string", required = true) @RequestParam(value="username") String username, 
			@ApiParam(name = "pwd", value = "Alphanumeric string", required = true) @RequestParam(value="pwd") String pwd) {
	
		return userManager.loginMFA(username, pwd);
	}

	@ApiOperation(value = "Sign off a user", 
			notes = "The user exit the e-VRE and the assignedz token is no longer valid", 
			response = Message.class)

	@RequestMapping(value="/user/logout",  method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)

	public Message logout(@RequestParam(value="token") String token) {

		Publisher<AuthenticationMessage> p =  PublisherFactory.getAuthenticationPublisher();
		AuthenticationMessage m = new AuthenticationMessageImpl();

		m.setToken(token);

		LocalDateTime timeLimit = LocalDateTime.MIN;

		m.setTimeZone(ZoneId.systemDefault().getId());
		m.setTimeLimit(timeLimit);

		// fake Role
		m.setRole(UserRole.OPERATOR);

		// publish message on authentication topic
		p.publish(m);


		return m;

	}

	@ApiOperation(value = "An administrator removes the profile of a user from e-VRE ", 
			notes = "A user with admin permissions can invoke this web service to remove an user profile from e-VRE", 
			response = Message.class)

	@RequestMapping(value="/user/removeprofile", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Message removeUserProfile(@RequestParam(value="token") String token, @RequestParam(value="id") String userId) {

		return null;
	}

	@ApiOperation(value = "Gets a user profile based on user id", 
			notes = "Retrieves a single user profile", response = UserProfile.class)
	@RequestMapping(value= "/user/getprofile", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public UserProfile getUserProfile(@RequestParam(value="token") String token, @RequestParam(value="userLogin") String userId) {

		return null;
	}

	@ApiOperation(value = "Gets a list of user profiles", 
			notes = "Retrieves a list of user profiles, can be invoked by users with Administrator role",
			response = UserProfile.class,
			responseContainer="List")
	@RequestMapping(value= "/user/getprofiles", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<UserProfile> getUserProfiles(@RequestParam(value="token") String token) {

		return null;
	}

	@ApiOperation(value = "Subscribes to a list of e-VRE events", 
			notes = "A user with a valid token can invoke this web service to subscribe to a list of e-VRE events", 
			response = Message.class)
	@RequestMapping(value="/user/subscribeevents", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)

	public Message subscribeEvent(@RequestParam(value="token") String token, @RequestParam(value="userid") String userId, @RequestParam(value="events")List <EvreEvent> events){
		return null;

	}

	@ApiOperation(value = "Unsubscribes from a list of e-VRE events ", 
			notes = "A user with a valid token can invoke this web service to unsubscribe from a list of e-VRE events", 
			response = Message.class)

	@RequestMapping(value="/user/unsubscribeevents", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)

	public Message unSubscribeEvent(@RequestParam(value="token") String token, @RequestParam(value="userid") String idUser, @RequestParam(value="events") List <EvreEvent> events){
		return null;
	}

	@ApiOperation(value = "Checks the status of an e-VRE event ", 
			notes = "A user with a valid token can invoke this web service to check if an event she/he is subscribed on has changed status", 
			response = Message.class)

	@RequestMapping(value="/user/checkevent", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Message checkEvent(@RequestParam(value="token") String token, @RequestParam(value="userid") String userId, @RequestParam(value="eventid") String eventId){
		return null;
	}

	@ApiOperation(value = "Checks the status of all subscribed e-VRE events ", 
			notes = "A user with a valid token can invoke this web service to check if events she/he is subscribed on have changed status", 
			response = Message.class)
	@RequestMapping(value="/user/checkevents", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List <Message> checkEvent(@RequestParam(value="token") String token, @RequestParam(value="userid") String userId){
		return null;
	}

	@ApiOperation(value = "Returns the list of  e-VRE events ", 
			notes = "A user with a valid token can invoke this web service to obtain the list of events she/he is subscribed on", 
			response = EvreEvent.class)
	@RequestMapping(value="/user/getevents", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<EvreEvent> getSubscribedEvents (@RequestParam(value="token") String token, @RequestParam(value="userid") String userId){
		return null;
	}




}
