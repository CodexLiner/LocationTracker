package callDetails;

public class contactModel {
    String name , contactId , ContactNumber ;

    public contactModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getContactNumber() {
        return ContactNumber;
    }

    public void setContactNumber(String contactNumber) {
        ContactNumber = contactNumber;
    }

    public contactModel(String name, String contactId, String contactNumber) {
        this.name = name;
        this.contactId = contactId;
        ContactNumber = contactNumber;
    }
}
