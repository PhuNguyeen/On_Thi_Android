package com.example.hoten_msv;

public class Contact_TenSV {
    private Integer Id;
    private String HoTen;
    private String SDT;
    public Contact_TenSV(Integer id, String hoTen, String SDT) {
        Id = id;
        HoTen = hoTen;
        this.SDT = SDT;
    }
    public Contact_TenSV(Integer id) {
        Id = id;
    }
    public Integer getId() {
        return Id;
    }
    public void setId(Integer id) {
        Id = id;
    }
    public String getHoTen() {
        return HoTen;
    }
    public void setHoTen(String hoTen) {
        HoTen = hoTen;
    }
    public String getSDT() {
        return SDT;
    }
    public void setSDT(String SDT) {
        this.SDT = SDT;
    }
}
