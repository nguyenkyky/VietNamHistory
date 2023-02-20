package com.oop.controller;

import com.oop.view.MainScreen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ListController{

    @FXML
    private Label link;

    @FXML
    private Button show;

    @FXML
    private Label title;

    public void setLink(String s) {
        link.setText(s);
    }

    public void setTitle(String s) {
        title.setText(s);
    }

    private AnchorPane root;

    public ListController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("com\\oop\\view\\customList.fxml"));
//        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            root = fxmlLoader.load();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public void add(VBox vBox) {
        vBox.getChildren().add(root);
    }

    @FXML
    void showButtonPressed(ActionEvent event) {
        String url = link.getText();
        MainScreen.callURL(url);
    }

}

