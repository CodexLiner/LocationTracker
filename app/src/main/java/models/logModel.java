package models;

import java.util.Date;

public class logModel {
      String model_no ,device_id, brand, contact_no, call_type, date, duration;

    public logModel() {
    }

    public logModel(String model_no, String device_id, String brand, String contact_no, String call_type, String date, String duration) {
        this.model_no = model_no;
        this.device_id = device_id;
        this.brand = brand;
        this.contact_no = contact_no;
        this.call_type = call_type;
        this.date = date;
        this.duration = duration;
    }
}
