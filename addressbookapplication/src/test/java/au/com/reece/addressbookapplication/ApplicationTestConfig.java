package au.com.reece.addressbookapplication;

import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import au.com.reece.addressbookapplication.serviceImpl.AddressBookServiceImpl;

@Profile("test")
@Configuration
public class ApplicationTestConfig {
	
	@Bean
	public ModelMapper modelMapper() {
	    return new ModelMapper();
	}
	
    @Bean
    public AddressBookServiceImpl nameService() {
        return Mockito.mock(AddressBookServiceImpl.class);
    }
}
