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
import eu.vre4eic.evre.core.messages.impl.AuthenticationMessageImpl;
import eu.vre4eic.evre.core.messages.impl.MessageImpl;
import eu.vre4eic.evre.nodeservice.comm.impl.Publisher;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import eu.vre4eic.evre.core.Common.UserRole;
import eu.vre4eic.evre.nodeservice.usermanager.dao.UserProfileRepository;


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
	
	
	
	
	public UserController()  {
		super();
		
	}

	@ApiOperation(value = "Creates a user profile on e-VRE", 
	        notes = "Creates a  user profile on e-VRE", response = Message.class)
	@RequestMapping(value="/user/createprofile", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	
	public  Message createUserProfile(@RequestParam(value="userid") String userId, @RequestParam(value="name") String name, 
			@RequestParam(value="email") String email, @RequestParam(value="role") UserRole role, 
			@RequestParam(value="password") String password, @RequestParam(value="snsid") String snsId, 
			@RequestParam(value="authid") String authId) {
		
		
		repository.save(new EVREUserProfile(userId, password, name, role, email,snsId, authId));
		System.out.println("Users found with findAll():");
		System.out.println("-------------------------------");
		for (EVREUserProfile userp : repository.findAll()) {
			System.out.println(userp);
		}
		System.out.println();
		
		return( new MessageImpl("Operation completed", Common.ResponseStatus.SUCCEED));
		 
	}

	@ApiOperation(value = "Updates the information in a profile of a user ", 
	        notes = "Updates the profile of the user identified by 'userid'. It can be invoked by a user to update her/his profile or by an adminstrator to update a profile of one registered user", 
	        response = Message.class)
	
	@RequestMapping(value="/user/updateprofile", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Message updateUserProfile(@RequestParam(value="token") String token, @RequestParam(value="userid") String userId, @RequestParam(value="name") String name, 
			@RequestParam(value="email") String email, @RequestParam(value="role") UserRole role, 
			@RequestParam(value="password") String password, @RequestParam(value="snsid") String snsId, 
			@RequestParam(value="authid") String authId){
		
		if (repository.findByUserId(userId)!=null){
			repository.save(new EVREUserProfile(userId, password, name, role, email,snsId, authId));
			
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
		Publisher p;
		AuthenticationMessage m = new AuthenticationMessageImpl();
		try {
			p = Publisher.getInstance();
			
			// fake request to AAAI service
//			Random rnd = new Random();
//			int token = rnd.nextInt(1000000);
			String token = pwd;

			m.setToken(token);
			
			// to do clock synchronization
			int TTL = 5;
			LocalDateTime timeLimit = LocalDateTime.now().plusMinutes(TTL);
			m.setTimeZone(ZoneId.systemDefault());
			m.setTimeLimit(timeLimit);
						
			// fake Role
			m.setRole(UserRole.OPERATOR);
			
			// publish message on authentication topic
			p.publishAuthentication(m);

			
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return m;
	}

	@ApiOperation(value = "Sign off a user", 
	        notes = "The user exit the e-VRE and the assignedz token is no longer valid", 
	        response = Message.class)
	
	@RequestMapping(value="/user/logout",  method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)

	public Message logout(@RequestParam(value="token") String token) {
		
		Publisher p;
		AuthenticationMessage m = new AuthenticationMessageImpl();
		try {
			p =  Publisher.getInstance();
			
			m.setToken(token);
			
			LocalDateTime timeLimit = LocalDateTime.MIN;
			
			m.setTimeZone(ZoneId.systemDefault());
			m.setTimeLimit(timeLimit);
						
			// fake Role
			m.setRole(UserRole.OPERATOR);
			
			// publish message on authentication topic
			p.publishAuthentication(m);

			
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
