package com.example.futuramagame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback{

    private MyAnimationThread animationThread = null;
    //fons
    int borde;
    private Bitmap fonsBitmap;
    //la bender
    private Bitmap benderBitmap;
    private Bitmap benderResized;
    private Bender bender;
    //el paquet
    private Bitmap paquetBitmap;
    private Bitmap paquetResized;
    private Paquet paquet;
    //la nau
    private NauExpress nauExpress;
    private Bitmap nauExpressBitmapDret;
    private Bitmap nauExpressResizeddret;
    private Bitmap nauExpressBitmapEsq;
    private Bitmap nauExpressResizedEsq;

    public GameSurfaceView(Context context){
        super(context);
        getHolder().addCallback(this);
        borde = 50;

        fonsBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.espacio);

        benderBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bender);
        benderResized = Bitmap.createScaledBitmap(benderBitmap, 300, 300, false);

        nauExpressBitmapDret = BitmapFactory.decodeResource(context.getResources(), R.drawable.naudret);
        nauExpressResizeddret = Bitmap.createScaledBitmap(nauExpressBitmapDret, 300, 200, false);

        nauExpressBitmapEsq = BitmapFactory.decodeResource(context.getResources(), R.drawable.nauesquerra);
        nauExpressResizedEsq = Bitmap.createScaledBitmap(nauExpressBitmapEsq, 300, 200, false);

        paquetBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.paquet);
        paquetResized = Bitmap.createScaledBitmap(paquetBitmap, 100, 100, false);

        paquet = new Paquet();

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if(animationThread != null){
            return;
        }
        bender = new Bender(getWidth()/2, getHeight()/2 + 650); //700 es per a quadrarlo baix de la pantalla
        nauExpress = new NauExpress(0, borde );

        animationThread = new MyAnimationThread(getHolder());
        animationThread.start();

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    private int punts = 0;
    private boolean caiguent = false;
    public void newDraw(Canvas canvas){

        Rect rectBackground = new Rect(0,0,getWidth(), getHeight());
        canvas.drawBitmap(fonsBitmap, null, rectBackground,null);
        canvas.drawBitmap(benderResized, bender.getX(), bender.getY(), null);

        if(movimentNau()){
            canvas.drawBitmap(nauExpressResizeddret, nauExpress.getX(), nauExpress.getY(), null);
        }else{
            canvas.drawBitmap(nauExpressResizedEsq, nauExpress.getX(), nauExpress.getY(), null);
        }
        //LA NAU DEIXA CAURE EL PAQUET
        int random = new Random().nextInt(100);
        int mov = new Random().nextInt(40);

        if(!caiguent) {
            if (random == 1) {
                paquet.setX(nauExpress.getX());
                paquet.setY(nauExpress.getY());

                caiguent = true;
            }
        }else{
            paquet.setY(paquet.getY() + mov);
            canvas.drawBitmap(paquetResized, paquet.getX(), paquet.getY(), null);

            if(paquet.getY() > getHeight()){
                caiguent = false;
            }
        }
        if(paquet.equals(bender)){
            punts += 10;

        }
        System.out.println(punts);



    }

    @Override
    public boolean onTouchEvent(MotionEvent ev){
        final int action = ev.getAction();

        switch(action) {
            case MotionEvent.ACTION_MOVE: {
                bender.setX(ev.getX());

            }break;
/*            case MotionEvent.ACTION_UP:{
                System.out.println("Dispara");
                paquet = new Paquet(nauExpress.getX(), nauExpress.getY());
                paquets.add(paquet);
                disparar = true;
            }break;*/

        }
        invalidate();
        return true;
    }

/*
    public void dispararNau(float posX, float posY){
        paquet = new Paquet(posX, posY);
        int mov = 10;
        for (int i= 0; i < getHeight()/2+700; i++){
            paquet.setY(paquet.getY()+mov);
            System.out.println("disparant a tope" + i +" fins "+ getHeight()/2+700);
        }
        disparar = false;
        System.out.println("ACABA EL DISPAR");
    }
*/

    private boolean anant;
    public boolean movimentNau(){
        float mov = 5;
        if(nauExpress.getX() > getHeight()-1000){
            anant = false;
        }
        if(nauExpress.getX() < 0){
            anant = true;
        }
        //getHeight() = 1848
        if(anant){
            nauExpress.setX(nauExpress.getX()+mov);
        }else{
            nauExpress.setX(nauExpress.getX()-mov);
        }
        return anant;
    }
    private class MyAnimationThread extends Thread {
        private SurfaceHolder surfaceHolder;
        public boolean stop = false;
        //CONSTRUCTOR
        public MyAnimationThread(SurfaceHolder surfaceHolder) {
            this.surfaceHolder = surfaceHolder;
        }

        public void run() {
            while (!stop) {
                Canvas c = null;
                try {
                    c = surfaceHolder.lockCanvas(null);
                    synchronized (surfaceHolder) {
                        newDraw(c);
                    }
                }finally{
                    if(c != null){
                        surfaceHolder.unlockCanvasAndPost(c);
                    }
                }
            }
        }

    }

}
