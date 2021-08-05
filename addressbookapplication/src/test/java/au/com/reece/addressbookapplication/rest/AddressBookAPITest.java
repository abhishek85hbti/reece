package au.com.reece.addressbookapplication.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import au.com.reece.addressbookapplication.dto.AddressBookDTO;
import au.com.reece.addressbookapplication.service.AddressBookService;
import au.com.reece.addressbookapplication.serviceUtil.Status;

@WebMvcTest(AddressBookAPI.class)
public class AddressBookAPITest {

    private MockMvc mockMvc;
    
    @MockBean
	AddressBookService<AddressBookDTO> addressBookService;
    
    @Autowired
    public AddressBookAPITest(MockMvc mockMvc) {
		this.mockMvc = mockMvc;
	}
    
    @Test
    public void getAllAddressBookAlongWithContactEntries_success() throws Exception {
        
        mockMvc.perform(MockMvcRequestBuilders
                .get("/addressbook-management/addressbooks")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[1].addressBookName", Matchers.is("AddressBook2")));
    }
    
    @Test
    public void addNewAddressBookViaAddressBookName_success() throws Exception {
        
        mockMvc.perform(MockMvcRequestBuilders
                .post("/addressbook-management/addressbook")
                .param("addressbookname", "AddressBook"))
                .andExpect(status().isCreated())
                .andExpect(content().string(Status.SUCCESS.toString()));
    }
    
    @Test
    public void deleteAddressBookViaAddressBookName_success() throws Exception {
        
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/addressbook-management/addressbook/{addressbookname}", "AddressBook"))
        		.andExpect(status().isOk())
    			.andExpect(content().string(Status.DEL_SUCCESS.toString()));
    }
    
    @BeforeEach
    void setMockOutput() {
    	
    	AddressBookDTO addressBook1 = new AddressBookDTO();
    	addressBook1.setAddressBookName("AddressBook1");
    	AddressBookDTO addressBook2 = new AddressBookDTO();
    	addressBook1.setAddressBookName("AddressBook1");
    	AddressBookDTO addressBook3 = new AddressBookDTO();
    	addressBook1.setAddressBookName("AddressBook1");
        List<AddressBookDTO> addressBooks = new ArrayList<>(
        		Arrays.asList(addressBook1, addressBook2, addressBook3));

        Mockito.when(addressBookService.getAll()).thenReturn(addressBooks);
        Mockito.when(addressBookService.add("AddressBook")).thenReturn(Status.SUCCESS);
        Mockito.when(addressBookService.delete("AddressBook")).thenReturn(Status.DEL_SUCCESS);
    }
}
