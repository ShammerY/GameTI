package com.example.tigame;

import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import javax.crypto.spec.PSource;

public class Enemy extends Drawing implements Runnable{
    private Image[] image;
    private int id;
    private boolean movingUp = true;
    private boolean movingRight = true;
    private int frame;
    private int durability;
    private Vector direction;
    private Vector initialPos;
    private boolean shot;
    public Enemy(Vector pos,int id,Vector inPos){
        this.initialPos = inPos;
        this.pos = pos;
        this.id = id;
        this.frame = 0;
        switch(this.id){
            case 1:
                setFlyingEye();
                break;
            case 2:
                setZombie();
                break;
            case 3:
                setStormHead();
                break;
            case 4:
                setReaper();
                break;
            case 5:
                setSkeleton();
                break;
        }
    }

    public Vector getInitialPos() {
        return initialPos;
    }

    private void setFlyingEye(){
        this.durability = 5;
        this.width = 50;
        this.heigh = 40;
        image = new Image[8];
        for(int i=0;i<8;i++){
            String uri = "file:"+GameApplication.class.getResource("enemyFlight/FlightEye"+(i+1)+".png").getPath();
            image[i] = new Image(uri);
        }
        setRandomDirection();
    }
    private void setSkeleton(){
        this.durability = 3;
        this.width = 40;
        this.heigh = 44;
        image = new Image[12];
        for(int i=0; i<12;i++){
            String uri = "file:"+GameApplication.class.getResource("skeleton/Skeleton enemy_"+(i+1)+".png").getPath();
            image[i] = new Image(uri);
        }
        direction = new Vector(0,0);
    }
    private void setReaper(){
        this.durability = 8;
        this.width = 70;
        this.heigh = 70;
        image = new Image[8];
        for(int i=0;i<8;i++){
            String url = "file:"+GameApplication.class.getResource("Reaper/Bringer-of-Death_Walk_"+(i+1)+".png").getPath();
            image[i] = new Image(url);
        }
        direction = new Vector(0,0);
    }
    private void setZombie(){
        this.durability = 6;
        this.width = 50;
        this.heigh = 70;
        image = new Image[5];
        for(int i=0;i<5;i++){
            String uri = "file:"+GameApplication.class.getResource("zombieIdle/zombieIdle"+(i+1)+".png").getPath();
            image[i] = new Image(uri);
        }
    }
    private void setStormHead(){
        this.durability = 6;
        this.width = 35;
        this.heigh = 70;
        image = new Image[9];
        for(int i=0;i<9;i++){
            String uri = "file:"+GameApplication.class.getResource("electricEnemy/stormHeadIdle"+(i+1)+".png").getPath();
            image[i] = new Image(uri);
        }
        shot = false;
        direction = new Vector(0,0);
    }
    public boolean shot(){return this.shot;}
    public void setShot(boolean b){this.shot=b;}
    public void setPlayerDirection(double avatarX, double avatarY){
        double diffX = avatarX - pos.getX();
        double diffY = avatarY - pos.getY();
        direction = new Vector(diffX,diffY);
        direction.normalize();
        direction.setMag(4);
    }
    public void setRandomDirection(){
        double x = Math.floor(Math.random()*14+1)*(double)(800/16);
        double y = Math.floor(Math.random()*10+1)*(double)(600/12);
        double diffX = x - pos.getX();
        double diffY = y - pos.getY();
        direction = new Vector(diffX,diffY);
        direction.normalize();
        direction.setMag(4);
    }
    public void shoot(){
        shot = true;
    }

    public int getId() {
        return id;
    }

    public Vector getDirection(){return direction;}
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
        gc.drawImage(image[frame],pos.getX(),pos.getY(),this.width,this.heigh);
    }

    @Override
    public void run() {
        bucle();
        while (true) {
            switch(this.id){
                case 1:
                    frame = (frame+1)%8;
                    break;
                case 2:
                    frame = (frame+1)%5;
                    break;
                case 3:
                    frame = (frame+1)%9;
                    break;
                case 4:
                    frame =(frame+1)%8;
                    break;
                case 5:
                    frame = (frame+1)%12;
                    break;
            }
            try {
                Thread.sleep(80);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void bucle(){
        Thread thread = new Thread(()-> {
            while (true) {
                shoot();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread.start();
    }
}
