package com.example.administrator.sideprojectacademy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import static android.support.constraint.Constraints.TAG;


public class MainActivity extends AppCompatActivity {

    private GameView mGameView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGameView = new GameView(this);
        // Android 4.1 and higher simple way to request fullscreen.
        mGameView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        setContentView(mGameView);
        Log.i("Main activity", "On create kört");
    }
}
