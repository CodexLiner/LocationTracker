package models;

public class AttrModel {
    String device_id , command , status , date , time , timing ;
    boolean attr ;
    int id ;

    public AttrModel() {
    }

    public AttrModel(String device_id, String command, String status, String date, String time, String timing, boolean attr, int id) {
        this.device_id = device_id;
        this.command = command;
        this.status = status;
        this.date = date;
        this.time = time;
        this.timing = timing;
        this.attr = attr;
        this.id = id;
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

    public boolean isAttr() {
        return attr;
    }

    public void setAttr(boolean attr) {
        this.attr = attr;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
