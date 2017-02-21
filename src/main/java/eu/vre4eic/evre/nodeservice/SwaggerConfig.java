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
import static com.google.common.base.Predicates.*;


@Configuration
//@SpringBootApplication
@EnableSwagger2
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class SwaggerConfig {                                    
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
          .pathMapping("/NodeService/");                                           
    }
    
    private Predicate<String> paths() {
        return or(PathSelectors.regex("/user.*"),
        		PathSelectors.regex("/node.*"));
      }
    private ApiInfo apiInfo() {
    	return new ApiInfoBuilder()
                .title("e-VRE NodeService")
                .description("This pages shows the Web Services entry points for the e-VRE NodeService component.")
                .version("0.0.1")
                .termsOfServiceUrl("http://terms-of-services.evre")
                .license("TBD")
                .licenseUrl("http://url-to-license.com")
                .build();
    	}
}