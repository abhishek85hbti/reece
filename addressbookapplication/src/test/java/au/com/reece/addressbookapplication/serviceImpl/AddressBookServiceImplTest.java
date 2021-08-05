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
import au.com.reece.addressbookapplication.dto.AddressBookDTO;
import au.com.reece.addressbookapplication.model.AddressBook;
import au.com.reece.addressbookapplication.serviceUtil.Status;

@ExtendWith(MockitoExtension.class)
public class AddressBookServiceImplTest {

	@InjectMocks
	private AddressBookServiceImpl addressBookService;
	
	@Mock
	private AddressBookDAO<AddressBook> addressBookDAO;
	
	@Mock
	private ModelMapper modelMapper;
	
    @Test
    public void getAllAddressBookAlongWithContactEntriesFromDB_success() throws Exception {
    	
    	AddressBook addressBook1 = new AddressBook("AddressBook1");
    	AddressBook addressBook2 = new AddressBook("AddressBook2");
        List<AddressBook> addressBooks = new ArrayList<>(
        		Arrays.asList(addressBook1, addressBook2));
        Mockito.lenient().when(addressBookDAO.getAll()).thenReturn(addressBooks);
        Mockito.lenient().when(addressBookDAO.isAddressBookExists("AddressBook")).thenReturn(true);
        Mockito.lenient().when(addressBookDAO.upsert(addressBook1)).thenReturn(Optional.of(addressBook1));
        Mockito.lenient().when(addressBookDAO.deleteByAddressBookName("AddressBook")).thenReturn(1);
    	AddressBookDTO addressBookDTO1 = new AddressBookDTO();
    	addressBookDTO1.setAddressBookName("AddressBook1");
    	AddressBookDTO addressBookDTO2 = new AddressBookDTO();
    	addressBookDTO2.setAddressBookName("AddressBook2");
        Mockito.lenient().when(modelMapper.map(addressBook1, AddressBookDTO.class)).thenReturn(addressBookDTO1);
        Mockito.lenient().when(modelMapper.map(addressBook2, AddressBookDTO.class)).thenReturn(addressBookDTO2);
        
    	List<AddressBookDTO> books = addressBookService.getAll();
    	assertEquals(books.size(), 2);
    	assertEquals(books.get(0).getAddressBookName(), "AddressBook1");
    }
    
    @Test
    public void addNewAddressBookViaAddressBookNameAdded_success() throws Exception {
    	
    	AddressBookDTO addressBookDTO = new AddressBookDTO();
    	addressBookDTO.setAddressBookName("AddressBook");
    	AddressBook addressBook = new AddressBook("AddressBook");
    	Mockito.lenient().when(modelMapper.map(addressBookDTO, AddressBook.class)).thenReturn(addressBook);
    	Mockito.lenient().when(addressBookDAO.isAddressBookExists("AddressBook")).thenReturn(false);
    	Mockito.lenient().when(addressBookDAO.upsert(addressBook)).thenReturn(Optional.of(addressBook));
    	Status status = addressBookService.add(addressBookDTO);
    	assertEquals(status, Status.SUCCESS);
    }
    
    @Test
    public void addNewAddressBookViaAddressBookNameUpdated_success() throws Exception {
    	
    	AddressBookDTO addressBookDTO = new AddressBookDTO();
    	addressBookDTO.setAddressBookName("AddressBook");
    	AddressBook addressBook = new AddressBook("AddressBook");
    	Mockito.lenient().when(modelMapper.map(addressBookDTO, AddressBook.class)).thenReturn(addressBook);
    	Mockito.lenient().when(addressBookDAO.isAddressBookExists("AddressBook")).thenReturn(true);
    	Status status = addressBookService.add(addressBookDTO);
    	assertEquals(status, Status.ALREDY_EXISTS);
    }
    
    
    @Test
    public void deleteAddressBookViaAddressBookNameDeleted_success() throws Exception {
    	
    	Mockito.lenient().when(addressBookDAO.deleteByAddressBookName("AddressBook")).thenReturn(1);
    	Status status = addressBookService.delete("AddressBook");
    	assertEquals(status, Status.DEL_SUCCESS);
    }
    
    @Test
    public void deleteAddressBookViaAddressBookNameDoesNotExists_success() throws Exception {
    	
    	Mockito.lenient().when(addressBookDAO.deleteByAddressBookName("AddressBook")).thenReturn(0);
    	Status status = addressBookService.delete("AddressBook");
    	assertEquals(status, Status.DEL_OK);
    }
}
