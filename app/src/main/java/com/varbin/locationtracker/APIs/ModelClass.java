package com.varbin.locationtracker.APIs;

public class ModelClass {
    String model_no , device_id ,brand ;
    double latitude , longitude;

    public ModelClass() {
    }

    public ModelClass(String model_no, String device_id, String brand, double latitude, double longitude) {
        this.model_no = model_no;
        this.device_id = device_id;
        this.brand = brand;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getModel_no() {
        return model_no;
    }

    public void setModel_no(String model_no) {
        this.model_no = model_no;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
