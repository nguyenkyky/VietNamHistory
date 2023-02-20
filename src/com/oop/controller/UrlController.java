package com.oop.controller;

import com.oop.view.MainScreen;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class UrlController {

    @FXML
    private Label url;

    @FXML
    private VBox urlContainer;

    private VBox root;
    private String showUrl;
    private String urlStr;

    public UrlController(String realUrl) {
        this.urlStr = realUrl;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("com\\oop\\view\\urlContainer.fxml"));
//        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            root = fxmlLoader.load();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public void add(HBox main) {
        main.getChildren().add(root);
    }

    public void setUrl(String s) {
        url.setText(s);
    }

    @FXML
    void urlPressed(MouseEvent event) {
        MainScreen.callURL(urlStr);
    }

}

