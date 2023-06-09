package com.example.tigame;

public class Timer implements Runnable{
    private long startTime;
    private long currentTime;
    private long interval;
    private boolean isRunning;
    public Timer(){
        isRunning = true;
        this.startTime = System.currentTimeMillis();
        this.currentTime = 0;
        interval = 0;
    }
    @Override
    public void run() {
        while (isRunning) {
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
    public void setInterval(long n){
        this.interval = n;
    }
    public void stop(){
        isRunning = false;
    }
    public void start(){
        isRunning = true;
    }
    public long getCurrentTime(){return currentTime;}
    public long getInterval(){return this.interval;}
}
