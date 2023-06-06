package com.example.tigame;

import javafx.scene.image.Image;

import java.util.ArrayList;

public class GameUI {
    private ArrayList<Image> hearts;
    public GameUI(){
        hearts = new ArrayList<>();
        setHearts();
    }
    private void setHearts(){
        for(int i=0;i<3;i++){
            String p = "file:"+GameApplication.class.getResource("gameUI/Heart1.png").getPath();
            //String uri = "file:"+GameApplication.class.getResource("rainbowMan_idle/rainbowMan-idle"+(i+1)+".png").getPath();
            Image image = new Image(p);

            this.hearts.add(image);
        }
    }
    public ArrayList<Image> getHearts(){return this.hearts;}
}
