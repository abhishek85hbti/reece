package au.com.reece.addressbookapplication.daoImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import au.com.reece.addressbookapplication.dao.ContactEntryDAO;
import au.com.reece.addressbookapplication.model.ContactEntry;
import au.com.reece.addressbookapplication.persistence.jpa.ContactEntryRepository;

@Component
public class ContactEntryDAOImpl implements ContactEntryDAO<ContactEntry> {

	private ContactEntryRepository contactEntryRepository;

	@Autowired
	public ContactEntryDAOImpl(ContactEntryRepository contactRepository) {
		this.contactEntryRepository = contactRepository;
	}
	@Override
	public List<ContactEntry> getAll() {
		return contactEntryRepository.findAll();
	}
	@Override
	public List<ContactEntry> getContactEntryByPhoneNumber(Integer phNumber, String name) {
		return contactEntryRepository.findByPhoneNumberAndName(phNumber, name);
	}
	@Override
	public Integer deleteByPhoneNumber(Integer phNumber) {
		return contactEntryRepository.deleteByPhoneNumber(phNumber);
	}
	@Override
	public Optional<ContactEntry> upsert(ContactEntry contactEntry) {
		return Optional.ofNullable(contactEntryRepository.save(contactEntry));
	}
	@Override
	public Optional<ContactEntry> getContactEntryByPhoneNumber(Integer phNumber) {

		if (!contactEntryRepository.findByPhoneNumber(phNumber).isEmpty()) {
			return Optional.ofNullable(contactEntryRepository.findByPhoneNumber(phNumber).get(0));
		}
		return Optional.empty();
	}
}
