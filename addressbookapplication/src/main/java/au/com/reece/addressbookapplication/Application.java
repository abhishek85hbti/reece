package au.com.reece.addressbookapplication;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	private static final Logger LOGGER = LogManager.getLogger(Application.class);
	
	/*
	 * The @SpringBootApplication annotation enables auto-configuration and component scanning.
	 * Spring Boot finds the MyBean annotation and loads it into the application context bean factory.
	 * 
	 * With the @Autowired annotation we inject our ApplicationContext bean into the field. 
	 * Now we can access the methods of the context.
	 * 
	 */
	public static void main(String[] args) {
		LOGGER.info("AddressBookApplication Getting Started ...");
		SpringApplication.run(Application.class, args);
		LOGGER.info("AddressBookApplication Started Successfully.");
	}
}
