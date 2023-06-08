package com.example.tigame;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GameController implements Initializable, Runnable {
    @FXML
    public Label gameOverLB;
    @FXML
    public Label returnBT;
    @FXML
    public Rectangle bgSquare;
    @FXML
    public Label ammoLB;
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
    private GameUI gameUI;
    private Timer timer;
    private boolean avatarFacing = true;
    private int magazine;
    private String soundfondo;
    private String soundShoot;
    private String soundReload;
    private String soundFinalGame;
    private  String soundEnemy;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        gc = canvas.getGraphicsContext2D();
        canvas.setFocusTraversable(true);
        canvas.setOnKeyPressed(this::onKeyPressed);
        canvas.setOnKeyReleased(this::onKeyReleased);
        canvas.setOnMousePressed(this::onMousePressed);
        canvas.setOnMouseMoved(this::onMouseMoved);
        setGameOverMenu();
        setMaps();
        setEnemyMovement();
        gameUI = new GameUI();
        //time = 0;
        magazine = 20;
        lastSec = 0;
        setAvatar();
        timer = new Timer();
        new Thread(timer).start();
        //new Thread(this).start();
        currentMap = 0;
        draw();
    }
    private void setAvatar(){
        int id = CharacterSelection.getInstance().getCharacterID();
        if(id!=1 && id!=2 && id!=3){
            id=1;
        }
        avatar = new Avatar(id);
        new Thread(avatar).start();
    }
    private void setGameOverMenu(){
        gameOverLB.setTextFill(Color.RED);
        returnBT.setTextFill(Color.PURPLE);
        gameOverLB.setText("");
        returnBT.setText("");
        gameOverLB.setDisable(true);
        returnBT.setDisable(true);
        bgSquare.setDisable(true);
        bgSquare.setVisible(false);
    }
    private void endGame(){
        isAlive = !isAlive;
        if(isAlive && avatar.getDurability()>0){
            draw();
        }else if(avatar.getDurability()<=0){
            gameOverLB.setDisable(false);
            returnBT.setDisable(false);
            bgSquare.setDisable(false);
            bgSquare.setVisible(true);
            gameOverLB.setText("GAME OVER");
            returnBT.setText("Return");
            timer.stop();
            /*
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("YOU LOOSE!");
            alert.setContentText("LOST ALL 3 LIVES");
            alert.showAndWait();
             */
        }
    }
    private void setMaps() {
        maps = new ArrayList<>();
        //________________Map 1______________________
        int[] connections0 = {-1,1,-1,-1};
        maps.add(new Map(0, new Boundaries().getMap1Boundaries(), connections0));
        //________________Map 2______________________
        int[] connections1 = {0,3,2,5};
        maps.add(new Map(1, new Boundaries().getMap2Boundaries(),connections1));
        //________________Map 3______________________
        int[] connections2 = {-1,-1,-1,1};
        maps.add(new Map(2, new Boundaries().getMap3Boundaries(),connections2));
        //________________Map 4______________________
        int[] connections3 = {1,-1,-1,4};
        maps.add(new Map(3, new Boundaries().getMap4Boundaries(),connections3));
        //________________Map 5______________________
        int[] connections4 = {5,-1,3,-1};
        maps.add(new Map(4, new Boundaries().getMap5Boundaries(),connections4));
        //________________Map 6______________________
        int[] connections5 = {-2,4,1,-1};
        maps.add(new Map(5, new Boundaries().getMap6Boundaries(),connections5));
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
            ammoLB.setTextFill(Color.CRIMSON);
            ammoLB.setText("R");
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
        double diffX = e.getX()+12 - avatarPosX;
        double diffY = e.getY()+12 - avatarPosY;

        Vector diff = new Vector(diffX,diffY);
        diff.normalize();
        diff.setMag(4);

        maps.get(currentMap).getBullets().add(
                new Bullet(new Vector(avatarPosX, avatarPosY), diff,false)
        );
        magazine--;
        ammoLB.setText(""+magazine);

    }
    public void draw(){
        Thread thread = new Thread(()->{
            while(isAlive){
                Map map = maps.get(currentMap);
                Platform.runLater(()->{//Runnable
                    String url = "file:"+GameApplication.class.getResource("backgrounds/bgp.png").getPath();
                    Image image= new Image(url);
                    gc.drawImage(image,0,0,canvas.getWidth(),canvas.getHeight());
                    //gc.setFill(Color.GRAY);
                    //gc.fillRect(0,0,canvas.getWidth(),canvas.getHeight());
                    //setGameUI();
                    avatar.draw(gc);
                    avatar.setRunning(wIsPressed || aIsPressed || sIsPressed || dIsPressed);

                    if(avatar.getDurability()<=0){
                        endGame();
                    }
                    //Pintar Obstaculos
                    for(int i=0;i<map.getObstacles().size();i++){
                        Obstacle o = map.getObstacles().get(i);
                        o.draw(gc);
                        AvatarObstacleCollition(o);
                        //Colision Enemigo con Obstaculo
                        for(int j=0;j<map.getEnemies().size();j++){
                            Enemy e = map.getEnemies().get(j);
                            enemyObstacleCollition(o,e);

                        }
                    }
                    drawGameUI();
                    //Pintar Balas

                    for(int i=0 ; i<map.getBullets().size() ; i++){
                        Bullet b = map.getBullets().get(i);
                        b.draw(gc);
                        if(isOutside(b.pos.getX(),b.pos.getY())){
                            map.getBullets().remove(i);
                        }else{

                            try{
                                //Colision de Balas enemigas con jugador
                                bulletAvatarCollition(b);

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
                                //Colision Balas con Enemigos
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
                            }catch(IndexOutOfBoundsException ex){}
                            //Colision de Balas con Enemigos
                        }
                    }
                    //Pintar Enemigos
                    for( int i=0;i<map.getEnemies().size();i++){
                        Enemy e = map.getEnemies().get(i);
                        e.draw(gc);
                        //enemyBounceMovement(e);
                        enemyMovement(e);
                        AvatarEnemyCollition(e);
                    }
                });
                playerMovement();
                //calculateTime();
                AvatarCollideWithMapBoundary();
                lastSec = timer.getInterval();

                try{
                    Thread.sleep(16);
                }catch(InterruptedException ex){
                    ex.printStackTrace();
                }
            }
        });
        thread.start();
    }

    private void bulletAvatarCollition(Bullet bullet) {
        if(bullet.isEnemyBullet()){
            Vector aPos = avatar.pos;
            Vector ePos = bullet.pos;
            double limX = 0;
            double limY = aPos.getY() - ePos.getY();
            if(avatarFacing){
                limX = aPos.getX() - ePos.getX();
            }else{
                limX = (aPos.getX()-avatar.width) - ePos.getX();
            }
            if((limX > (-((double)avatar.width)) && limX < ((double)bullet.width)) && limY> (-((double)avatar.heigh)) && limY < ((double)bullet.heigh)){
                avatar.pos.setX(canvas.getWidth()/2);
                avatar.pos.setY(canvas.getHeight()/2);
                avatarDamaged();
            }
        }
    }

    private void enemyObstacleCollition(Obstacle obstacle,Enemy enemy) {
        Vector ePos = enemy.pos;
        Vector oPos = obstacle.pos;
        double limX = 0;
        double limY = ePos.getY() - oPos.getY();
        if(avatarFacing){
            limX = ePos.getX() - oPos.getX();
        }else{
            limX = (ePos.getX()-50) - oPos.getX();
        }
        if((limX > (-enemy.width) && limX < obstacle.width) && limY> (-enemy.heigh) && limY < (obstacle.heigh)){
            double difSup = Math.abs(limY+avatar.heigh);
            double difInf = Math.abs(limY-obstacle.heigh);
            double difRight = Math.abs(limX-obstacle.width);
            double difLeft = Math.abs(limX+avatar.width);

            if(difSup < 6){
                ePos.setY(ePos.getY()-5);
            }
            if(difInf < 6){
                ePos.setY(ePos.getY()+5);
            }
            if(difLeft < 6){
                ePos.setX(ePos.getX()-5);
            }
            if(difRight < 6){
                ePos.setX(ePos.getX()+5);
            }

        }
    }
    private void enemyMovement(Enemy e) {
        switch(e.getId()){
            case 1:
                if(timer.getInterval()==1){
                    e.setRandomDirection();
                }else{
                    e.pos.setX(e.pos.getX()+e.getDirection().getX());
                    e.pos.setY(e.pos.getY()+e.getDirection().getY());
                }
                mapBoundaries(e);
                break;
            case 2:
                break;
            case 3:

                if(timer.getInterval()==3 && e.shot()){
                    double avatarPosX = 0;
                    double avatarPosY = 0;
                    if(avatarFacing){
                        avatarPosX = avatar.pos.getX()+25;
                        avatarPosY = avatar.pos.getY()+25;
                    }else{
                        avatarPosX = avatar.pos.getX()-25;
                        avatarPosY = avatar.pos.getY()+25;
                    }
                    //double diffX = e.getX() - avatarPosX;
                    //double diffY = e.getY() - avatarPosY;
                    double diffX = avatarPosX - (e.pos.getX()+((double)e.width/2));
                    double diffY = avatarPosY - (e.pos.getY()+((double)e.heigh/2));
                    Vector diff = new Vector(diffX,diffY);
                    diff.normalize();
                    diff.setMag(2);
                    maps.get(currentMap).getBullets().add(
                            new Bullet(new Vector(e.pos.getX()+((double)e.width/2), e.pos.getY()+((double)e.heigh/2)), diff,true)
                    );
                    e.setShot(false);
                }
                break;
            case 4:
                double avatarPosX = 0;
                double avatarPosY = 0;
                if(avatarFacing){
                    avatarPosX = avatar.pos.getX()+25;
                    avatarPosY = avatar.pos.getY()+25;
                }else{
                    avatarPosX = avatar.pos.getX()-25;
                    avatarPosY = avatar.pos.getY()+25;
                }
                double diffX = avatarPosX - (e.pos.getX()+((double)e.width/2));
                double diffY = avatarPosY - (e.pos.getY()+((double)e.heigh/2));
                Vector v = e.getDirection();
                v.setX(diffX);
                v.setY(diffY);
                v.normalize();
                v.setMag(1);
                e.pos.setX(e.pos.getX()+v.getX());
                e.pos.setY(e.pos.getY()+v.getY());
                break;
        }

    }
    private void mapBoundaries(Enemy e) {
        if(e.pos.getX()<=0){
            e.pos.setX(10);
        }
        if(e.pos.getX()>= canvas.getWidth()-e.width){
            e.pos.setX(canvas.getWidth()-e.width-5);
        }
        if(e.pos.getY()<=0){
            e.pos.setY(5);
        }
        if(avatar.pos.getY()>canvas.getHeight()-e.heigh){
            e.pos.setY(canvas.getHeight()-e.heigh-5);
        }
    }
    private void drawGameUI() {
        for(int i=0; i< avatar.getDurability();i++){
            Image image = gameUI.getHearts().get(i);
            gc.drawImage(image,i*(800/16),0,50,50);
        }
        Image ammoImage = gameUI.getAmmoUI();
        gc.drawImage(ammoImage,(800/16)*14,(600/12)*10,100,100);

    }
    private void AvatarCollideWithMapBoundary() {
        double aposX = 0;
        if(avatar.isFacingRight()){
            aposX = avatar.pos.getX();
        }else{
            aposX = avatar.pos.getX()-avatar.width;
        }
        double difSup = Math.abs(0-avatar.pos.getY());
        double difInf = Math.abs(canvas.getHeight()-(avatar.pos.getY()+avatar.heigh));
        double difRight = Math.abs(canvas.getWidth() - (aposX+avatar.width));
        double difLeft = Math.abs(0 - aposX);
        if(difSup<5){
            avatar.pos.setY(canvas.getHeight()-avatar.heigh-10);
            maps.get(currentMap).clearBullets();
            timer.setInterval(0);
            currentMap = maps.get(currentMap).getConnections()[0];
        } else if(difInf<5){
            avatar.pos.setY(10);
            maps.get(currentMap).clearBullets();
            timer.setInterval(0);
            currentMap = maps.get(currentMap).getConnections()[1];
        } else if(difRight<5){
            avatar.pos.setX(25+avatar.width);
            //avatar.setIsFacingRight(true);
            timer.setInterval(0);
            maps.get(currentMap).clearBullets();
            currentMap = maps.get(currentMap).getConnections()[2];
        } else if(difLeft<5){
            avatar.pos.setX(canvas.getWidth()-(avatar.width+25));
            //avatar.setIsFacingRight(false);
            timer.setInterval(0);
            maps.get(currentMap).clearBullets();
            currentMap = maps.get(currentMap).getConnections()[3];
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
        if(bullet.isEnemyBullet()){
            return false;
        }
        Vector bPos = bullet.pos;
        Vector ePos = enemy.pos;
        double limX = bPos.getX() - ePos.getX();
        double limY = bPos.getY() - ePos.getY();
        if((limX>-bullet.width && limX<enemy.width) && limY>-bullet.heigh && limY<enemy.heigh){
            return true;
        }
        return false;
    }
    private void enemyBounceMovement(Enemy enemy){
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
        if(enemy.pos.getY()<=0){
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
            limX = (aPos.getX()-avatar.width) - oPos.getX();
        }
        if((limX > (-avatar.width) && limX < obstacle.width) && limY> (-avatar.heigh) && limY < (obstacle.heigh)){
            double difSup = Math.abs(limY+avatar.heigh);
            double difInf = Math.abs(limY-obstacle.heigh);
            double difRight = Math.abs(limX-obstacle.width);
            double difLeft = Math.abs(limX+avatar.width);

             if(difSup < 9){
                aPos.setY(aPos.getY()-3);
            }
            if(difInf < 9){
                aPos.setY(aPos.getY()+3);
            }
            if(difLeft < 9){
                aPos.setX(aPos.getX()-3);
            }
            if(difRight < 9){
                aPos.setX(aPos.getX()+3);
            }

        }
    }
    private void AvatarEnemyCollition(Enemy enemy){
        Vector aPos = avatar.pos;
        Vector ePos = enemy.pos;
        double limX = 0;
        double limY = aPos.getY() - ePos.getY();
        if(avatarFacing){
            limX = aPos.getX() - ePos.getX();
        }else{
            limX = (aPos.getX()-avatar.width) - ePos.getX();
        }
        if((limX > (-((double)avatar.width/2)) && limX < ((double)enemy.width/2)) && limY> (-((double)avatar.heigh/2)) && limY < ((double)enemy.heigh/2)){
            double difSup = Math.abs(limY+avatar.heigh);
            double difInf = Math.abs(limY-enemy.heigh);
            double difRight = Math.abs(limX-enemy.width);
            double difLeft = Math.abs(limX+avatar.width);
            avatar.pos.setX(canvas.getWidth()/2);
            avatar.pos.setY(canvas.getHeight()/2);
            avatarDamaged();
        }

    }
    private void avatarDamaged(){
        avatar.setDurability(avatar.getDurability()-1);
        if(avatar.getDurability()>0){
            maps.get(currentMap).clearBullets();
            currentMap = 0;
            avatar.pos.setX(canvas.getWidth()/2-((double)avatar.width/2));
            avatar.pos.setY(canvas.getHeight()/2-((double)avatar.heigh/2));
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
            endGame();
        }
        if(event.getCode().equals(KeyCode.R)){
            magazine = 20;
            ammoLB.setTextFill(Color.LIGHTSLATEGRAY);
            ammoLB.setText(""+magazine);
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

    }
    @FXML
    public void returnToMenu(MouseEvent mouseEvent) {
        GameApplication.openWindow("menuWindow.fxml");
        Stage stage = (Stage) returnBT.getScene().getWindow();
        stage.close();
    }
    @FXML
    public void returnMouseEnter(MouseEvent mouseEvent) {
        returnBT.setTextFill(Color.WHITE);
    }
    @FXML
    public void returnMouseExit(MouseEvent mouseEvent) {
        returnBT.setTextFill(Color.PURPLE);
    }
}