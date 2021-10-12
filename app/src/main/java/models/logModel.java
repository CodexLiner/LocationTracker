package models;

import java.util.Date;
import java.util.List;

public class logModel {
    List<String> contact_no, call_type, date, duration;
    String  model_no ,device_id, brand;

    public logModel(List<String> contact_no, List<String> call_type, List<String> date, List<String> duration, String model_no, String device_id, String brand) {
        this.contact_no = contact_no;
        this.call_type = call_type;
        this.date = date;
        this.duration = duration;
        this.model_no = model_no;
        this.device_id = device_id;
        this.brand = brand;
    }

    public logModel() {
    }

}
