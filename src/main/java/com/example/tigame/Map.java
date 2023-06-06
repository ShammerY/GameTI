package com.example.tigame;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Map {
    private int id;
    private Image image;
    private ArrayList<Bullet> bullets;
    private ArrayList<Enemy> enemies;
    private ArrayList<Obstacle> obstacles;
    private Color color;
    public Map(int id, Image image, int[][] boundaries){
        obstacles = new ArrayList<>();
        enemies = new ArrayList<>();
        this.image = image;
        this.id = id;
        bullets = new ArrayList<>();
        setObstacles(boundaries);
    }
    public void setObstacles(int[][] boundaries){
        for(int i=0; i<12;i++){
            for(int j=0;j<16;j++){
                if (boundaries[i][j] ==1){
                    double x = (800/16)*j;
                    double y = (600/12)*i;
                    obstacles.add(new Obstacle(new Vector(x,y)));
                }
            }
        }
    }
    public void setEnemies(ArrayList<Enemy> enem){this.enemies = enem;}
    public ArrayList<Enemy> getEnemies(){return this.enemies;}
    public ArrayList<Obstacle> getObstacles(){return obstacles;}
    public void setObstacles(ArrayList<Obstacle> obs){this.obstacles = obs;}

    public int getId() {
        return id;
    }

    public Image getImage() {
        return this.image;
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public void setBullets(ArrayList<Bullet> bullets) {
        this.bullets = bullets;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
