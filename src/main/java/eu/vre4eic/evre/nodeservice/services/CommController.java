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

@RestController
public class CommController {

	Properties property = new Properties();
	public CommController()  {
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

	 @ApiOperation(value = "Chek if the NodeService communication infrastructure is active", 
		        notes = "A service with a valid identifier can invoke this web service to check if the communication infrastructure is active", 
		        response = boolean.class)
	    @RequestMapping(value="node/ping", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
		public boolean pingNodeService(@RequestParam(value="evresid") String evresid) {
			
			return true;
		}
	    
	    @ApiOperation(value = "Subscribes a services to a list of e-VRE topics", 
		        notes = "A service with a valid identifier can invoke this web service to subscribe to a list of e-VRE topics", 
		        response = SubscriptionMessage.class)
	    @RequestMapping(value="/node/subscribetopics", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
		public SubscriptionMessage subscribeService(@RequestParam(value="evresid") String evresid, 
				@RequestParam(value="topics") List <NotificationType> topics){
	    	SubscriptionMessage sm= new SubscriptionMessageImpl("url", "msg", Common.ResponseStatus.SUCCEED);
			return sm;
		}

}
