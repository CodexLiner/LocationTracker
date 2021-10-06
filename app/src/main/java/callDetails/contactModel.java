package callDetails;

public class contactModel {
    String model_no , device_id , brand, name , contact_number;

    public contactModel(String model_no, String device_id, String brand, String name, String contact_number) {
        this.model_no = model_no;
        this.device_id = device_id;
        this.brand = brand;
        this.name = name;
        this.contact_number = contact_number;
    }

    public contactModel() {
    }

}
