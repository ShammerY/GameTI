package com.example.tigame;

import javafx.scene.image.Image;

import java.util.ArrayList;

public class GameUI {
    private ArrayList<Image> hearts;
    private Image ammo;
    public GameUI(){
        hearts = new ArrayList<>();
        setHearts();
        setAmmoUI();
    }

    private void setAmmoUI() {
        String uri = "file:"+GameApplication.class.getResource("gameUI/ammo.png").getPath();
        this.ammo = new Image(uri);
    }

    private void setHearts(){
        for(int i=0;i<3;i++){
            String p = "file:"+GameApplication.class.getResource("gameUI/Heart1.png").getPath();
            //String uri = "file:"+GameApplication.class.getResource("rainbowMan_idle/rainbowMan-idle"+(i+1)+".png").getPath();
            Image image = new Image(p);
            this.hearts.add(image);
        }
    }
    public Image getAmmoUI(){return this.ammo;}
    public ArrayList<Image> getHearts(){return this.hearts;}
}
