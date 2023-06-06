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
        boundaries[2][6] = 1;
        boundaries[9][2] = 1;
        boundaries[5][13] = 1;
        return boundaries;
    }
}
