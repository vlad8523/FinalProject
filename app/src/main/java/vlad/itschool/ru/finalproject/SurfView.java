package vlad.itschool.ru.finalproject;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
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
    float x,y;
    Rect rectBack,rectJoystick;
    boolean isTouch = false;
    public void setTouchDown(float x,float y){
        this.x = x;
        this.y = y;
        isTouch = true;

        rectBack = new Rect((int)x-80,(int)y-80,(int)x+80,(int)y+80);
        rectJoystick = new Rect((int)x-48,(int)y-48,(int)x+48,(int)y+48);
    }

    public void setTouchMove(float x,float y){
        this.x = x;
        this.y = y;

        rectJoystick = new Rect((int)x-48,(int)y-48,(int)x+48,(int)y+48);
    }

    public void setTouchUp(){
        isTouch = false;
    }
    /*
* Конструктор
 */
    public DrawThr(Context context, SurfaceHolder surfaceHolder, Resources resources){
        this.surfaceHolder = surfaceHolder;

        backgroundJoystick = BitmapFactory.decodeResource(resources, R.drawable.background_joystick);
        joystick = BitmapFactory.decodeResource(resources,R.drawable.joystick);

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
