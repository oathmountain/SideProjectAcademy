package com.example.administrator.sideprojectacademy;
import android.app.*;
import android.graphics.Canvas;
import android.view.SurfaceHolder;


public class GameEngine {
    Activity context;
    Canvas c;
    //GameObject[] gameObjects;
    public GameEngine(Activity context, GameObject[] gameObjects){
        this.context = context;
        //this.gameObjects = gameObjects;
        for(int i = 0; i <gameObjects.length; i++){
            gameObjects[i] = new GameObject(i*i*i, i*i*i, 5,5);
        }
    }


    public void initGame(GameView gameView, SurfaceHolder surfaceHolder){
        c = surfaceHolder.lockCanvas(null);
    }
    public void initLevel(int level){

    }
    public void updatePlayerPhysics(){

    }
    public void updateInput(){

    }
    public void updateAIPhysics(GameObject[] gameObjects){
        for(GameObject go : gameObjects){
            go.updateLocation();
        }
    }
    public void updateUI(){

    }
    public void updateSound(){

    }
    public void updatePhysicsCheck(){

    }
    public void finalizeLoop(GameObject[] gameObjects){
       /* for(GameObject go : gameObjects){
            c.drawRect(go.x, go.y, go.width,go.height, p2);
        }*/
    }
    public void resume(){

    }
    public void pause(){

    }

}
