package com.example.nguyenducphu_181201867_vetau;

public class Contact_181201867 {
    private Integer Id;
    private String gaDi;
    private String gaDen;
    private Double donGia;
    private boolean status;

    public Contact_181201867(Integer id, String gaDi, String gaDen, Double donGia, boolean status) {
        Id = id;
        this.gaDi = gaDi;
        this.gaDen = gaDen;
        this.donGia = donGia;
        this.status = status;
    }

    public Contact_181201867(Integer id) {
        Id = id;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getGaDi() {
        return gaDi;
    }

    public void setGaDi(String gaDi) {
        this.gaDi = gaDi;
    }

    public String getGaDen() {
        return gaDen;
    }

    public void setGaDen(String gaDen) {
        this.gaDen = gaDen;
    }

    public Double getDonGia() {
        return donGia;
    }

    public void setDonGia(String donGia) {
        donGia = donGia;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
