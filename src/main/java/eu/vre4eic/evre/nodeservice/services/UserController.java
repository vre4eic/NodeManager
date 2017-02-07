package eu.vre4eic.evre.nodeservice.services;



import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;




import eu.vre4eic.evre.nodeservice.core.UserProfile;
import eu.vre4eic.evre.nodeservice.core.messages.Message;

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

	/*
	public Message createUserProfile(UserProfile profile) {
		
		return null;
	}
*/
	//probably this method won't be used, it should be implemented by AAAI service
	@RequestMapping("/user/login")
	
	public Message login(@RequestParam(value="username") String username, @RequestParam(value="pwd") String pwd) {
		
		return null;
	}

	//probably this method won't be used, it should be implemented by AAAI service
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

	
	

	

}
