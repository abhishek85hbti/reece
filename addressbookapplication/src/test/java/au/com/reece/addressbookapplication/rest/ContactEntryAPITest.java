package au.com.reece.addressbookapplication.rest;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
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

import au.com.reece.addressbookapplication.dto.ContactEntryDTO;
import au.com.reece.addressbookapplication.service.ContactEntryService;
import au.com.reece.addressbookapplication.serviceUtil.Status;
import au.com.reece.addressbookapplication.util.TestUtil;

@WebMvcTest(ContactEntryAPI.class)
public class ContactEntryAPITest {
	
    private MockMvc mockMvc;
    
    @MockBean
    private ContactEntryService<ContactEntryDTO> contactService;
    
    @Autowired
    public ContactEntryAPITest(MockMvc mockMvc) {
		this.mockMvc = mockMvc;
	}
    
    @Test
    public void getAllUniqueContactEntries_success() throws Exception {
        
        mockMvc.perform(MockMvcRequestBuilders
                .get("/contactentry-management/all-contacts")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[1].name", Matchers.is("ContactEntryName2")));
    }
    
    @Test
    public void getAllContactEntriesFromAnAddressBook_success() throws Exception {
        
        mockMvc.perform(MockMvcRequestBuilders
                .get("/contactentry-management/contacts")
                .param("addressbookname", "AddressBook")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[1].name", Matchers.is("ContactEntryName2")));
    }
    
    @Test
    public void addContactEntryGivenAddressBook_success() throws Exception {
        
    	ContactEntryDTO contactEntry1 = new ContactEntryDTO();
    	contactEntry1.setName("ContactEntryName1");
    	contactEntry1.setPhoneNumber(1234567891);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/contactentry-management/contact")
                .param("addressbookname", "AddressBook")
                .content(TestUtil.asJsonString(contactEntry1))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().string(Status.SUCCESS.toString()));
    }
    
    @Test
    public void deleteCOntactEntryViaPhoneNumber_success() throws Exception {
        
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/contactentry-management/contact/{phonenumber}", 1234567890))
        		.andExpect(status().isOk())
    			.andExpect(content().string(Status.DEL_SUCCESS.toString()));
    }
    
    @BeforeEach
    void setMockOutput() {
    	
    	List<ContactEntryDTO> dtos = new ArrayList<ContactEntryDTO>();
    	ContactEntryDTO contactEntry1 = new ContactEntryDTO();
    	contactEntry1.setName("ContactEntryName1");
    	contactEntry1.setPhoneNumber(1234567891);
    	ContactEntryDTO contactEntry2 = new ContactEntryDTO();
    	contactEntry2.setName("ContactEntryName2");
    	contactEntry2.setPhoneNumber(1234567892);
    	dtos.add(contactEntry1);dtos.add(contactEntry2);

        Mockito.when(contactService.getAll()).thenReturn(dtos);
        Mockito.when(contactService.getAll("AddressBook")).thenReturn(dtos);
        Mockito.when(contactService.add(contactEntry1, "AddressBook")).thenReturn(Status.SUCCESS);
        Mockito.when(contactService.delete(1234567890)).thenReturn(Status.DEL_SUCCESS);
    }
}
