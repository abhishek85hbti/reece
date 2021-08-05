package au.com.reece.addressbookapplication.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class ContactEntryDTO implements Serializable {

	@NotNull
	private String name;

	@NotNull
	private Integer phoneNumber;
	
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
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(Integer phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	/**
	 * @return the phoneNumber
	 */
	public Integer getPhoneNumber() {
		return phoneNumber;
	}
	@Override
	public String toString() {
		return "ContactEntryDTO [name=" + name + ", phoneNumber=" + phoneNumber + "]";
	}
}
