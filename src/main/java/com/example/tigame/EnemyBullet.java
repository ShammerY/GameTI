package com.example.tigame;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class EnemyBullet extends Drawing{
    private Vector direction;
    public EnemyBullet(Vector pos, Vector direction ){
        this.width = 10;
        this.heigh = 10;
        this.pos = pos;
        this.direction = direction;
    }
    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillOval(pos.getX(),pos.getY(),this.width,this.heigh);

        pos.setX(pos.getX()+direction.getX());
        pos.setY(pos.getY()+direction.getY());

    }
}
