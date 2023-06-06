package com.example.tigame;

public class Timer implements Runnable{
    private long startTime;
    private long currentTime;
    public Timer(){
        this.startTime = System.currentTimeMillis();
        this.currentTime = 0;
    }
    @Override
    public void run() {
        while (true) {
            currentTime++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public long getCurrentTime(){return currentTime;}
}
