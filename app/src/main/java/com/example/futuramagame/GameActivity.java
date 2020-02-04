package com.example.futuramagame;

import android.os.Bundle;

public class GameActivity extends MainMenu{

    public GameSurfaceView gameSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        gameSurfaceView = new GameSurfaceView(this);
        setContentView(gameSurfaceView);
    }





}
