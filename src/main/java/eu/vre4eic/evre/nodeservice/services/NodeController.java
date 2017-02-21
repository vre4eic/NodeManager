package eu.vre4eic.evre.nodeservice.services;



import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonIgnore;

import eu.vre4eic.evre.core.messages.Message;




/**
 * This class contains methods for managing users. 
 * @author Cesare
 *
 */

@Controller
public class NodeController {

	private static final String RELEASE = "release";
	private static final String WELCOME_PAGE = "welcome";

	public NodeController()  {
		super();
	}

	@JsonIgnore
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String WelcomePage(ModelMap model, HttpSession session) {

		session.setAttribute(RELEASE, "0.01a");
		
		return WELCOME_PAGE;
	}
	//list of services
	    @JsonIgnore
		@RequestMapping(value="/servicesdoc", method=RequestMethod.GET)
		public String userServices(Model model, HttpSession session, @RequestParam(value="component")String component) {
			session.setAttribute(RELEASE, "0.01a");

			return component;
		}
	    @RequestMapping(value="node/ping", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
		public boolean pingNodeService(@RequestParam(value="token") String token) {
			
			return true;
		}

}
