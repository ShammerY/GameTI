package com.example.tigame;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Avatar extends Drawing implements Runnable{
    private boolean isRunning = false;
    private boolean isFacingRight = true;
    private Image[] idle;
    private Image[] run;
    private int durability;
    private int frame;
    public Avatar(){
        durability = 3;
        frame = 0;
        pos.setX(400);
        pos.setY(300);
        this.width = 50;
        this.heigh = 50;
        idle = new Image[5];
        for(int i=0;i<5;i++){
            String uri = "file:"+GameApplication.class.getResource("Black/Quieto/Gunner_Black_Idle_"+(i+1)+".png").getPath();
            idle[i] = new Image(uri);
        }
        run = new Image[6];
        for(int i=0;i<6;i++){
            String uri = "file:"+GameApplication.class.getResource("Black/Run/Gunner_Black_Run_"+(i+1)+".png").getPath();
            run[i] = new Image(uri);
        }

    }

    public int getDurability() {
        return durability;
    }
    public void setDurability(int durability) {
        this.durability = durability;
    }

    public void setRunning(boolean b){isRunning = b;}
    public void setFrame(int frame){this.frame = frame;}
    public void setIsFacingRight(boolean facing){isFacingRight = facing;}
    public boolean isFacingRight(){return this.isFacingRight;}
    @Override
    public void draw(GraphicsContext gc) {
        //gc.setFill(Color.AZURE);
        //gc.fillOval(pos.getX(),pos.getY(),50,50);

        if(!isRunning){
            try{
                gc.drawImage(idle[frame], isFacingRight?pos.getX():pos.getX(), pos.getY(), isFacingRight?50:-50, 50);

            }catch(IndexOutOfBoundsException ex){
                frame = 0;
            }
            gc.drawImage(idle[frame], isFacingRight?pos.getX():pos.getX(), pos.getY(), isFacingRight?50:-50, 50);
        }else{
            try{
                gc.drawImage(run[frame], isFacingRight?pos.getX():pos.getX(), pos.getY(), isFacingRight?50:-50, 50);
            }catch(IndexOutOfBoundsException ex){
                frame = 0;
            }
        }


    }

    @Override
    public void run() {
        while (true) {
            if(isRunning){
                frame = (frame + 1) % 6;
            }else{
                frame = (frame + 1) % 5;
            }

            try {
                Thread.sleep(80);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
