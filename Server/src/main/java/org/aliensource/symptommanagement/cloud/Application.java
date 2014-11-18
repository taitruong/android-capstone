package org.aliensource.symptommanagement.cloud;

import org.aliensource.symptommanagement.cloud.auth.OAuth2SecurityConfiguration;
import org.aliensource.symptommanagement.cloud.json.ResourcesMapper;
import org.aliensource.symptommanagement.cloud.repository.CheckIn;
import org.aliensource.symptommanagement.cloud.repository.Doctor;
import org.aliensource.symptommanagement.cloud.repository.IntakeTime;
import org.aliensource.symptommanagement.cloud.repository.Medicament;
import org.aliensource.symptommanagement.cloud.repository.Medication;
import org.aliensource.symptommanagement.cloud.repository.Patient;
import org.aliensource.symptommanagement.cloud.repository.Role;
import org.aliensource.symptommanagement.cloud.repository.Symptom;
import org.aliensource.symptommanagement.cloud.repository.SymptomTime;
import org.aliensource.symptommanagement.cloud.repository.Video;
import org.aliensource.symptommanagement.cloud.repository.VideoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

//Tell Spring to automatically inject any dependencies that are marked in
//our classes with @Autowired
@EnableAutoConfiguration
// Tell Spring to automatically create a JPA implementation of our
// VideoRepository
@EnableJpaRepositories(basePackageClasses = {VideoRepository.class})
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
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class Application extends RepositoryRestMvcConfiguration {

    private final static Logger LOGGER = LoggerFactory.getLogger(Application.class);

    private ResourcesMapper resourcesMapper = new ResourcesMapper();

	// Tell Spring to launch our app!
	public static void main(String[] args) {
        LOGGER.info(">>>>> starting Symptom Management");
		SpringApplication.run(Application.class, args);

	}

	// We are overriding the bean that RepositoryRestMvcConfiguration
	// is using to convert our objects into JSON so that we can control
	// the format. The Spring dependency injection will inject our instance
	// of ObjectMapper in all of the spring data rest classes that rely
	// on the ObjectMapper. This is an example of how Spring dependency
	// injection allows us to easily configure dependencies in code that
	// we don't have easy control over otherwise.
	//
	// Normally, we would not override this object mapping. However, in this
	// case, we are overriding the JSON conversion so that we can easily
	// extract a list of videos, etc. using Retrofit. You can remove this
	// method from the class to see what the default HATEOAS-based responses
	// from Spring Data Rest look like. You will need to access the server
	// from your browser as removing this method will break the Retrofit
	// client.
	//
	// See the ResourcesMapper class for more details.
	@Override
    @Bean
    @Primary
	public ObjectMapper halObjectMapper() {
		return resourcesMapper;
	}

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        jsonConverter.setObjectMapper(resourcesMapper);
        return jsonConverter;
    }

	@Override
	protected void configureRepositoryRestConfiguration(
			RepositoryRestConfiguration config) {
		config.exposeIdsFor(Video.class,
                CheckIn.class,
                Doctor.class,
                IntakeTime.class,
                Medicament.class,
                Medication.class,
                Patient.class,
                Role.class,
                Symptom.class,
                SymptomTime.class);
	}

}
