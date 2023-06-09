package com.example.tigame;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Weapon extends Drawing{
    private Image pic;
    int id;
    public Weapon(Vector pos, int id){
        this.width = 50;
        this.heigh = 50;
        this.pos = pos;
        this.id = id;
        switch (this.id){
            case 1:
                setPistol();
                break;
            case 2:
                setShotgun();
                break;
            case 3:
                setRifle();
                break;
        }
    }


    private void setPistol() {
        this.width = 32;
        this.heigh = 32;
        String uri = "file:"+GameApplication.class.getResource("Guns/Revolver.png").getPath();
        pic = new Image(uri);
    }

    private void setShotgun() {
        this.width = 25;
        this.heigh = 60;
        String uri = "file:"+GameApplication.class.getResource("Guns/Shotgun.png").getPath();
        pic = new Image(uri);
    }

    private void setRifle() {
        this.width = 25;
        this.heigh = 60;
        String uri = "file:"+GameApplication.class.getResource("Guns/M15.png").getPath();
        pic = new Image(uri);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.drawImage(pic,pos.getX(),pos.getY(),this.width,this.heigh);
    }
}
