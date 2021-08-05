package au.com.reece.addressbookapplication.dao;

import java.util.List;
import java.util.Optional;

public interface AddressBookDAO<T> {
	List<T> getAll();
	List<T> getAddressBook(String name);
	boolean isAddressBookExists(String name);
	Integer deleteByAddressBookName(String name);
	Optional<T> upsert(T e);
}
