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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import au.com.reece.addressbookapplication.dto.ContactEntryDTO;
import au.com.reece.addressbookapplication.service.ContactEntryService;
import au.com.reece.addressbookapplication.serviceUtil.Status;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/contactentry-management")
public class ContactEntryAPI {

	private static final Logger LOGGER = LogManager.getLogger(ContactEntryAPI.class);

	private ContactEntryService<ContactEntryDTO> contactService;

	@Autowired
	public ContactEntryAPI(ContactEntryService<ContactEntryDTO> contactService) {
		this.contactService = contactService;
	}

	@Operation(summary = "Find all Unique Contact Entries",
			description = "Find all Unique Contact Entries Across all the Address Books.")
	@GetMapping(value = "/all-contacts",
			produces= {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<List<ContactEntryDTO>> getAllContactEntry() {
		
		LOGGER.info("getAllContactEntry Started ...");
		List<ContactEntryDTO> allEntry = contactService.getAll();
		LOGGER.info("getAllContactEntry Completed.");
		return new ResponseEntity<List<ContactEntryDTO>>(allEntry, HttpStatus.OK);
	}

	@Operation(summary = "Find all Contact Entries From an Address Book",
			description = "Find all Contact Entries From an Address Book provided as Request Parameter.")
	@GetMapping(value = "/contacts",
			produces= {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<List<ContactEntryDTO>> getAllContactEntry(@RequestParam(required = true) @NotBlank String addressbookname) {
		
		LOGGER.info("getAllContactEntry Started ...");
		List<ContactEntryDTO> allEntry = contactService.getAll(addressbookname);
		LOGGER.info("getAllContactEntry Completed.");
		return new ResponseEntity<List<ContactEntryDTO>>(allEntry, HttpStatus.OK);
	}
	
	@Operation(summary = "Add a Contact Entry",
			description = "Add a Contact Entry in provided the Address Book as Request Parameter.")
	@PostMapping(value = "/contact", 
			consumes = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<String> addContactEntry(@RequestBody ContactEntryDTO entry,
			@RequestParam(required = true) @NotBlank String addressbookname) {
		
		LOGGER.info("addContactEntry Started ...");
		Status status = contactService.add(entry, addressbookname);
		LOGGER.info("addContactEntry Completed.");
		return new ResponseEntity<String>(status.toString(), HttpStatus.OK);
	}
	
	@Operation(summary = "Delete a Contact Entry",
			description = "Delete a Contact Entiry Across the Address Books with given Phone Number as Request Parameter.")
	@DeleteMapping(value = "/contact/{phonenumber}")
	public ResponseEntity<String> deleteContactEntry(@PathVariable(required = true) @NotBlank Integer phonenumber) {
		
		LOGGER.info("addContactEntry Started ...");
		Status status = contactService.delete(phonenumber);
		LOGGER.info("addContactEntry Completed.");
		return new ResponseEntity<String>(status.toString(), HttpStatus.OK);
	}
}
