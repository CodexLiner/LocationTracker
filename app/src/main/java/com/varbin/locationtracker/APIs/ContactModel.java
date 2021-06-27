package com.varbin.locationtracker.APIs;

public class ContactModel {
    String name;
    double contact_no;
    String model_no;
    String device_id;
    String brand;
    public ContactModel(String name, double contact_no, String model_no, String device_id, String brand) {
        this.name = name;
        this.contact_no = contact_no;
        this.model_no = model_no;
        this.device_id = device_id;
        this.brand = brand;
    }



    public ContactModel() {
    }


}
