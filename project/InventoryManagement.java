package com.arusnac.project;

/**
 * Starting point for application
 *
 * @author Andrew Rusnac
 *
 *
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class InventoryManagement extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(InventoryManagement.class.getResource("main-form.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 930, 442);

        stage.setTitle("Inventory Management");
        stage.setScene(scene);
        stage.show();


    }


    public static void main(String[] args) {
        launch();
    }
}