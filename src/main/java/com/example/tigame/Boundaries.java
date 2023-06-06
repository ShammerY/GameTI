package com.example.tigame;

public class Boundaries {
    public Boundaries(){}
    public int[][] getMap1Boundaries(){
        int[][] boundaries = new int[12][16];
        for(int i=0;i<16;i++){
            boundaries[0][i] = 0;
            boundaries[11][i] = 0;
        }
        for(int i=1;i<11;i++){
            boundaries[i][0] = 0;
            boundaries[i][15] = 0;
        }
        boundaries[4][6] = 1;
        boundaries[9][2] = 1;
        boundaries[5][13] = 1;
        return boundaries;
    }
    public int[][] getMap2Boundaries(){
        //Matriz que representa la posición de los obstaculos
        //Donde haya 1, hay un obstaculo
        int[][] boundaries = new int[12][16];
        for(int i=0;i<16;i++){
            boundaries[0][i] = 0;
            boundaries[11][i] = 0;
        }
        for(int i=1;i<11;i++){
            boundaries[i][0] = 0;
            boundaries[i][15] = 0;
        }
        boundaries[6][4] = 1;
        boundaries[7][6] = 1;
        boundaries[5][13] = 1;
        return boundaries;
    }
    public int[][] getMap3Boundaries(){
        int[][] boundaries = new int[12][16];
        for(int i=0;i<16;i++){
            boundaries[0][i] = 0;
            boundaries[11][i] = 0;
        }
        for(int i=1;i<11;i++){
            boundaries[i][0] = 0;
            boundaries[i][15] = 0;
        }
        boundaries[11][6] = 1;
        boundaries[5][2] = 1;
        boundaries[7][8] = 1;
        return boundaries;
    }
    public int[][] getMap4Boundaries(){
        int[][] boundaries = new int[12][16];
        for(int i=0;i<16;i++){
            boundaries[0][i] = 0;
            boundaries[11][i] = 0;
        }
        for(int i=1;i<11;i++){
            boundaries[i][0] = 0;
            boundaries[i][15] = 0;
        }
        boundaries[4][4] = 1;
        boundaries[7][7] = 1;
        boundaries[9][9] = 1;
        return boundaries;
    }
}
