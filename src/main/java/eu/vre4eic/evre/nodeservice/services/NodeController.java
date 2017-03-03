package eu.vre4eic.evre.nodeservice.services;



import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

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

import eu.vre4eic.evre.core.EvreEvent;
import eu.vre4eic.evre.core.Common;
import eu.vre4eic.evre.core.Common.NotificationType;
import eu.vre4eic.evre.core.Common.UserRole;
import eu.vre4eic.evre.core.messages.Message;
import eu.vre4eic.evre.core.messages.SubscriptionMessage;
import eu.vre4eic.evre.core.messages.impl.SubscriptionMessageImpl;
import io.swagger.annotations.ApiOperation;


/**
 * This class contains methods for managing users. 
 * @author Cesare
 *
 */

@Controller
public class NodeController {

	private static final String RELEASE = "release";
	private static final String WELCOME_PAGE = "welcome";
	Properties property = new Properties();
	public NodeController()  {
		super();
		
		InputStream in;
		
		try {
			in = this.getClass().getClassLoader()
					.getResourceAsStream("Nodeservice.properties");
			property.load(in);
			in.close();
		} catch (FileNotFoundException e) {
			
		} catch (IOException e) {
			
		}
	}

	//@JsonIgnore
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String WelcomePage(ModelMap model, HttpSession session) {

		session.setAttribute(RELEASE, property.getProperty("VERSION"));
		
		return WELCOME_PAGE;
	}
	//list of services
	    
		@RequestMapping(value="/servicesdoc", method=RequestMethod.GET)
		public String userServices(Model model, HttpSession session, @RequestParam(value="component")String component) {
			session.setAttribute(RELEASE, "0.01a");

			return component;
		}
		@RequestMapping(value = "/doc", method = RequestMethod.GET)
		public String redirect() {

			return "redirect:/doc/index"; 
		}

}
