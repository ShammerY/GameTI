package com.example.tigame;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GameController implements Initializable, Runnable {
    @FXML
    public Label timeLB;
    @FXML
    private Canvas canvas;
    private boolean isAlive = true;
    private GraphicsContext gc;
    private Avatar avatar;
    private boolean wIsPressed = false;
    private boolean aIsPressed = false;
    private boolean sIsPressed = false;
    private boolean dIsPressed = false;
    private int currentMap;
    private ArrayList<Map> maps;
    private Timer timer;
    private long time;
    private boolean avatarFacing = true;
    private int magazine;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        gc = canvas.getGraphicsContext2D();
        canvas.setFocusTraversable(true);

        canvas.setOnKeyPressed(this::onKeyPressed);
        canvas.setOnKeyReleased(this::onKeyReleased);
        canvas.setOnMousePressed(this::onMousePressed);
        canvas.setOnMouseMoved(this::onMouseMoved);
        setMaps();
        setEnemyMovement();
        time = 0;
        avatar = new Avatar();
        magazine = 20;
        new Thread(avatar).start();
        //timer = new Timer();
        //new Thread(timer).start();
        //new Thread(this).start();
        currentMap = 0;
        draw();
    }
    private void setMaps(){
        maps = new ArrayList<>();
        //________________Map 1______________________
        String uri = "file:"+GameApplication.class.getResource("forestBG/game_background_1.png").getPath();
        maps.add(new Map(0,new Image(uri),new Boundaries().getMap1Boundaries()));
        //________________Map 2______________________
        uri = "file:"+GameApplication.class.getResource("forestBG/game_background_2.png").getPath();
        maps.add(new Map(1,new Image(uri),new Boundaries().getMap2Boundaries()));
        maps.get(1).getEnemies().add(new Enemy(new Vector(100,230)));
        maps.get(1).getEnemies().add(new Enemy(new Vector(350,350)));
        //maps.get(1).getObstacles().add(new Obstacle(new Vector(100,300)));
        //maps.get(1).getObstacles().add(new Obstacle(new Vector(300,150)));
        //________________Map 3______________________
        uri = "file:"+GameApplication.class.getResource("forestBG/game_background_3.png").getPath();
        maps.add(new Map(2,new Image(uri),new Boundaries().getMap3Boundaries()));
        maps.get(2).getEnemies().add(new Enemy(new Vector(350,350)));
        maps.get(2).getEnemies().add(new Enemy( new Vector(300,300)));
        //________________Map 4______________________
        uri = "file:"+GameApplication.class.getResource("forestBG/game_background_4.png").getPath();
        maps.add(new Map(3,new Image(uri),new Boundaries().getMap4Boundaries()));
        //maps.get(3).getObstacles().add(new Obstacle(new Vector(400,400)));
        maps.get(3).getEnemies().add(new Enemy(new Vector(200,400)));
    }
    private void setEnemyMovement(){
        for(Map map:maps){
            for(Enemy enemy: map.getEnemies()){
                new Thread(enemy).start();
            }
        }
    }
    private void onMouseMoved(MouseEvent mouseEvent) {
        double mouseX = mouseEvent.getX();
        double avatarX = avatar.pos.getX();
        if(mouseX>avatarX){
            avatar.setIsFacingRight(true);
            if(avatarFacing==false && avatar.isFacingRight()==true){
                avatar.pos.setX(avatar.pos.getX()-50);
                avatarFacing = avatar.isFacingRight();
            }
        }else{
            avatar.setIsFacingRight(false);
            if(avatarFacing!=avatar.isFacingRight()){
                avatar.pos.setX(avatar.pos.getX()+50);
                avatarFacing = avatar.isFacingRight();
            }
        }
    }
    public void setFacing(MouseEvent mouseEvent){
        double mouseX = mouseEvent.getX();
        double avatarX = avatar.pos.getX();
        if(mouseX>avatarX){
            avatar.setIsFacingRight(true);
            if(avatarFacing==false && avatar.isFacingRight()==true){
                avatar.pos.setX(avatar.pos.getX()-50);
                avatarFacing = avatar.isFacingRight();
            }
        }else{
            avatar.setIsFacingRight(false);
            if(avatarFacing!=avatar.isFacingRight()){
                avatar.pos.setX(avatar.pos.getX()+50);
                avatarFacing = avatar.isFacingRight();
            }
        }
    }

    public void onMousePressed(MouseEvent e){
        //setFacing(e);
        if(magazine<=0){
            return;
        }
        double avatarPosX = 0;
        double avatarPosY = 0;
        if(avatarFacing){
            avatarPosX = avatar.pos.getX()+25;
            avatarPosY = avatar.pos.getY()+25;
        }else{
            avatarPosX = avatar.pos.getX()-25;
            avatarPosY = avatar.pos.getY()+25;
        }
        double diffX = e.getX() - avatarPosX;
        double diffY = e.getY() - avatarPosY;

        Vector diff = new Vector(diffX,diffY);
        diff.normalize();
        diff.setMag(4);

        maps.get(currentMap).getBullets().add(
                new Bullet(new Vector(avatarPosX, avatarPosY), diff)
        );
        magazine--;
    }
    public void draw(){
        Thread thread = new Thread(()->{
            while(isAlive){
                Map map = maps.get(currentMap);
                Platform.runLater(()->{//Runnable
                    gc.drawImage(map.getImage(),0,0,canvas.getWidth(),canvas.getHeight());
                    //gc.setFill(Color.GRAY);
                    //gc.fillRect(0,0,canvas.getWidth(),canvas.getHeight());
                    avatar.draw(gc);
                    avatar.setRunning(wIsPressed || aIsPressed || sIsPressed || dIsPressed);

                    //Pintar Obstaculos
                    for(Obstacle o:map.getObstacles()){
                        o.draw(gc);
                        AvatarObstacleCollition(o);
                    }
                    //Pintar Balas

                    for(int i=0 ; i<map.getBullets().size() ; i++){
                        Bullet b = map.getBullets().get(i);
                        b.draw(gc);
                        if(isOutside(b.pos.getX(),b.pos.getY())){
                            map.getBullets().remove(i);
                        }else{
                            //Colision de balas con obstaculos
                            for(int j=0;j<map.getObstacles().size();j++){
                                Obstacle o = map.getObstacles().get(j);
                                if(bulletObstacleCollition(b,o)){
                                    o.setDurability(o.getDurability()-1);
                                    map.getBullets().remove(i);
                                    if(o.getDurability()<=0){
                                        map.getObstacles().remove(j);
                                    }
                                }
                            }
                            //Colision de Balas con Enemigos
                            for(int j=0;j<map.getEnemies().size();j++){
                                Enemy e = map.getEnemies().get(j);
                                if(bulletEnemyCollition(b,e)){
                                    e.setDurability(e.getDurability()-1);
                                    map.getBullets().remove(i);
                                    if(e.getDurability()<=0){
                                        map.getEnemies().remove(j);
                                    }
                                }
                            }

                        }
                    }

                    //Pintar Enemigos
                    for( int i=0;i<map.getEnemies().size();i++){
                        Enemy e = map.getEnemies().get(i);
                        e.draw(gc);
                        enemyMovement(e);
                    }
                });
                playerMovement();
                mapBoundaries();
                //calculateTime();
                try{
                    Thread.sleep(16);
                }catch(InterruptedException ex){
                    ex.printStackTrace();
                    System.out.println("INTERRUPTED EXCEPTION");
                }
            }
        });
        thread.start();
    }

    private void mapBoundaries() {
        double limX = 0;
        if(avatarFacing){
            limX = avatar.pos.getX();
        }else{
            limX = avatar.pos.getX()-50;
        }

        if(limX<0){
            avatar.pos.setX(canvas.getWidth()-50);
            currentMap = ((currentMap+1)%4);
        }
        if(limX > canvas.getWidth()-20){
            avatar.pos.setX(50);
            if(currentMap==0){
                currentMap=3;
            }else{
                currentMap = ((currentMap-1)%4);
            }


        }
        if(avatar.pos.getY()<150){
            avatar.pos.setY(150);
        }
        if(avatar.pos.getY()>canvas.getHeight()-50){
            avatar.pos.setY(canvas.getHeight()-50);
        }
    }

    private void playerMovement() {
        if(wIsPressed){
            avatar.pos.setY(avatar.pos.getY()-3);
        }
        if(sIsPressed){
            avatar.pos.setY(avatar.pos.getY()+3);
        }
        if(aIsPressed){
            avatar.pos.setX(avatar.pos.getX()-3);
        }
        if(dIsPressed){
            avatar.pos.setX(avatar.pos.getX()+3);
        }
    }

    private boolean bulletEnemyCollition(Bullet bullet, Enemy enemy) {
        Vector bPos = bullet.pos;
        Vector ePos = enemy.pos;
        double limX = bPos.getX() - ePos.getX();
        double limY = bPos.getY() - ePos.getY();
        if((limX>-bullet.width && limX<enemy.width) && limY>-bullet.heigh && limY<enemy.heigh){
            return true;
        }
        return false;
    }
    private void enemyMovement(Enemy enemy){
        if(enemy.isMovingUp()){
            enemy.pos.setY(enemy.pos.getY()-2);
        }else{
            enemy.pos.setY(enemy.pos.getY()+2);
        }
        if(enemy.isMovingRight()){
            enemy.pos.setX(enemy.pos.getX()+2);
        }else{
            enemy.pos.setX(enemy.pos.getX()-2);
        }
        if(enemy.pos.getY()<=150){
            enemy.setMovingUp(false);
        }
        if(enemy.pos.getY()+70>=canvas.getHeight()){
            enemy.setMovingUp(true);
        }
        if(enemy.pos.getX()+50>=canvas.getWidth()){
            enemy.setMovingRight(false);
        }
        if(enemy.pos.getX()<=0){
            enemy.setMovingRight(true);
        }

    }
    private void AvatarObstacleCollition(Obstacle obstacle){
        Vector aPos = avatar.pos;
        Vector oPos = obstacle.pos;
        double limX = 0;
        double limY = aPos.getY() - oPos.getY();
        if(avatarFacing){
            limX = aPos.getX() - oPos.getX();
        }else{
            limX = (aPos.getX()-50) - oPos.getX();
            //System.out.println("limX = "+limX+" :: limY="+limY);
        }
        if((limX > (-avatar.width) && limX < obstacle.width) && limY> (-avatar.heigh) && limY < (obstacle.heigh)){
            double difSup = Math.abs(limY+avatar.heigh);
            double difInf = Math.abs(limY-obstacle.heigh);
            double difRight = Math.abs(limX-obstacle.width);
            double difLeft = Math.abs(limX+avatar.width);
            //System.out.println("sup="+difSup+" inf="+difInf+" right="+difRight+" left="+difLeft);
            if(difSup < 9){
                aPos.setY(aPos.getY()-5);
            }
            if(difInf < 9){
                aPos.setY(aPos.getY()+5);
            }
            if(difLeft < 9){
                aPos.setX(aPos.getX()-5);
            }
            if(difRight < 9){
                aPos.setX(aPos.getX()+5);
            }


        }

    }
    private boolean bulletObstacleCollition(Bullet bullet, Obstacle obstacle){
        Vector bPos = bullet.pos;
        Vector oPos = obstacle.pos;
        double limX = bPos.getX() - oPos.getX();
        double limY = bPos.getY() - oPos.getY();
        if((limX>-bullet.width && limX<obstacle.width) && limY>-bullet.heigh && limY<obstacle.heigh){
            return true;
        }
        return false;
    }
    private boolean isOutside(double x, double y){
        return (x<0 || x+20>canvas.getWidth() || y<0 || y+20> canvas.getHeight());
    }
    private void onKeyPressed(KeyEvent event){
        if(event.getCode().equals(KeyCode.W)){
            wIsPressed = true;
        }
        if(event.getCode().equals(KeyCode.A)){
            aIsPressed = true;
        }
        if(event.getCode().equals(KeyCode.S)){
            sIsPressed = true;
        }
        if(event.getCode().equals(KeyCode.D)){
            dIsPressed = true;
        }
        if(event.getCode().equals(KeyCode.P)){
            isAlive = !isAlive;
            if(isAlive){
                draw();
            }
        }
        if(event.getCode().equals(KeyCode.R)){
            magazine = 20;
        }
    }
    private void onKeyReleased(KeyEvent event){
        if(event.getCode().equals(KeyCode.W)){
            wIsPressed = false;
        }
        if(event.getCode().equals(KeyCode.A)){
            aIsPressed = false;
        }
        if(event.getCode().equals(KeyCode.S)){
            sIsPressed = false;
        }
        if(event.getCode().equals(KeyCode.D)){
            dIsPressed = false;
        }
    }

    @Override
    public void run() {
        while (true) {
            time++;
            timeLB.setText(""+time);
            System.out.println(time);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}