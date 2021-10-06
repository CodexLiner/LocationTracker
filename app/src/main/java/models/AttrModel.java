package models;

public class AttrModel {
    String device_id , command , status , date , time , timing , attr , id , filepath , file_id;

    public AttrModel() {
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getFile_id() {
        return file_id;
    }

    public void setFile_id(String file_id) {
        this.file_id = file_id;
    }

    public AttrModel(String device_id, String command, String status, String date, String time, String timing, String attr, String id, String filepath, String file_id) {
        this.device_id = device_id;
        this.command = command;
        this.status = status;
        this.date = date;
        this.time = time;
        this.timing = timing;
        this.attr = attr;
        this.id = id;
        this.filepath = filepath;
        this.file_id = file_id;
    }
}
