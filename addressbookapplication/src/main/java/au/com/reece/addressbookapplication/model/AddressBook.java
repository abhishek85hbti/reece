package au.com.reece.addressbookapplication.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="ADDRESS_BOOK")
public class AddressBook 
{
	
	@Id
	@Column(name="ADDRESS_BOOK_NAME")
	private String addressBookName;
	
	@ManyToMany(mappedBy="addressBook", cascade = CascadeType.ALL)
	private List<ContactEntry> contactEntries = new ArrayList<>();
	
	public AddressBook() {
	}
	public AddressBook(String addressBookName) {
		this.addressBookName = addressBookName;
	}
	/**
	 * @return the contactEntries
	 */
	public List<ContactEntry> getContactEntries() {
		return contactEntries;
	}
	/**
	 * @param contactEntries the contactEntries to set
	 */
	public void setContactEntries(List<ContactEntry> entries) {
		this.contactEntries = entries;
		for (ContactEntry entry : contactEntries) {
			entry.getAddressBook().add(this);
		}
	}
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
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof AddressBook)) return false;
		AddressBook that = (AddressBook) o;
		return Objects.equals(getAddressBookName(), that.getAddressBookName());
	}
	@Override
	public int hashCode() {
		return Objects.hash(getAddressBookName(), getAddressBookName());
	}
}
