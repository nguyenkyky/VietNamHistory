package com.oop.model;

public class DiaDanhModel extends Model{
    private String description;
    public DiaDanhModel(String name) {
        super(name);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
