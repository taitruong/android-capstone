package org.aliensource.symptommanagement.cloud;

import org.aliensource.symptommanagement.cloud.auth.OAuth2SecurityConfiguration;
import org.aliensource.symptommanagement.cloud.repository.PatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

//Tell Spring to automatically inject any dependencies that are marked in
//our classes with @Autowired
@EnableAutoConfiguration
// Tell Spring to automatically create a JPA implementation of our
// repositories
@EnableJpaRepositories(basePackageClasses = {PatientRepository.class})
// Tell Spring to turn on WebMVC (e.g., it should enable the DispatcherServlet
// so that requests can be routed to our Controllers)
@EnableWebMvc
// Tell Spring that this object represents a Configuration for the
// application
@Configuration
// Tell Spring to go and scan our controller package (and all sub packages) to
// find any Controllers or other components that are part of our applciation.
// Any class in this package that is annotated with @Controller is going to be
// automatically discovered and connected to the DispatcherServlet.
@ComponentScan
// We use the @Import annotation to include our OAuth2SecurityConfiguration
// as part of this configuration so that we can have security and oauth
// setup by Spring
@Import(OAuth2SecurityConfiguration.class)
// enable for using @PreAuthorize
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class Application {

    private final static Logger LOGGER = LoggerFactory.getLogger(Application.class);

	// Tell Spring to launch our app!
	public static void main(String[] args) {
        LOGGER.info("Starting Symptom Management");
		SpringApplication.run(Application.class, args);
	}

}
