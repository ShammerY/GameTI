package com.example.tigame;

import javafx.scene.canvas.GraphicsContext;

public abstract class Drawing {
    protected Vector pos = new Vector(0,0);
    protected int width;
    protected int heigh;
    public abstract void draw(GraphicsContext gc);
}
