package au.com.reece.addressbookapplication.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import au.com.reece.addressbookapplication.dao.AddressBookDAO;
import au.com.reece.addressbookapplication.dao.ContactEntryDAO;
import au.com.reece.addressbookapplication.dto.AddressBookDTO;
import au.com.reece.addressbookapplication.dto.ContactEntryDTO;
import au.com.reece.addressbookapplication.model.AddressBook;
import au.com.reece.addressbookapplication.model.ContactEntry;
import au.com.reece.addressbookapplication.serviceUtil.Status;

@ExtendWith(MockitoExtension.class)
public class ContactEntryServiceImpTest {

	@InjectMocks
	private ContactEntryServiceImpl contactEntryService;
	
	@Mock
	private AddressBookDAO<AddressBook> addressBookDAO;
	
	@Mock
	private ContactEntryDAO<ContactEntry> contactEntryDAO;
	
	@Mock
	private ModelMapper modelMapper;
	
    @Test
    public void getAllUniqueContactEntry_success() throws Exception {
    	List<ContactEntry> entries = new ArrayList<ContactEntry>();
    	ContactEntry contactEntry1 = new ContactEntry();
    	contactEntry1.setName("ContactEntryName1");
    	contactEntry1.setPhoneNumber(1234567891);
    	ContactEntry contactEntry2 = new ContactEntry();
    	contactEntry2.setName("ContactEntryName2");
    	contactEntry2.setPhoneNumber(1234567892);
    	entries.add(contactEntry1);entries.add(contactEntry2);
    	
    	
    	List<ContactEntryDTO> dtos = new ArrayList<ContactEntryDTO>();
    	ContactEntryDTO contactEntryDTO1 = new ContactEntryDTO();
    	contactEntryDTO1.setName("ContactEntryName1");
    	contactEntryDTO1.setPhoneNumber(1234567891);
    	ContactEntryDTO contactEntryDTO2 = new ContactEntryDTO();
    	contactEntryDTO2.setName("ContactEntryName2");
    	contactEntryDTO2.setPhoneNumber(1234567892);
    	dtos.add(contactEntryDTO1);dtos.add(contactEntryDTO2);
    	Mockito.lenient().when(contactEntryDAO.getAll()).thenReturn(entries);
        Mockito.lenient().when(modelMapper.map(contactEntry1, ContactEntryDTO.class)).thenReturn(contactEntryDTO1);
        Mockito.lenient().when(modelMapper.map(contactEntry2, ContactEntryDTO.class)).thenReturn(contactEntryDTO2);
        
        List<ContactEntryDTO> list = contactEntryService.getAll();
    	assertEquals(list.size(), 2);
    	assertEquals(list.get(0).getName(), "ContactEntryName1");
    }
    
    @Test
    public void getContactEntryForAnAddressBook_success() throws Exception {
    	
    	Mockito.lenient().when(addressBookDAO.getAddressBook("AddressBook")).thenReturn(new ArrayList<AddressBook>());
    	List<ContactEntryDTO> list = contactEntryService.getAll("AddressBook");
    	assertEquals(list.size(), 0);
    }
    
    @Test
    public void addContactEntryViaAddressBookAdded_success() throws Exception {
    	
    	ContactEntryDTO contactEntryDTO = new ContactEntryDTO();
    	contactEntryDTO.setName("ContactEntryName");
    	contactEntryDTO.setPhoneNumber(1234567890);
    	
    	Mockito.lenient().when(addressBookDAO.isAddressBookExists("AddressBook")).thenReturn(true);
    	ContactEntry contactEntry = new ContactEntry();
    	contactEntry.setName("ContactEntryName");
    	contactEntry.setPhoneNumber(1234567890);
    	Mockito.lenient().when(modelMapper.map(contactEntryDTO, ContactEntry.class)).thenReturn(contactEntry);
    	
    	Mockito.lenient().when(contactEntryDAO.getContactEntryByPhoneNumber(1234567890)).thenReturn(Optional.empty());
    	Mockito.lenient().when(contactEntryDAO.upsert(contactEntry)).thenReturn(Optional.empty());
    	
    	Status status = contactEntryService.add(contactEntryDTO, "AddressBook");
    	assertEquals(status, Status.SUCCESS);
    }
    
    @Test
    public void addContactEntryViaAddressBookUpdated_success() throws Exception {
    	
    	ContactEntryDTO contactEntryDTO = new ContactEntryDTO();
    	contactEntryDTO.setName("ContactEntryName");
    	contactEntryDTO.setPhoneNumber(1234567890);
    	
    	Mockito.lenient().when(addressBookDAO.isAddressBookExists("AddressBook")).thenReturn(true);
    	ContactEntry contactEntry = new ContactEntry();
    	contactEntry.setName("ContactEntryName");
    	contactEntry.setPhoneNumber(1234567890);
    	Mockito.lenient().when(modelMapper.map(contactEntryDTO, ContactEntry.class)).thenReturn(contactEntry);
    	
    	Mockito.lenient().when(contactEntryDAO.getContactEntryByPhoneNumber(1234567890)).thenReturn(Optional.of(new ContactEntry()));
    	Mockito.lenient().when(contactEntryDAO.upsert(contactEntry)).thenReturn(Optional.empty());
    	
    	Status status = contactEntryService.add(contactEntryDTO, "AddressBook");
    	assertEquals(status, Status.UPDATED);
    }
    
    @Test
    public void addContactEntryViaAddressBookAddressBookNotFound_success() throws Exception {
    	
    	ContactEntryDTO contactEntryDTO = new ContactEntryDTO();
    	contactEntryDTO.setName("ContactEntryName");
    	contactEntryDTO.setPhoneNumber(1234567890);
    	Mockito.lenient().when(addressBookDAO.isAddressBookExists("AddressBook")).thenReturn(false);
    	Status status = contactEntryService.add(contactEntryDTO, "AddressBook");
    	assertEquals(status, Status.ADDRESSBOOK_NOT_FOUND);
    }
    
    @Test
    public void deleteContactEntryViaPhNumberDeleted_success() throws Exception {
    	
    	Mockito.lenient().when(contactEntryDAO.deleteByPhoneNumber(1234567890)).thenReturn(1);
    	Status status = contactEntryService.delete(1234567890);
    	assertEquals(status, Status.DEL_SUCCESS);
    }
    
    @Test
    public void deleteContactEntryViaPhNumberDoesNotExists_success() throws Exception {
    	
    	Mockito.lenient().when(contactEntryDAO.deleteByPhoneNumber(1234567890)).thenReturn(0);
    	Status status = contactEntryService.delete(1234567890);
    	assertEquals(status, Status.DEL_OK);
    }
}
