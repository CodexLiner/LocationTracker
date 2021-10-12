package models;

import java.util.List;

public class ContactModel {
    String model_no , device_id , brand;
    List<String> name , contact_no;

    public ContactModel(String model_no, String device_id, String brand, List<String> name, List<String> contact_no) {
        this.model_no = model_no;
        this.device_id = device_id;
        this.brand = brand;
        this.name = name;
        this.contact_no = contact_no;
    }

    public ContactModel() {
    }


}
