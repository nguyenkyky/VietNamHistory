package com.oop.model;

public abstract class Model {
    protected String name;
    protected Model(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
