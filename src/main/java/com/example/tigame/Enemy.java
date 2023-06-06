package com.example.tigame;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Enemy extends Drawing implements Runnable{
    private Image[] image;
    private boolean movingUp = true;
    private boolean movingRight = true;
    private int frame;
    private int durability;
    public Enemy(Vector pos){
        durability = 10;
        this.pos = pos;
        this.frame = 0;
        this.width = 50;
        this.heigh = 70;
        image = new Image[10];
        for(int i=0;i<10;i++){
            String uri = "file:"+GameApplication.class.getResource("ZombieWalk/zombieWalk"+(i+1)+".png").getPath();
            image[i] = new Image(uri);
        }
    }

    public int getDurability() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

    public boolean isMovingUp() {
        return movingUp;
    }
    public void setMovingUp(boolean movingUp) {
        this.movingUp = movingUp;
    }

    public boolean isMovingRight() {
        return movingRight;
    }

    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.drawImage(image[frame],pos.getX(),pos.getY(),50,70);
    }

    @Override
    public void run() {
        while (true) {
            frame = (frame+1)%10;
            try {
                Thread.sleep(80);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
