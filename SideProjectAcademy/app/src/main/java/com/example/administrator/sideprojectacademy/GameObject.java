package com.example.administrator.sideprojectacademy;

public class GameObject {
    int x, y, width, height, counter = 0;
    public GameObject(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    public void updateLocation(){
        switch(counter){
            case 0:
                x += 50;
                break;
            case 1:
                x += 50;
                break;
            case 2:
                y += 50;
                break;
            case 3:
                y+=50;
                break;
        }
        counter ++;
        if(counter == 4){
            counter = 0;
        }
    }
}
