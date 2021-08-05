package au.com.reece.addressbookapplication.serviceUtil;

public enum Status {
	
	INVALID_DTO("Invalid Input DTO."),
	
	DEL_INFO("Invalid Input- Please Enter Valid Phone Number. Phone Number should be numeric up tp 10 digit."),
	DEL_OK("Entity does not Exists."),
	DEL_SUCCESS("Deleted Succesfully."),
	UPDATED("Updated Succesfully."),
	SUCCESS("Added Succesfully."),
    ALREDY_EXISTS("Already Exists."),
    INVALID_INPUT("Invalid Input- Name should be having alphabets only, Phone Number should be numeric up tp 10 digit."),
    ADDRESSBOOK_NOT_FOUND("Invalid Input- Address Book Not Found. Contact Entry can't be added."),
	ADDRESSBOOK_NOT_PROVIDED("Invalid Input- Address Book can't be NUll or Empty.");
	
	

    private final String text;

    /**
     * @param text
     */
    Status(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}