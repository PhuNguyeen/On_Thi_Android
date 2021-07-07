package com.example.nguyenducphu_181201867_quanan;

public class Contact_181201867 {
    private Integer Id;
    private String TenNhaHang;
    private Double DanhGia;
    private String DiaDiem;
//    private Integer GiaKhuyenMai;


    public Contact_181201867(Integer id, String tenNhaHang, String diaDiem, Double danhGia) {
        Id = id;
        TenNhaHang = tenNhaHang;
        DanhGia = danhGia;
        DiaDiem = diaDiem;
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

    public String getTenNhaHang() {
        return TenNhaHang;
    }

    public void setTenNhaHang(String tenNhaHang) {
        TenNhaHang = tenNhaHang;
    }

    public Double getDanhGia() {
        return DanhGia;
    }

    public void setDanhGia(Double danhGia) {
        DanhGia = danhGia;
    }

    public String getDiaDiem() {
        return DiaDiem;
    }

    public void setDiaDiem(String diaDiem) {
        DiaDiem = diaDiem;
    }
}
