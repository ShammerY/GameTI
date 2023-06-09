package com.example.tigame;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Map {
    private int id;
    private ArrayList<Bullet> bullets;
    private ArrayList<Enemy> enemies;
    private int[][] boundaries;
    private ArrayList<Obstacle> lockedBlocks;
    private ArrayList<Obstacle> obstacles;
    private int[] connections;
    private Color color;
    public Map(int id, int[][] boundaries,int[] connections){
        this.lockedBlocks = new ArrayList<>();
        this.boundaries = boundaries;
        this.connections = connections;
        obstacles = new ArrayList<>();
        enemies = new ArrayList<>();
        this.id = id;
        bullets = new ArrayList<>();
        setObstacles();
        if(this.id!=0){
            setEnemies();
        }
    }
    public void setObstacles(){
        Image borderImage = new Image("file:"+GameApplication.class.getResource("Border/Bricks"+(id+1)+".png").getPath());
        Image lockedBlock = new Image("file:"+GameApplication.class.getResource("Border/BricksLocked.png").getPath());
        for(int i=0; i<12;i++){
            for(int j=0;j<16;j++){
                if (boundaries[i][j] == 2){
                    obstacles.add(new Obstacle(new Vector((double)j*(800/16),(double)i*(600/12)),false,borderImage));
                } else if (boundaries[i][j]==1){
                    obstacles.add(new Obstacle(new Vector((double)j*(800/16),(double)i*(600/12)),true,borderImage));
                } else if(boundaries[i][j] == 5){
                    lockedBlocks.add(new Obstacle(new Vector((double)j*(800/16),(double)i*(600/12)),false,lockedBlock));
                }
            }
        }
    }
    private void setEnemies(){
        int cant = (int)Math.floor(Math.random()*7+5);
        int x=0;
        int y=0;
        int enemyId = 0;
        for(int i=0;i<cant;i++){
            enemyId = (int)Math.floor(Math.random()*5+1);
            x =(int)Math.floor(Math.random()*14+1);
            y =(int)Math.floor(Math.random()*10+1);
            Vector inPos = new Vector(x*((double)800/16),y*((double)600/12));
            if(boundaries[y][x]==0){
                enemies.add(new Enemy(new Vector(inPos.getX(),inPos.getY()),enemyId,inPos));
            }else{
                i--;
            }
        }
    }

    public ArrayList<Obstacle> getLockedBlocks() {
        return lockedBlocks;
    }

    public void clearBullets(){
        while(bullets.size()>0){
            bullets.remove(bullets.get(0));
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


    public void resetEnemyPos() {
        for(Enemy e:enemies){
            e.pos.setX(e.getInitialPos().getX());
            e.pos.setY(e.getInitialPos().getY());
        }
    }
}
