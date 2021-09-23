package models;

import com.google.gson.annotations.SerializedName;

public class FileUpload {
    @SerializedName("postcommand")
    String commnad;
    String device_id;
    String file_date_time;
    String file_path;

    public FileUpload(String postCommand, String device_id, String file_date_time, String file_path) {
        this.commnad = postCommand;
        this.device_id = device_id;
        this.file_date_time = file_date_time;
        this.file_path = file_path;
    }
}

