package eu.vre4eic.evre.nodeservice.services;



import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eu.vre4eic.evre.core.EvreEvent;
import eu.vre4eic.evre.core.UserProfile;
import eu.vre4eic.evre.core.messages.Message;
import eu.vre4eic.evre.core.Common.UserRole;

/**
 * This class contains methods for managing users. 
 * @author Cesare
 *
 */

@RestController
public class UserController {

	public UserController()  {
		super();
	}

	@RequestMapping(value="/user/createprofile", method = RequestMethod.POST)
	public  Message createUserProfile(@RequestParam(value="login") String login, @RequestParam(value="name") String name, 
			@RequestParam(value="email") String email, @RequestParam(value="role") UserRole role, 
			@RequestParam(value="nickname") String nickName, @RequestParam(value="password") String password) {
		
		return null;
	}

	@RequestMapping(value="/user/updateprofile", method = RequestMethod.POST)
	public Message updateUserProfile(@RequestParam(value="token") String token, @RequestParam(value="userId") String userId, @RequestParam(value="login") String login, @RequestParam(value="name") String name, 
			@RequestParam(value="email") String email, @RequestParam(value="role") UserRole role, 
			@RequestParam(value="nickname") String nickName, @RequestParam(value="password") String password){
		return null;
	}
	
	@RequestMapping("/user/deleteprofile")
	public Message unRegister(@RequestParam(value="token") String token) {
		
		return null;
	}
	
	@RequestMapping("/user/login")
	
	public Message login(@RequestParam(value="username") String username, @RequestParam(value="pwd") String pwd) {
		
		return null;
	}

	
	@RequestMapping("/user/logout")

	public Message logout(@RequestParam(value="token") String token) {
		
		return null;
	}

	
	@RequestMapping("/user/removeprofile")
	public Message removeUserProfile(@RequestParam(value="token") String token, @RequestParam(value="id") String userId) {
		
		return null;
	}

	@RequestMapping("/user/getprofile")
	public UserProfile getUserProfile(@RequestParam(value="token") String token, @RequestParam(value="id") String userId) {
		
		return null;
	}

	@RequestMapping("/user/subscribeevents")

	public Message subscribeEvent(@RequestParam(value="token") String token, @RequestParam(value="userid") String userId, @RequestParam(value="event")List <EvreEvent> events){
	return null;

	}
	
	/*
	
	public Message unSubscribeEvent(String idUser, List <EvreEvent> events);
	
	public Message checkEvent(String userId, String eventId);
	
	public List <Message> checkEvent(String userId);
	
	
	public List<EvreEvent> getSubscribedEvents (String userId);
	
*/
	

}
