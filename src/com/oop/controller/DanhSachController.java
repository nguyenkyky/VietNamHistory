package com.oop.controller;

import com.oop.model.ThoiKyModel;
import com.oop.service.ThoiKyService;
import com.oop.service.impl.ThoiKyServiceImpl;
import com.oop.util.HienThi;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.List;

public class DanhSachController {

    @FXML
    private VBox listContainer;
    @FXML
    private Label danhSachTitle;
    private VBox root;

    public DanhSachController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("com\\oop\\view\\danhsach.fxml"));
//        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            root = fxmlLoader.load();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    private VBox main;

    public void add(VBox main) {
        main.getChildren().add(root);
    }

    public void add(ScrollPane scrollPane) {
        scrollPane.setContent(root);
    }

    public void setDanhSachTitle(String s) {
        danhSachTitle.setText(s);
    }
}
