package eu.vre4eic.evre.nodeservice.nodemanager;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import eu.vre4eic.evre.nodeservice.nodemanager.ZKServer;
@Configuration
@WebListener
public class NodeServiceContextListener implements ServletContextListener {

	  @Override
	//  @Bean
	  public void contextDestroyed(ServletContextEvent arg0) {
		  ZKServer.stopService();
	  }

	  @Override
	 // @Bean
	  public void contextInitialized(ServletContextEvent arg0) {
		  ZKServer.init();
	  }

	

	}