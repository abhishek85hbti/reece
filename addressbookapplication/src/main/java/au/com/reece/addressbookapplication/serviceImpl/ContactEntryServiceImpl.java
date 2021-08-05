package au.com.reece.addressbookapplication.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import au.com.reece.addressbookapplication.dao.AddressBookDAO;
import au.com.reece.addressbookapplication.dao.ContactEntryDAO;
import au.com.reece.addressbookapplication.dto.ContactEntryDTO;
import au.com.reece.addressbookapplication.model.AddressBook;
import au.com.reece.addressbookapplication.model.ContactEntry;
import au.com.reece.addressbookapplication.service.ContactEntryService;
import au.com.reece.addressbookapplication.serviceUtil.Status;

@Component(value = "contactEntryService")
public class ContactEntryServiceImpl implements ContactEntryService<ContactEntryDTO> {

	private static final Logger LOGGER = LogManager.getLogger(ContactEntryServiceImpl.class);

	private ContactEntryDAO<ContactEntry> contactEntryDAO;
	
	private AddressBookDAO<AddressBook> addressBookDAO;
	
	private ModelMapper modelMapper;

    @Autowired
	public ContactEntryServiceImpl(ContactEntryDAO<ContactEntry> contactEntryDAO,
			AddressBookDAO<AddressBook> addressBookDAO, ModelMapper modelMapper) {
		this.contactEntryDAO = contactEntryDAO;
		this.addressBookDAO = addressBookDAO;
		this.modelMapper = modelMapper;
	}

	@Override
	public List<ContactEntryDTO> getAll() {

		List<ContactEntryDTO> contactEntryDTOs = new ArrayList<>();
		contactEntryDAO.getAll().stream().forEach(entry -> {
			contactEntryDTOs.add(modelMapper.map(entry, ContactEntryDTO.class));});
		return contactEntryDTOs;
	}
	
	@Override
	public List<ContactEntryDTO> getAll(String addressBookName) {

		List<ContactEntryDTO> contactEntryDTOs = new ArrayList<>();
		if (!addressBookDAO.getAddressBook(addressBookName).isEmpty()) {
			AddressBook AddressBook = addressBookDAO.getAddressBook(addressBookName).get(0);
			AddressBook.getContactEntries().stream().forEach(entry -> {
				contactEntryDTOs.add(modelMapper.map(entry, ContactEntryDTO.class));});
		}
		return contactEntryDTOs;
	}
	
	@Override
	public Status delete(Integer phoneNumber) {
		
		if (contactEntryDAO.deleteByPhoneNumber(phoneNumber) > 0) {
			return Status.DEL_SUCCESS;
		}
		return Status.DEL_OK;
	}
	
	@Override
	public Status add(ContactEntryDTO entry, String addressBookName) {

		if (!addressBookDAO.isAddressBookExists(addressBookName)) {
			return Status.ADDRESSBOOK_NOT_FOUND;	
		}
		boolean isContactUpdate = false;
		try {
			ContactEntry model = modelMapper.map(entry, ContactEntry.class);
			Optional<ContactEntry> contactEntry = contactEntryDAO.
					getContactEntryByPhoneNumber(entry.getPhoneNumber());
			if (contactEntry.isPresent()) {
				isContactUpdate = true;
				contactEntry.get().setName(entry.getName());
				contactEntry.get().addAddressBook(new AddressBook(addressBookName));
				model = contactEntry.get();
			}
			model.addAddressBook(new AddressBook(addressBookName));
			contactEntryDAO.upsert(model);
		} catch (MappingException e) {
			LOGGER.error("Note able to map DTO to model.", entry);
			return Status.INVALID_DTO;
		}
		return (isContactUpdate) ? Status.UPDATED : Status.SUCCESS;
	}
}
