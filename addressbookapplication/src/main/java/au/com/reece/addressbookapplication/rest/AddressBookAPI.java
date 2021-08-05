package au.com.reece.addressbookapplication.rest;

import java.util.List;

import javax.validation.constraints.NotBlank;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import au.com.reece.addressbookapplication.dto.AddressBookDTO;
import au.com.reece.addressbookapplication.service.AddressBookService;
import au.com.reece.addressbookapplication.serviceUtil.Status;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/addressbook-management")
public class AddressBookAPI {

	private static final Logger LOGGER = LogManager.getLogger(AddressBookAPI.class);

	private AddressBookService<AddressBookDTO> addressBookService;
	
	@Autowired
	public AddressBookAPI(AddressBookService<AddressBookDTO> service) {
		this.addressBookService = service;
	}
	
	@Operation(summary = "Find All AddressBooks",
			description = "Find All AddressBooks along with Associated Contact Entries.")
	@GetMapping(value = "/addressbooks",
			produces= {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<List<AddressBookDTO>> getAllAddressBooks() {
		
		LOGGER.info("getAllAddressBooks Started ...");
		List<AddressBookDTO> allEntry = addressBookService.getAll();
		LOGGER.info("getAllAddressBooks Completed.");
		return new ResponseEntity<List<AddressBookDTO>>(allEntry, HttpStatus.OK);
	}

	@Operation(summary = "Add an AddressBook",
			description = "Add an AddressBook.")
	@PostMapping(value = "/addressbook")
	public ResponseEntity<String> addAddressBook(@RequestParam(required = true) @NotBlank String addressbookname) {
		
		LOGGER.info("addAddressBook Started ...");
		Status status = addressBookService.add(addressbookname);
		LOGGER.info("addAddressBook Completed.");
		return new ResponseEntity<String>(status.toString(), HttpStatus.CREATED);
	}
	
	@Operation(summary = "Delete an Address Book",
			description = "Delete an Address Book.")
	@DeleteMapping(value = "/addressbook/{addressbookname}")
	public ResponseEntity<String> deleteAddressBook(@PathVariable(required = true) @NotBlank String addressbookname) {
		
		LOGGER.info("addContactEntry Started ...");
		Status status = addressBookService.delete(addressbookname);
		LOGGER.info("addContactEntry Completed.");
		return new ResponseEntity<String>(status.toString(), HttpStatus.OK);
	}
}
