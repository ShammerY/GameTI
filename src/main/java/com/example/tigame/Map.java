package com.example.tigame;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Map {
    private int id;
    private ArrayList<Bullet> bullets;
    private ArrayList<Enemy> enemies;
    private ArrayList<Weapon> weapons;
    private int[][] boundaries;
    private ArrayList<Obstacle> lockedBlocks;
    private ArrayList<Obstacle> obstacles;
    private int[] connections;
    private Color color;
    public Map(int id, int[][] boundaries,int[] connections){
        this.lockedBlocks = new ArrayList<>();
        this.boundaries = boundaries;
        weapons = new ArrayList<>();
        this.connections = connections;
        obstacles = new ArrayList<>();
        enemies = new ArrayList<>();
        this.id = id;
        bullets = new ArrayList<>();
        setObstacles();
        if(this.id!=0){
            setEnemies();
        }
        if (this.id==0 || this.id==2){
            setWeapons();
        }
    }
    private void setWeapons(){
        int cant = 2;
        int x=0;
        int y=0;
        int weaponId = 0;
        for(int i=0;i<cant;i++){
            weaponId = (int)Math.floor(Math.random()*3+1);
            x =(int)Math.floor(Math.random()*14+1);
            y =(int)Math.floor(Math.random()*10+1);
            if(boundaries[y][x]==0){
                if(!searchRepeatedWeapon(weaponId)){
                    weapons.add(new Weapon(new Vector(x*(800/16),y*(600/12)),weaponId));
                }else{
                    i--;
                }
            }else{
                i--;
            }
        }
    }

    private boolean searchRepeatedWeapon(int weaponId) {
        for(Weapon w:weapons){
            if(weaponId==w.id){
                return true;
            }
        }
        return false;
    }

    public ArrayList<Weapon> getWeapons() {
        return weapons;
    }

    public void setObstacles(){
        Image borderImage = new Image("file:"+GameApplication.class.getResource("Border/Bricks"+(id+1)+".png").getPath());
        Image lockedBlock = new Image("file:"+GameApplication.class.getResource("Border/BricksLocked.png").getPath());
        int cant = (int)Math.floor(Math.random()*5+2);
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
        for(int i=0;i<cant;i++){
            int x =(int)Math.floor(Math.random()*12+2);
            int y =(int)Math.floor(Math.random()*6+3);
            if(boundaries[y][x]==0){
                obstacles.add(new Obstacle(new Vector(x*(800/16),y*(600/12)),true,borderImage));
                boundaries[y][x] = 1;
            }else{
                i--;
            }
        }
    }
    private void setEnemies(){
        int cant = (int)Math.floor(Math.random()*6+5);
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
                boundaries[y][x] = 1;
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
