package eu.vre4eic.evre.nodeservice;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;

//@Configuration
@ComponentScan

@EnableAutoConfiguration
public class NodeServiceStart {

    public static void main(String[] args) {
     	System.out.println("Working Directory is = " +
                System.getProperty("user.dir"));
        SpringApplication.run(NodeServiceStart.class, args);
    }
}
