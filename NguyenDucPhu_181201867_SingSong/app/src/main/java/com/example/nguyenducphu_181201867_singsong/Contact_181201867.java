    package com.example.nguyenducphu_181201867_singsong;

public class Contact_181201867 {
    private Integer Id;
    private String tenBàiHat;
    private String caSi;
    private Double thoiLuong;

//    private Integer GiaKhuyenMai;


    public Contact_181201867(Integer id, String tenBàiHat, String caSi, Double thoiLuong) {
        Id = id;
        this.tenBàiHat = tenBàiHat;
        this.caSi = caSi;
        this.thoiLuong = thoiLuong;
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

    public String getTenBàiHat() {
        return tenBàiHat;
    }

    public void setTenBàiHat(String tenBàiHat) {
        this.tenBàiHat = tenBàiHat;
    }

    public String getCaSi() {
        return caSi;
    }

    public void setCaSi(String caSi) {
        this.caSi = caSi;
    }

    public Double getThoiLuong() {
        return thoiLuong;
    }

    public void setThoiLuong(Double thoiLuong) {
        this.thoiLuong = thoiLuong;
    }
}
