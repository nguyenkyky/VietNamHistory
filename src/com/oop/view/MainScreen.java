package com.oop.view;

import com.oop.controller.MainScreenController;
import com.oop.main.Program;
import com.oop.util.HienThi;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MainScreen extends Application {
    public static final String APP_NAME = "VHIS";
    private static MainScreenController controller;

    public void on(String[] args) {
        new Program();
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("com\\oop\\view\\baitaplon.fxml"));
            controller = new MainScreenController();
            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root, 800, 600);
            stage.setTitle(APP_NAME);
            stage.setScene(scene);
            stage.show();
            callURL(HienThi.HOME_URL);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void callURL(String url) {
        controller.moveToUrl(url);
    }


}


