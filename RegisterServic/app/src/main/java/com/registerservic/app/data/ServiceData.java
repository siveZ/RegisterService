package com.registerservic.app.data;

import java.io.Serializable;

/**
 * Created by Artyom on 3/20/2015.
 */
public class ServiceData implements Serializable{

    private String name;
    private String address;
    private String phone;
    private String description;
    private String service_type;

    public ServiceData(){

    }

    public ServiceData(String name, String address, String phone, String description, String service_type) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.description = description;
        this.service_type = service_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getService_type() {
        return service_type;
    }

    public void setService_type(String service_type) {
        this.service_type = service_type;
    }
}
