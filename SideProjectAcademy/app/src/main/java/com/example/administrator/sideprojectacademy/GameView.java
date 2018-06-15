package com.example.administrator.sideprojectacademy;
import android.view.*;
import android.content.*;
import android.util.*;
import android.app.*;

import static android.support.constraint.Constraints.TAG;

public class GameView extends SurfaceView implements SurfaceHolder.Callback{
    private GameThread thread;
    private GameEngine gameEngine;
    private GameObject[] gameObjects = new GameObject[10];
    public GameView(Context context){//}, AttributeSet attrs){
        super(context);//, attrs);

        gameEngine = new GameEngine((Activity) context, gameObjects);

        try{

            SurfaceHolder holder = getHolder();
            holder.addCallback(this);
            gameEngine.initGame(this, holder);
            thread = new GameThread(holder, context, gameEngine, gameObjects);

            setFocusable(true);
            setFocusableInTouchMode(true);
            Log.i("GameView", "Hela Konstruktorn körd");

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
