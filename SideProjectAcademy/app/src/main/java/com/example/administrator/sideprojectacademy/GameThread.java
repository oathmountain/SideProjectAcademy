package com.example.administrator.sideprojectacademy;
import android.view.*;
import android.content.*;
import android.graphics.*;

public class GameThread extends Thread {
    private SurfaceHolder mSurfaceHolder;
    private Context context;
    private GameEngine gameEngine;
    private boolean running;
    private boolean paused;
    private int startingLevel;

    public GameThread (SurfaceHolder surfaceHolder, Context context, GameEngine gameEngine){
        this.mSurfaceHolder = surfaceHolder;
        this.context = context;
        this.gameEngine = gameEngine;
    }

    @Override
    public void run(){
        try{
            long startTime = 0;
            long endTime = 0;
            int limitFps = 60;
            int minLoopTime = 1000/limitFps;

            if(startingLevel <= 0){
                startingLevel = 1;
            }
            gameEngine.initLevel(startingLevel);

            boolean doPause = true;
            boolean doResume = false;
            while(running){
                startTime = System.currentTimeMillis();

                Canvas c = null;
                try{
                    if(!paused){
                        c = mSurfaceHolder.lockCanvas(null);
                        synchronized (mSurfaceHolder){
                            if(doResume){
                                doResume = false;
                                doPause = true;
                                gameEngine.resume();
                            }
                            gameEngine.updateInput();
                            gameEngine.updatePlayerPhysics();
                            gameEngine.updateAIPhysics();
                            gameEngine.updatePhysicsCheck();
                            gameEngine.updateUI();
                            gameEngine.updateSound();
                            gameEngine.finalizeLoop();
                        }
                    }else{
                        if(doPause){
                            doResume = true;
                            doPause = false;
                            gameEngine.pause();

                            c = mSurfaceHolder.lockCanvas(null);
                            synchronized (mSurfaceHolder){
                                Paint p = new Paint();
                                p.setStyle(Paint.Style.FILL);
                                p.setColor(Color.BLACK);
                                p.setAlpha(125);
                                c.drawRect(0,0, ScreenManager.screenWidthPx, ScreenManager.screenHeightPx, p);
                                p = null;
                            }
                        }
                    }
                }catch(Exception e){
                    running = false;

                }finally{
                    if(c != null){
                        mSurfaceHolder.unlockCanvasAndPost(c);
                    }
                }
                endTime = System.currentTimeMillis();

                try{
                    long sleepTime = minLoopTime - (endTime - startTime);
                    if(sleepTime > 0){
                        Thread.sleep(sleepTime);
                    }
                }catch(Exception e){

                }
            }
        }catch(Exception e){

        }
    }
    public boolean isRunning(){
        return running;
    }
    public void setStartingLevel(int startingLevel){
        this.startingLevel = startingLevel;
    }
    public void setRunning(boolean running){
        this.running = running;
    }
    public boolean isPaused(){
        return paused;
    }
    public void setPaused(boolean paused){
        this.paused = paused;
    }
}
