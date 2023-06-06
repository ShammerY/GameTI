package com.example.tigame;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Obstacle extends Drawing{
    private Image pic;
    private int durability;
    public Obstacle(Vector pos){
        this.width = 50;
        this.heigh = 50;
        this.pos = pos;
        String uri = "file:"+GameApplication.class.getResource("obstacles/barrel1.png").getPath();
        pic = new Image(uri);
        durability = 5;
    }
    public int getWidth(){
        return this.width;
    }
    public int getHeigh(){
        return this.heigh;
    }
    @Override
    public void draw(GraphicsContext gc) {
        //gc.setFill(Color.RED);
        //gc.fillRect(pos.getX(),pos.getY(),100,100);
        gc.drawImage(pic,pos.getX(),pos.getY(),50,50);
    }
    public void setDurability(int n){this.durability = n;}
    public int getDurability(){return this.durability;}
}
