package com.varbin.locationtracker.APIs;

public class ContactModel {
   String name , contact_no , model_no , device_id ;

    public ContactModel() {
    }

    public ContactModel(String name, String contact_no, String model_no, String device_id) {
        this.name = name;
        this.contact_no = contact_no;
        this.model_no = model_no;
        this.device_id = device_id;
    }
}
