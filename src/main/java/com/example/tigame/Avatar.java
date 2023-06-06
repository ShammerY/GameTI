package com.example.tigame;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Avatar extends Drawing implements Runnable{
    private boolean isRunning = false;
    private boolean isFacingRight = true;
    private Image[] idle;
    private Image[] run;
    private int frame;
    public Avatar(){
        frame = 0;
        pos.setX(400);
        pos.setY(300);
        this.width = 50;
        this.heigh = 50;
        idle = new Image[10];
        for(int i=0;i<10;i++){
            String uri = "file:"+GameApplication.class.getResource("rainbowMan_idle/rainbowMan-idle"+(i+1)+".png").getPath();
            idle[i] = new Image(uri);
        }
        run = new Image[8];
        for(int i=0;i<8;i++){
            String uri = "file:"+GameApplication.class.getResource("rainbowMan_run/rainbowMan"+(i+1)+"_run.png").getPath();
            run[i] = new Image(uri);
        }

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
            //gc.drawImage(idle[frame],pos.getX(),pos.getY(),width,50);
            gc.drawImage(idle[frame], isFacingRight?pos.getX():pos.getX(), pos.getY(), isFacingRight?50:-50, 50);
        }else{
            try{
                //gc.drawImage(run[frame],pos.getX(),pos.getY(),width,50);
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
                frame = (frame + 1) % 8;
            }else{
                frame = (frame + 1) % 10;
            }

            try {
                Thread.sleep(80);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
