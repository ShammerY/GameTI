package com.example.tigame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class GameApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        openWindow("menuWindow.fxml");
    }
    public static File getFile(String fileName) {
        return new File(Objects.requireNonNull(GameApplication.class.getResource(fileName)).getPath());
    }
    public static void openWindow(String path) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(GameApplication.class.getResource(path));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("GAME FOR U");
            String uri = "file:"+GameApplication.class.getResource("backgrounds/aimCursor1.png").getPath();
            Cursor cursor = Cursor.cursor(uri);
            scene.setCursor(cursor);
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