package com.example.tigame;

public class Boundaries {
    public Boundaries(){}
    /*
    MAP: 16 x 12 | (800px x 600px)
    1 = Obstacle;
    [ 1 ][ 1 ][ 1 ][ 1 ][ 1 ][ 1 ][ 0 ][ 0 ][ 0 ][ 0 ][ 1 ][ 1 ][ 1 ][ 1 ][ 1 ][ 1 ]
    [ 1 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 1 ]
    [ 1 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 1 ]
    [ 1 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 1 ]
    [ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ]
    [ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ]
    [ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ]
    [ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ]
    [ 1 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 1 ]
    [ 1 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 1 ]
    [ 1 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 0 ][ 1 ]
    [ 1 ][ 1 ][ 1 ][ 1 ][ 1 ][ 1 ][ 0 ][ 0 ][ 0 ][ 0 ][ 1 ][ 1 ][ 1 ][ 1 ][ 1 ][ 1 ]

     */
    public int[][] getMap1Boundaries(){
        int[][] boundaries = new int[12][16];
        //Bordes
        for(int i=0;i<16;i++){
            boundaries[0][i] = 2;
            boundaries[11][i] = 2;
        }
        for(int i=1;i<11;i++){
            boundaries[i][0] = 2;
            boundaries[i][15] = 2;
        }
        boundaries = infEntrance(boundaries);
        //Obstaculos
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
            boundaries[0][i] = 2;
            boundaries[11][i] = 2;
        }
        for(int i=1;i<11;i++){
            boundaries[i][0] = 2;
            boundaries[i][15] = 2;
        }
        boundaries = supEntrance(boundaries);
        boundaries = leftEntrance(boundaries);
        boundaries = rightEntrance(boundaries);
        boundaries = infEntrance(boundaries);
        return boundaries;
    }
    public int[][] getMap3Boundaries(){
        int[][] boundaries = new int[12][16];
        for(int i=0;i<16;i++){
            boundaries[0][i] = 2;
            boundaries[11][i] = 2;
        }
        for(int i=1;i<11;i++){
            boundaries[i][0] = 2;
            boundaries[i][15] = 2;
        }
        boundaries = leftEntrance(boundaries);

        return boundaries;
    }
    public int[][] getMap4Boundaries(){
        int[][] boundaries = new int[12][16];
        for(int i=0;i<16;i++){
            boundaries[0][i] = 2;
            boundaries[11][i] = 2;
        }
        for(int i=1;i<11;i++){
            boundaries[i][0] = 2;
            boundaries[i][15] = 2;
        }
        boundaries = supEntrance(boundaries);
        boundaries = leftEntrance(boundaries);
        boundaries[4][4] = 1;
        boundaries[7][7] = 1;
        boundaries[9][9] = 1;
        return boundaries;
    }
    public int[][] getMap5Boundaries(){
        int[][] boundaries = new int[12][16];
        for(int i=0;i<16;i++){
            boundaries[0][i] = 2;
            boundaries[11][i] = 2;
        }
        for(int i=1;i<11;i++){
            boundaries[i][0] = 2;
            boundaries[i][15] = 2;
        }
        boundaries = rightEntrance(boundaries);
        boundaries = supEntrance(boundaries);

        return boundaries;
    }
    public int[][] getMap6Boundaries(){
        int[][] boundaries = new int[12][16];
        for(int i=0;i<16;i++){
            boundaries[0][i] = 2;
            boundaries[11][i] = 2;
        }
        for(int i=1;i<11;i++){
            boundaries[i][0] = 2;
            boundaries[i][15] = 2;
        }
        boundaries = supEntrance(boundaries);
        boundaries = rightEntrance(boundaries);
        boundaries = infEntrance(boundaries);
        boundaries[4][4] = 1;
        boundaries[7][7] = 1;
        boundaries[9][9] = 1;
        return boundaries;
    }
    private int[][] supEntrance(int[][] boundaries){
        boundaries[0][6] = 0;
        boundaries[0][7] = 0;
        boundaries[0][8] = 0;
        boundaries[0][9] = 0;
        return boundaries;
    }
    private int[][] infEntrance(int[][] boundaries){
        boundaries[11][6] = 0;
        boundaries[11][7] = 0;
        boundaries[11][8] = 0;
        boundaries[11][9] = 0;
        return boundaries;
    }
    private int[][] rightEntrance(int[][] boundaries){
        boundaries[4][15] = 0;
        boundaries[5][15] = 0;
        boundaries[6][15] = 0;
        boundaries[7][15] = 0;
        return boundaries;
    }
    private int[][] leftEntrance(int[][] boundaries){
        boundaries[4][0] = 0;
        boundaries[5][0] = 0;
        boundaries[6][0] = 0;
        boundaries[7][0] = 0;
        return boundaries;
    }
}
