package com.example.administrator.sideprojectacademy;
import android.util.Log;
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
    private GameObject[] gameObjects;

    public GameThread (SurfaceHolder surfaceHolder, Context context, GameEngine gameEngine, GameObject[] gameObjects){
        this.mSurfaceHolder = surfaceHolder;
        this.context = context;
        this.gameEngine = gameEngine;
        this.gameObjects = gameObjects;
    }

    @Override
    public void run(){
        Log.i("GameThread", "Game Thread run()");
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
                            //c.drawColor(Color.WHITE);
                            Paint p2 = new Paint();
                            p2.setStyle(Paint.Style.FILL);
                            p2.setColor(Color.WHITE);
                            p2.setAlpha(125);
                            //c.drawRect(100 ,100, 500, 500, p2);
                            //p2 = null;
                            Log.i("GameThread", "innan update");
                            gameEngine.updateInput();
                            gameEngine.updatePlayerPhysics();
                            gameEngine.updateAIPhysics(gameObjects);
                            gameEngine.updatePhysicsCheck();
                            gameEngine.updateUI();
                            gameEngine.updateSound();
                            gameEngine.finalizeLoop(gameObjects);
                            Log.i("GameThread", "Innan render");
                            for(GameObject go : gameObjects){
                                c.drawRect(go.x, go.y, go.width,go.height, p2);
                            }
                            p2 = null;
                            Log.i("GameThread", "Spelmotor uppdaterad i spelloopen");
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
                    Log.i("GameThread", "Running tilldelas false: " + e.getMessage());
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
