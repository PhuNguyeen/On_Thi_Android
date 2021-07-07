package com.example.nguyenducphu_181201867_thietbi;

import java.io.Serializable;

public class Contact_181201867 implements Serializable {
    private Integer Id;
    private String tenThietBi;
    private String moTa;
    private String image;
    private boolean status;

    public Contact_181201867(Integer id) {
        Id = id;
    }

    public Contact_181201867(Integer id, String tenThietBi, String moTa, String image, boolean status) {
        Id = id;
        this.tenThietBi = tenThietBi;
        this.moTa = moTa;
        this.image = image;
        this.status = status;
    }
    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getTenThietBi() {
        return tenThietBi;
    }

    public void setTenThietBi(String tenThietBi) {
        this.tenThietBi = tenThietBi;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
