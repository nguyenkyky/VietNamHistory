package com.oop.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class DetailController {

    @FXML
    private VBox detailContainer;

    @FXML
    private Label detailTitle;

    private VBox root;

    public DetailController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("com\\oop\\view\\detail.fxml"));
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

    public void setDetailTitle(String s) {
        detailTitle.setText(s);
    }

}

