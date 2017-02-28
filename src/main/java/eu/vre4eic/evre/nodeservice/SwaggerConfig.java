package eu.vre4eic.evre.nodeservice;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Predicate;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import static springfox.documentation.builders.PathSelectors.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


import static com.google.common.base.Predicates.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
//@SpringBootApplication
@EnableSwagger2
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class SwaggerConfig {    
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Bean
	public Docket api() { 
		return new Docket(DocumentationType.SWAGGER_2) 
				.groupName("nodeservice-api")
				.apiInfo(apiInfo())
				.select()   
				//.apis(!(RequestHandlerSelectors.withClassAnnotation(JsonIgnore.class))
				.apis(RequestHandlerSelectors.any())              
				//.paths(PathSelectors.any()) 
				//.paths(PathSelectors.regex("/user.*"))
				.paths(paths())
				.build()
				.pathMapping("/");                                           
	}

	private Predicate<String> paths() {
		return or(PathSelectors.regex("/user.*"),
				PathSelectors.regex("/node.*"));
	}
	private ApiInfo apiInfo() {
		InputStream in;
		Properties property = new Properties();
		try {
			in = this.getClass().getClassLoader()
					.getResourceAsStream("Nodeservice.properties");
			property.load(in);
			in.close();
		} catch (FileNotFoundException e) {

			log.error(e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return new ApiInfoBuilder()
				.title("e-VRE NodeService")
				.description("This pages shows the Web Services entry points for the e-VRE NodeService component.")
				.version(property.getProperty("VERSION"))
				.termsOfServiceUrl("http://terms-of-services.evre")
				.license(property.getProperty("LICENSE_TYPE"))
				.licenseUrl(property.getProperty("LICENSE_URL"))
				.build();
	}
}