package au.com.reece.addressbookapplication.persistence.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import au.com.reece.addressbookapplication.model.AddressBook;

public interface AddressBookRepository extends JpaRepository<AddressBook, String> {

	List<AddressBook> findAll();
	List<AddressBook> findByAddressBookName(String name);
	AddressBook save(AddressBook contactEntry);
	@Transactional
	@Modifying
	@Query("delete from AddressBook a where a.addressBookName=:addressBookName")
	Integer deleteByAddressBookName(String addressBookName);
}
