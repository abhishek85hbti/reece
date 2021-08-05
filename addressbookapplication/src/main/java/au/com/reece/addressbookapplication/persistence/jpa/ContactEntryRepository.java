package au.com.reece.addressbookapplication.persistence.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import au.com.reece.addressbookapplication.model.ContactEntry;

@Transactional
public interface ContactEntryRepository extends JpaRepository<ContactEntry, Integer> {

	List<ContactEntry> findAll();
	List<ContactEntry> findByPhoneNumberAndName(Integer phoneNumber, String name);
	List<ContactEntry> findByPhoneNumber(Integer phoneNumber);
	@Transactional
	@Modifying
	@Query("delete from ContactEntry c where c.phoneNumber=:phoneNumber")
	Integer deleteByPhoneNumber(Integer phoneNumber);
	ContactEntry save(ContactEntry contactEntry);
}
