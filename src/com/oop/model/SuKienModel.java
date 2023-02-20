package com.oop.model;

import java.util.ArrayList;
import java.util.List;

public class SuKienModel extends Model{
    private String description;
    private List<String> nhanVatLienQuan = new ArrayList<>();
    private List<String> diaDanhLienQuan = new ArrayList<>();
    public SuKienModel(String name) {
        super(name);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getNhanVatLienQuan() {
        return nhanVatLienQuan;
    }

    public void setNhanVatLienQuan(List<String> nhanVatLienQuan) {
        this.nhanVatLienQuan = nhanVatLienQuan;
    }

    public List<String> getDiaDanhLienQuan() {
        return diaDanhLienQuan;
    }

    public void setDiaDanhLienQuan(List<String> diaDanhLienQuan) {
        this.diaDanhLienQuan = diaDanhLienQuan;
    }
}
