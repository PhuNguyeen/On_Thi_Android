package com.example.nguyenducphu_181201867_checkbox;

import java.io.Serializable;

public class Contact_181201867 implements Serializable {
    private Integer Id;
    private String name;
    private String phoneNumber;
    private boolean status;

    public Contact_181201867(Integer id) {
        Id = id;
    }

    public Contact_181201867(Integer id, String name, String phoneNumber, boolean status) {
        Id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.status = status;
    }
    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
