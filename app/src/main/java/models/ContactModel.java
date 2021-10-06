package models;

public class ContactModel {
    String model_no , device_id , brand, name , contact_no;

    public ContactModel(String model_no, String device_id, String brand, String name, String contact_no) {
        this.model_no = model_no;
        this.device_id = device_id;
        this.brand = brand;
        this.name = name;
        this.contact_no = contact_no;
    }

    public ContactModel() {
    }


}
