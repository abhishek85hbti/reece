package au.com.reece.addressbookapplication.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

public class AddressBookDTO implements Serializable {

	@NotNull
	private String addressBookName;
	
	private Set<ContactEntryDTO> contactEntries = new HashSet<>();
	
	/**
	 * @return the addressBookName
	 */
	public String getAddressBookName() {
		return addressBookName;
	}
	/**
	 * @param addressBookName the addressBookName to set
	 */
	public void setAddressBookName(String addressBookName) {
		this.addressBookName = addressBookName;
	}
	/**
	 * @return the contactEntries
	 */
	public Set<ContactEntryDTO> getContactEntries() {
		return contactEntries;
	}
	/**
	 * @param contactEntries the contactEntries to set
	 */
	public void setContactEntries(Set<ContactEntryDTO> contactEntries) {
		this.contactEntries = contactEntries;
	}
	@Override
	public String toString() {
		return "AddressBookDTO [addressBookName=" + addressBookName + ", contactEntries=" + contactEntries + "]";
	}
}
