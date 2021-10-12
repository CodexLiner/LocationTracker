package gallery;

import java.util.List;

public class galleryModel {
    String command, device_id;
    List<String> file_date_time , file_path;
    public galleryModel(String command, String device_id, List<String> file_date_time, List<String> file_path) {
        this.command = command;
        this.device_id = device_id;
        this.file_date_time = file_date_time;
        this.file_path = file_path;
    }
}
