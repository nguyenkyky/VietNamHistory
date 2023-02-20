package com.oop.model;

public class DanhNhanModel extends Model{

    private String description;

    public DanhNhanModel(String name) {
        super(name);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
