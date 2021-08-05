package au.com.reece.addressbookapplication.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import au.com.reece.addressbookapplication.dao.AddressBookDAO;
import au.com.reece.addressbookapplication.dto.AddressBookDTO;
import au.com.reece.addressbookapplication.model.AddressBook;
import au.com.reece.addressbookapplication.service.AddressBookService;
import au.com.reece.addressbookapplication.serviceUtil.Status;

@Component(value = "addressBookService")
public class AddressBookServiceImpl implements AddressBookService<AddressBookDTO>{

	private static final Logger LOGGER = LogManager.getLogger(AddressBookServiceImpl.class);

	private AddressBookDAO<AddressBook> addressBookDAO;
	
	private ModelMapper modelMapper;

    @Autowired
	public AddressBookServiceImpl(AddressBookDAO<AddressBook> addressBookDAO, ModelMapper modelMapper) {
		this.addressBookDAO = addressBookDAO;
		this.modelMapper = modelMapper;
	}

	@Override
	public List<AddressBookDTO> getAll() {
		
		List<AddressBookDTO> addressBookDTOs = new ArrayList<>();
		addressBookDAO.getAll().stream().forEach(address -> {
			addressBookDTOs.add(modelMapper.map(address, AddressBookDTO.class));});
		return addressBookDTOs;
	}

	@Override
	public Status add(AddressBookDTO entry) {

		try {
			AddressBook model = modelMapper.map(entry, AddressBook.class);
			if (addressBookDAO.isAddressBookExists(model.getAddressBookName())) {
				return Status.ALREDY_EXISTS;	
			}
			addressBookDAO.upsert(model);
		} catch (MappingException e) {
			LOGGER.error("Note able to map DTO to model.", entry);
			return Status.INVALID_DTO;
		}
		return Status.SUCCESS;
	}

	@Override
	public Status delete(String addressBookName) {
		
		if (addressBookDAO.deleteByAddressBookName(addressBookName) > 0) {
			return Status.DEL_SUCCESS;
		}
		return Status.DEL_OK;
	}
	
	@Override
	public Status add(String addressBookName) {
		
		AddressBookDTO dto = new AddressBookDTO();
		dto.setAddressBookName(addressBookName);
		return add(dto);
	}
}
