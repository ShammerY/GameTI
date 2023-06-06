package com.example.tigame;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuWindowController implements Initializable {
    private GraphicsContext gc;
    @FXML
    public Canvas canvas;
    @FXML
    private Label charactersBT;

    @FXML
    private Label playBT;

    @FXML
    private Label scoresBT;

    @FXML
    void displayCharacters(MouseEvent event) {

    }

    @FXML
    void displayScores(MouseEvent event) {

    }

    @FXML
    void playGame(MouseEvent event) {
        GameApplication.openWindow("hello-view.fxml");
        Stage stage = (Stage) scoresBT.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image image = new Image("file:"+GameApplication.class.getResource("backgrounds/The_Midnight_Background.png").getPath());
        gc = canvas.getGraphicsContext2D();
        canvas.setFocusTraversable(true);
        gc.drawImage(image,0,0,canvas.getWidth(),canvas.getHeight());
        playBT.setTextFill(Color.PURPLE);
        charactersBT.setTextFill(Color.PURPLE);
        scoresBT.setTextFill(Color.PURPLE);
    }
    @FXML
    public void mouseEnteredPlay(MouseEvent mouseEvent) {
        playBT.setTextFill(Color.WHITE);
    }
    @FXML
    public void mouseEnteredCharacters(MouseEvent mouseEvent) {
        charactersBT.setTextFill(Color.WHITE);
    }
    @FXML
    public void mouseEnteredScores(MouseEvent mouseEvent) {
        scoresBT.setTextFill(Color.WHITE);
    }
    @FXML
    public void mouseExitedPlay(MouseEvent mouseEvent) {
        playBT.setTextFill(Color.PURPLE);
    }
    @FXML
    public void mouseExitedCharacters(MouseEvent mouseEvent) {
        charactersBT.setTextFill(Color.PURPLE);
    }
    @FXML
    public void mouseExitedScores(MouseEvent mouseEvent) {
        scoresBT.setTextFill(Color.PURPLE);
    }
}
