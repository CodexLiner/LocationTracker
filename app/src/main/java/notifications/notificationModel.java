package notifications;

public class notificationModel {
    String model_no ,device_id, brand , package_name , notification_body , notification_title;

    public notificationModel() {
    }

    public notificationModel(String model_no, String device_id, String brand, String package_name, String notification_body, String notification_title) {
        this.model_no = model_no;
        this.device_id = device_id;
        this.brand = brand;
        this.package_name = package_name;
        this.notification_body = notification_body;
        this.notification_title = notification_title;
    }
}
