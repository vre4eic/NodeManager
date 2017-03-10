package eu.vre4eic.evre.nodeservice;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

//@Configuration
//@ComponentScan

//@EnableAutoConfiguration
@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})

public class NodeServiceStart {

	
    public static void main(String[] args) {
     //	System.out.println("Working Directory is = " +
       //         System.getProperty("user.dir"));
        SpringApplication.run(NodeServiceStart.class, args);
    }
}
