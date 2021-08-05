package au.com.reece.addressbookapplication.daoImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.util.ReflectionTestUtils;

import au.com.reece.addressbookapplication.model.ContactEntry;
import au.com.reece.addressbookapplication.persistence.jpa.ContactEntryRepository;

@DataJpaTest
public class ContactEntryDAOImplTest {

    @Autowired
    private TestEntityManager entityManager;
    
    @Autowired
    ContactEntryRepository contactEntryRepository;
    
	@InjectMocks
	private ContactEntryDAOImpl contactEntryDAO;
    
    @Test
    public void getAllContactEntry_success() throws Exception {
    	
    	ContactEntry contactEntry1 = new ContactEntry();
    	contactEntry1.setName("ContactEntryName1");
    	contactEntry1.setPhoneNumber(1234567891);
    	ContactEntry contactEntry2 = new ContactEntry();
    	contactEntry2.setName("ContactEntryName2");
    	contactEntry2.setPhoneNumber(1234567892);
    	entityManager.persist(contactEntry1);
    	entityManager.persist(contactEntry2);
    	ReflectionTestUtils.setField(contactEntryDAO,
    			"contactEntryRepository", contactEntryRepository);
    	
    	assertEquals(contactEntryDAO.getAll().size(), 2);
    	entityManager.flush();
    }
    
    @Test
    public void getContactEntryViaPhoneNumber_success() throws Exception {
    	
    	ContactEntry contactEntry1 = new ContactEntry();
    	contactEntry1.setName("ContactEntryName1");
    	contactEntry1.setPhoneNumber(1234567891);
    	ContactEntry contactEntry2 = new ContactEntry();
    	contactEntry2.setName("ContactEntryName2");
    	contactEntry2.setPhoneNumber(1234567892);
    	entityManager.persist(contactEntry1);
    	entityManager.persist(contactEntry2);
    	ReflectionTestUtils.setField(contactEntryDAO,
    			"contactEntryRepository", contactEntryRepository);
    	
    	assertEquals(contactEntryDAO.getContactEntryByPhoneNumber(1234567891).get().getName(), 
    			"ContactEntryName1");
    	entityManager.flush();
    }
    
    @Test
    public void getContactEntryViaPhoneNumber_failure() throws Exception {
    	
    	ContactEntry contactEntry1 = new ContactEntry();
    	contactEntry1.setName("ContactEntryName1");
    	contactEntry1.setPhoneNumber(1234567891);
    	ContactEntry contactEntry2 = new ContactEntry();
    	contactEntry2.setName("ContactEntryName2");
    	contactEntry2.setPhoneNumber(1234567892);
    	entityManager.persist(contactEntry1);
    	entityManager.persist(contactEntry2);
    	ReflectionTestUtils.setField(contactEntryDAO,
    			"contactEntryRepository", contactEntryRepository);
    	
    	assertEquals(contactEntryDAO.getContactEntryByPhoneNumber(1234567893), Optional.empty());
    	entityManager.flush();
    }
    
    @Test
    public void getContactEntryViaPhoneNumberName_success() throws Exception {
    	
    	ContactEntry contactEntry1 = new ContactEntry();
    	contactEntry1.setName("ContactEntryName1");
    	contactEntry1.setPhoneNumber(1234567891);
    	ContactEntry contactEntry2 = new ContactEntry();
    	contactEntry2.setName("ContactEntryName2");
    	contactEntry2.setPhoneNumber(1234567892);
    	entityManager.persist(contactEntry1);
    	entityManager.persist(contactEntry2);
    	ReflectionTestUtils.setField(contactEntryDAO,
    			"contactEntryRepository", contactEntryRepository);
    	
    	assertEquals(contactEntryDAO.getContactEntryByPhoneNumber(1234567891, "ContactEntryName1").size(), 1);
    	entityManager.flush();
    }
    
    @Test
    public void getContactEntryViaPhoneNumberName_failure() throws Exception {
    	
    	ContactEntry contactEntry1 = new ContactEntry();
    	contactEntry1.setName("ContactEntryName1");
    	contactEntry1.setPhoneNumber(1234567891);
    	ContactEntry contactEntry2 = new ContactEntry();
    	contactEntry2.setName("ContactEntryName2");
    	contactEntry2.setPhoneNumber(1234567892);
    	entityManager.persist(contactEntry1);
    	entityManager.persist(contactEntry2);
    	ReflectionTestUtils.setField(contactEntryDAO,
    			"contactEntryRepository", contactEntryRepository);
    	
    	assertEquals(contactEntryDAO.getContactEntryByPhoneNumber(1234567893, "ContactEntryName3").size(), 0);
    	entityManager.flush();
    }
    
    @Test
    public void deleteContactEntryViaPhoneNumberName_success() throws Exception {
    	
    	ContactEntry contactEntry1 = new ContactEntry();
    	contactEntry1.setName("ContactEntryName1");
    	contactEntry1.setPhoneNumber(1234567891);
    	ContactEntry contactEntry2 = new ContactEntry();
    	contactEntry2.setName("ContactEntryName2");
    	contactEntry2.setPhoneNumber(1234567892);
    	entityManager.persist(contactEntry1);
    	entityManager.persist(contactEntry2);
    	ReflectionTestUtils.setField(contactEntryDAO,
    			"contactEntryRepository", contactEntryRepository);
    	
    	assertEquals(contactEntryDAO.deleteByPhoneNumber(1234567891), 1);
    	entityManager.flush();
    }
    
    @Test
    public void deleteContactEntryViaPhoneNumberName_failure() throws Exception {
    	
    	ContactEntry contactEntry1 = new ContactEntry();
    	contactEntry1.setName("ContactEntryName1");
    	contactEntry1.setPhoneNumber(1234567891);
    	ContactEntry contactEntry2 = new ContactEntry();
    	contactEntry2.setName("ContactEntryName2");
    	contactEntry2.setPhoneNumber(1234567892);
    	entityManager.persist(contactEntry1);
    	entityManager.persist(contactEntry2);
    	ReflectionTestUtils.setField(contactEntryDAO,
    			"contactEntryRepository", contactEntryRepository);
    	
    	assertEquals(contactEntryDAO.deleteByPhoneNumber(1234567893), 0);
    	entityManager.flush();
    }
    
    @Test
    public void addContactEntry_success() throws Exception {
    	
    	ContactEntry contactEntry1 = new ContactEntry();
    	contactEntry1.setName("ContactEntryName1");
    	contactEntry1.setPhoneNumber(1234567891);
    	//entityManager.persist(contactEntry1);
    	ReflectionTestUtils.setField(contactEntryDAO,
    			"contactEntryRepository", contactEntryRepository);
    	contactEntryDAO.upsert(contactEntry1);
    	
    	assertEquals(entityManager.getId(contactEntry1), 1234567891);
    	entityManager.flush();
    }
    
    @Test
    public void updateContactEntry_success() throws Exception {
    	
    	ContactEntry contactEntry1 = new ContactEntry();
    	contactEntry1.setName("ContactEntryName1");
    	contactEntry1.setPhoneNumber(1234567891);
    	//entityManager.persist(contactEntry1);
    	ReflectionTestUtils.setField(contactEntryDAO,
    			"contactEntryRepository", contactEntryRepository);
    	contactEntryDAO.upsert(contactEntry1);
    	
    	ContactEntry contactEntry1Update = new ContactEntry();
    	contactEntry1Update.setName("ContactEntryName1Update");
    	contactEntry1Update.setPhoneNumber(1234567891);
    	contactEntryDAO.upsert(contactEntry1Update);
    	
    	assertEquals(entityManager.getId(contactEntry1Update), 1234567891);
    	entityManager.flush();
    }
}
