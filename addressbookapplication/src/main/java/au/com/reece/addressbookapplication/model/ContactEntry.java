package au.com.reece.addressbookapplication.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="CONTACT_ENTRY")
public class ContactEntry implements Serializable {

	@Column(name="NAME")
	private String name;

	@Id
	@Column(name="PHONE_NUMBER")
	private Integer phoneNumber;
	
	@ManyToMany(cascade = CascadeType.ALL)
	private Set<AddressBook> addressBook = new HashSet<>();
    
	public ContactEntry() {
	}
    public ContactEntry(String name, Integer phoneNumber) {
		this.name = name;
		this.phoneNumber = phoneNumber;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the phoneNumber
	 */
	public Integer getPhoneNumber() {
		return phoneNumber;
	}
	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(Integer phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	/**
	 * @return the addressBook
	 */
	public Set<AddressBook> getAddressBook() {
		return addressBook;
	}
	/**
	 * @param addressBook the addressBook to set
	 */
	public void setAddressBook(Set<AddressBook> books) {
		this.addressBook = books;
	}
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ContactEntry)) return false;
		ContactEntry that = (ContactEntry) o;
		return Objects.equals(getPhoneNumber(), that.getPhoneNumber());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getPhoneNumber());
	}
    public void addAddressBook(AddressBook address) {
    	getAddressBook().add(address);
    }
}
