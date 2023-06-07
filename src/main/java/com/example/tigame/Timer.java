package com.example.tigame;

public class Timer implements Runnable{
    private long startTime;
    private long currentTime;
    private long interval;
    public Timer(){
        this.startTime = System.currentTimeMillis();
        this.currentTime = 0;
        interval = 1;
    }
    @Override
    public void run() {
        while (true) {
            currentTime++;
            if(interval==3){
                interval = 1;
            }else{
                interval++;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public long getCurrentTime(){return currentTime;}
    public long getInterval(){return this.interval;}
}
