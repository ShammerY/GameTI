package com.example.tigame;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CharacterMenuController implements Initializable {
    @FXML
    public ImageView ch1IV;
    @FXML
    public ImageView ch2IV;
    @FXML
    public ImageView ch3IV;
    private boolean isAlive = true;
    @FXML
    public Canvas canvas;
    private GraphicsContext gc;
    @FXML
    private Rectangle character1SQ;

    @FXML
    private Rectangle character2SQ;

    @FXML
    private Rectangle character3SQ;

    @FXML
    private Label returnBT;

    @FXML
    void ReturnToMenu(MouseEvent event) {
        GameApplication.openWindow("menuWindow.fxml");
        Stage stage = (Stage) returnBT.getScene().getWindow();
        stage.close();
    }
    private void returnMenu(){
        GameApplication.openWindow("menuWindow.fxml");
        Stage stage = (Stage) returnBT.getScene().getWindow();
        stage.close();
    }

    @FXML
    void ch1Entered(MouseEvent event) {
        character1SQ.setStroke(Color.BLUEVIOLET);
    }

    @FXML
    void ch1Exited(MouseEvent event) {
        character1SQ.setStroke(Color.BLACK);

    }

    @FXML
    void ch1Select(MouseEvent event) {
        CharacterSelection.getInstance().setCharacterID(1);
        returnMenu();
    }
    @FXML
    void ch2Entered(MouseEvent event) {
        character2SQ.setStroke(Color.BLUEVIOLET);
    }

    @FXML
    void ch2Exited(MouseEvent event) {
        character2SQ.setStroke(Color.BLACK);

    }

    @FXML
    void ch2Select(MouseEvent event) {
        CharacterSelection.getInstance().setCharacterID(2);
        returnMenu();
    }

    @FXML
    void ch3Entered(MouseEvent event) {
        character3SQ.setStroke(Color.BLUEVIOLET);
    }

    @FXML
    void ch3Exited(MouseEvent event) {
        character3SQ.setStroke(Color.BLACK);
    }
    @FXML
    void ch3Select(MouseEvent event) {
        CharacterSelection.getInstance().setCharacterID(3);
        returnMenu();
    }
    @FXML
    void mouseEnteredReturn(MouseEvent event) {
        returnBT.setTextFill(Color.WHITE);
    }
    @FXML
    void mouseExitedReturn(MouseEvent event) {
        returnBT.setTextFill(Color.PURPLE);

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image image = new Image("file:"+GameApplication.class.getResource("backgrounds/Space_Background.png").getPath());
        gc = canvas.getGraphicsContext2D();
        canvas.setFocusTraversable(true);
        gc.drawImage(image,0,0,canvas.getWidth(),canvas.getHeight());
        returnBT.setTextFill(Color.PURPLE);
        ch1IV.setImage(new Image("file:"+GameApplication.class.getResource("Black/Quieto/Gunner_Black_Idle_1.png").getPath()));
        ch2IV.setImage(new Image("file:"+GameApplication.class.getResource("Blue/Quieto/Gunner_Blue_Idle_1.png").getPath()));
        ch3IV.setImage(new Image("file:"+GameApplication.class.getResource("Red/Quieto/Gunner_Red_Idle_1.png").getPath()));

    }
}
