package com.example.tigame;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Map {
    private int id;
    private ArrayList<Bullet> bullets;
    private ArrayList<Enemy> enemies;
    private ArrayList<Obstacle> obstacles;
    private int[] connections;
    private Color color;
    public Map(int id, int[][] boundaries,int[] connections){
        this.connections = connections;
        obstacles = new ArrayList<>();
        enemies = new ArrayList<>();
        this.id = id;
        bullets = new ArrayList<>();
        setObstacles(boundaries);
        setEnemies();
    }
    public void setObstacles(int[][] boundaries){
        Image borderImage = new Image("file:"+GameApplication.class.getResource("Border/Bricks"+(id+1)+".png").getPath());
        Image obs = new Image("file:"+GameApplication.class.getResource("obstacles/barrel1.png").getPath());
        for(int i=0; i<12;i++){
            for(int j=0;j<16;j++){
                if (boundaries[i][j] == 2){
                    obstacles.add(new Obstacle(new Vector((double)j*(800/16),(double)i*(600/12)),false,borderImage));
                } else if (boundaries[i][j]==1){
                    obstacles.add(new Obstacle(new Vector((double)j*(800/16),(double)i*(600/12)),true,borderImage));
                }
            }
        }
    }
    private void setEnemies(){
        int cant = (int)Math.floor(Math.random()*15);
        int x=0;
        int y=0;
        for(int i=0;i<cant;i++){
            x =(int)Math.floor(Math.random()*16);
            y =(int)Math.floor(Math.random()*12);
            enemies.add(new Enemy(new Vector(x*(800/16),y*(600/12))));
        }
    }

    public int[] getConnections() {
        return connections;
    }

    public void setEnemies(ArrayList<Enemy> enem){this.enemies = enem;}
    public ArrayList<Enemy> getEnemies(){return this.enemies;}
    public ArrayList<Obstacle> getObstacles(){return obstacles;}
    public void setObstacles(ArrayList<Obstacle> obs){this.obstacles = obs;}

    public int getId() {
        return id;
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
