package com.example.tigame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GameApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        openWindow("menuWindow.fxml");
    }
    public static void openWindow(String path) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(GameApplication.class.getResource(path));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
        }catch(IOException ex){
            ex.printStackTrace();
        }

    }
    public static void main(String[] args) {
        launch();
    }
}