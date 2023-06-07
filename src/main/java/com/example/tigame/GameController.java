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
        setGameOverMenu();
        setMaps();
        setEnemyMovement();
        gameUI = new GameUI();
        time = 0;
        magazine = 20;
        avatar = new Avatar();
        new Thread(avatar).start();
        //timer = new Timer();
        //new Thread(timer).start();
        //new Thread(this).start();
        currentMap = 0;
        draw();
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
    private void setGameUI(){
        String heart = "file:"+GameApplication.class.getResource("gameUI/Heart1.png").getPath();
        for(int i=0;i<avatar.getDurability();i++){
            gc.drawImage(new Image(heart),i*(800/16),0,50,50);
        }
        System.out.println("Sale del heart");
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
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("YOU LOOSE!");
            alert.setContentText("LOST ALL 3 LIVES");
            alert.showAndWait();
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
                    //gc.drawImage(map.getImage(),0,0,canvas.getWidth(),canvas.getHeight());
                    gc.setFill(Color.GRAY);
                    gc.fillRect(0,0,canvas.getWidth(),canvas.getHeight());
                    //setGameUI();
                    avatar.draw(gc);
                    avatar.setRunning(wIsPressed || aIsPressed || sIsPressed || dIsPressed);

                    if(avatar.getDurability()<=0){
                        endGame();
                    }
                    //Pintar Obstaculos
                    for(Obstacle o:map.getObstacles()){
                        o.draw(gc);
                        AvatarObstacleCollition(o);
                    }
                    for(int i=0; i< avatar.getDurability();i++){
                        Image image = gameUI.getHearts().get(i);
                        gc.drawImage(image,i*(800/16),0,50,50);
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
                        AvatarEnemyCollition(e);
                    }
                });
                playerMovement();
                //mapBoundaries();
                //calculateTime();
                AvatarCollideWithMapBoundary();

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
            currentMap = maps.get(currentMap).getConnections()[0];
        } else if(difInf<5){
            avatar.pos.setY(10);
            currentMap = maps.get(currentMap).getConnections()[1];
        } else if(difRight<5){
            avatar.pos.setX(25+avatar.width);
            //avatar.setIsFacingRight(true);
            currentMap = maps.get(currentMap).getConnections()[2];
        } else if(difLeft<5){
            avatar.pos.setX(canvas.getWidth()-(avatar.width+25));
            //avatar.setIsFacingRight(false);
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
            System.out.println(time);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
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