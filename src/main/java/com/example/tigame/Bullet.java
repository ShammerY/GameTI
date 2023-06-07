package com.example.tigame;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Bullet extends Drawing{
    private Vector direction;
    private boolean isEnemyBullet;
    private Color color;
    public Bullet(Vector pos, Vector direction,boolean isEnemyBullet ){
        this.isEnemyBullet = isEnemyBullet;
        this.direction = direction;
        this.pos = pos;
        if(!isEnemyBullet){
            this.width = 10;
            this.heigh = 10;
            this.color = Color.BLACK;
        }else{
            this.width = 20;
            this.heigh = 20;
            this.color = Color.LIGHTSTEELBLUE;
        }
    }
    public boolean isEnemyBullet(){return this.isEnemyBullet;}
    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(this.color);
        gc.fillOval(pos.getX(),pos.getY(),this.width,this.heigh);

        pos.setX(pos.getX()+direction.getX());
        pos.setY(pos.getY()+direction.getY());

    }
}
