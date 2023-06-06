package com.example.tigame;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Bullet extends Drawing{
    private Vector direction;
    public Bullet(Vector pos, Vector direction ){
        this.pos = pos;
        this.direction = direction;
    }
    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillOval(pos.getX(),pos.getY(),20,20);

        pos.setX(pos.getX()+direction.getX());
        pos.setY(pos.getY()+direction.getY());

    }
}
