package com.oop.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ChiTietController {
    @FXML
    private Label title;

    @FXML
    private Label value;

    private HBox root;

    public void setTitle(String s) {
        title.setText(s);
    }

    public void setValue(String s) {
        value.setText(s);
    }

    public ChiTietController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("com\\oop\\view\\chitiet.fxml"));
//        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            root = fxmlLoader.load();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public void add(VBox main) {
        main.getChildren().add(root);
    }
}
