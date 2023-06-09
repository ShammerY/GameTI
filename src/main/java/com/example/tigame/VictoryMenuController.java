package com.example.tigame;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class VictoryMenuController implements Initializable {
    private GraphicsContext gc;
    @FXML
    private Label btMenu;
    @FXML
    private Canvas cambasWin;
    @FXML
    private Label scoreLv;
    @FXML
    void returnMenu(MouseEvent event) {
        GameApplication.openWindow("menuWindow.fxml");
        Stage stage=(Stage) btMenu.getScene().getWindow();
        stage.close();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image image = new Image("file:"+GameApplication.class.getResource("backgrounds/Space_Background.png").getPath());
        gc = cambasWin.getGraphicsContext2D();
        cambasWin.setFocusTraversable(true);
        gc.drawImage(image,0,0,cambasWin.getWidth(),cambasWin.getHeight());

    }
}
