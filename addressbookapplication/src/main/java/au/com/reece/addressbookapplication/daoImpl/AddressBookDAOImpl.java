package au.com.reece.addressbookapplication.daoImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import au.com.reece.addressbookapplication.dao.AddressBookDAO;
import au.com.reece.addressbookapplication.model.AddressBook;
import au.com.reece.addressbookapplication.persistence.jpa.AddressBookRepository;

@Component
public class AddressBookDAOImpl implements AddressBookDAO<AddressBook> {

	private AddressBookRepository addressBookRepository;

	@Autowired
	public AddressBookDAOImpl(AddressBookRepository addressBookRepository) {
		this.addressBookRepository = addressBookRepository;
	}
	@Override
	public List<AddressBook> getAll() {
		return addressBookRepository.findAll();
	}
	@Override
	public Optional<AddressBook> upsert(AddressBook addressBook) {
		return Optional.ofNullable(addressBookRepository.save(addressBook));
	}
	@Override
	public boolean isAddressBookExists(String addressBookName) {
		if (addressBookName != null) {
			return !getAddressBook(addressBookName).isEmpty();
		}
		return false;
	}
	@Override
	public List<AddressBook> getAddressBook(String addressBookName) {
		return addressBookRepository.findByAddressBookName(addressBookName);
	}
	@Override
	public Integer deleteByAddressBookName(String name) {
		return addressBookRepository.deleteByAddressBookName(name);
	}
}
