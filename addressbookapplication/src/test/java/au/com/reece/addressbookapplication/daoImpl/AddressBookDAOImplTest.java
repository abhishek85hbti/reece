package au.com.reece.addressbookapplication.daoImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.util.ReflectionTestUtils;

import au.com.reece.addressbookapplication.model.AddressBook;
import au.com.reece.addressbookapplication.persistence.jpa.AddressBookRepository;

@DataJpaTest
public class AddressBookDAOImplTest {

    @Autowired
    private TestEntityManager entityManager;
    
    @Autowired
    AddressBookRepository addressBookRepository;
    
	@InjectMocks
	private AddressBookDAOImpl addressBookDAO;
	
    @Test
    public void getAllAddressBookAlongWithContactEntries_success() throws Exception {
    	
    	AddressBook addressBook1 = new AddressBook("AddressBook1");
    	AddressBook addressBook2 = new AddressBook("AddressBook2");
    	entityManager.persist(addressBook1);
    	entityManager.persist(addressBook2);
    	ReflectionTestUtils.setField(addressBookDAO,
    			"addressBookRepository", addressBookRepository);
    	assertEquals(addressBookDAO.getAll().size(), 2);
    	entityManager.flush();
    }
    
    @Test
    public void addAnAddressBook_success() throws Exception {
    	
    	AddressBook addressBook = new AddressBook("NewAddressBook");
    	ReflectionTestUtils.setField(addressBookDAO,
    			"addressBookRepository", addressBookRepository);
    	addressBookDAO.upsert(addressBook);
    	assertEquals(entityManager.getId(addressBook).toString(), "NewAddressBook");
    	entityManager.flush();
    }
    
    @Test
    public void isAddressBookExists_success() throws Exception {
    	
    	AddressBook addressBook = new AddressBook("AlreadyinDBAddressBook");
    	entityManager.persist(addressBook);
    	ReflectionTestUtils.setField(addressBookDAO,
    			"addressBookRepository", addressBookRepository);
    	assertTrue(addressBookDAO.isAddressBookExists("AlreadyinDBAddressBook"));
    	entityManager.flush();
    }
    
    @Test
    public void getAddressBookViaAddressBookName_success() throws Exception {
    	
    	AddressBook addressBook1 = new AddressBook("AddressBook1");
    	AddressBook addressBook2 = new AddressBook("AddressBook2");
    	entityManager.persist(addressBook1);
    	entityManager.persist(addressBook2);
    	ReflectionTestUtils.setField(addressBookDAO,
    			"addressBookRepository", addressBookRepository);
    	assertEquals(addressBookDAO.getAddressBook("AddressBook2").size(), 1);
    	assertEquals(addressBookDAO.getAddressBook("AddressBook2").get(0).getAddressBookName(), "AddressBook2");
    	entityManager.flush();
    }
    
    @Test
    public void deleteAddressBookViaAddressBookName_success() throws Exception {
    	
    	AddressBook addressBook1 = new AddressBook("AddressBook1");
    	AddressBook addressBook2 = new AddressBook("AddressBook2");
    	entityManager.persist(addressBook1);
    	entityManager.persist(addressBook2);
    	ReflectionTestUtils.setField(addressBookDAO,
    			"addressBookRepository", addressBookRepository);
    	assertEquals(addressBookDAO.deleteByAddressBookName("AddressBook2"), 1);
    	entityManager.flush();
    }
}
