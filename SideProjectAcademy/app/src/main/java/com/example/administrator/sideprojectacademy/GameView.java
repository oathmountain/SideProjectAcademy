package com.example.administrator.sideprojectacademy;
import android.view.*;
import android.content.*;
import android.util.*;
import android.app.*;

public class GameView extends SurfaceView implements SurfaceHolder.Callback{
    private GameThread thread;
    private GameEngine gameEngine;
    public GameView(Context context, AttributeSet attrs){
        super(context, attrs);
        gameEngine = new GameEngine((Activity) context);
        try{
            gameEngine.initGame(this);
            SurfaceHolder holder = getHolder();
            holder.addCallback(this);
            thread = new GameThread(holder, context, gameEngine);

            setFocusable(true);
            setFocusableInTouchMode(true);

        }catch(Exception e){

        }


    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){

    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        stop();
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder){
        start();
    }

    public void stop(){
        thread.setRunning(false);
        try{
            thread.join(500);
        }catch(Exception e){

        }
    }
    public void start(){
        thread.setRunning(true);
        thread.start();
    }
    public void pause(){
        thread.setPaused(true);
    }
    public void resume(){
        thread.setPaused(false);
    }
    public void restartLevel(){

    }
}
