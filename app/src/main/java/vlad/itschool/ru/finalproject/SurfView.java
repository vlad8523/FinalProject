package vlad.itschool.ru.finalproject;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Задача: отрисовка джойстика
 */

public class SurfView extends SurfaceView implements SurfaceHolder.Callback{
    float x,y;
    private DrawThr draw;
    public SurfView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        draw = new DrawThr(getContext(),getHolder(),getResources());
        draw.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        draw.requestStop();
        draw.bluetoothConnection.closeConnect();
        boolean retry = true;
        while(retry){
            try {
                draw.join();
                retry = false;
            }
            catch (InterruptedException e){}
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        x = event.getX();
        y = event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                draw.setTouchDown(x,y);
                break;
            case MotionEvent.ACTION_MOVE:
                draw.setTouchMove(x,y);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                draw.setTouchUp();
                break;
        }

        return true;
    }
}

class DrawThr extends Thread{
    private Bitmap backgroundJoystick,joystick;
    private SurfaceHolder surfaceHolder;
    private volatile boolean isRunning = true;
    private float xfirst,yfirst;
    private double r,Rad,x1,y1;
    private Rect rectBack,rectJoystick;
    private boolean isTouch = false;
    private int radiusJoystick = 200;
    public BluetoothConnection bluetoothConnection;
    /*
    * Конструктор
    */
    public DrawThr(Context context, SurfaceHolder surfaceHolder, Resources resources){
        this.surfaceHolder = surfaceHolder;

        backgroundJoystick = BitmapFactory.decodeResource(resources, R.drawable.background_joystick);
        joystick = BitmapFactory.decodeResource(resources,R.drawable.joystick);
        bluetoothConnection = new BluetoothConnection(context);
    }

    public void setTouchDown(float x,float y){
        xfirst = x;
        yfirst = y;
        isTouch = true;
        r = Math.sqrt(radiusJoystick*radiusJoystick);
        rectBack = new Rect((int)x-radiusJoystick,(int)y-radiusJoystick,(int)x+radiusJoystick,(int)y+radiusJoystick);
        rectJoystick = new Rect((int)x-48,(int)y-48,(int)x+48,(int)y+48);
        bluetoothConnection.send(0,0);
    }

    public void setTouchMove(float x,float y){
        Rad=Math.sqrt(Math.pow(x-xfirst,2)+Math.pow(y-yfirst,2));
        if(r>=Rad)  rectJoystick = new Rect((int)x-48,(int)y-48,(int)x+48,(int)y+48);
        else {
            y1 = (r*Math.abs(y-yfirst))/Rad*((y-yfirst)/Math.abs(y-yfirst))+yfirst;
            x1 = (r*Math.abs(x-xfirst))/Rad*((x-xfirst)/Math.abs(x-xfirst))+xfirst;
            rectJoystick = new Rect((int)x1-48,(int)y1-48,(int)x1+48,(int)y1+48);
        }
        bluetoothConnection.send(Math.abs(xfirst-x)*Math.abs(xfirst-x)/(xfirst-x),Math.abs(yfirst-y)*Math.abs(yfirst-y)/(yfirst-y));
    }

    public void setTouchUp(){
        isTouch = false;
        bluetoothConnection.send(0,0);
    }



    public void requestStop(){
        isRunning = false;
    }

    @Override
    public void run() {
        while(isRunning){
            Canvas canvas = surfaceHolder.lockCanvas();
            if(canvas != null){
                canvas.drawColor(Color.LTGRAY);
                try{
                    if (isTouch){
                        canvas.drawBitmap(backgroundJoystick,null,rectBack,null);
                        canvas.drawBitmap(joystick,null,rectJoystick,null);
                    }
                }
                finally{
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}