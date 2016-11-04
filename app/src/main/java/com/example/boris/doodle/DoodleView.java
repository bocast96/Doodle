package com.example.boris.doodle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Boris on 11/3/2016.
 */

public class DoodleView extends View {

    private Paint paint = new Paint(), canvasPaint = new Paint();
    private Path path = new Path();
    private Canvas canvas;
    private Bitmap bitmap;

    public DoodleView(Context context) {
        super(context);
        init(null,0);
    }

    public DoodleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs,0);
    }

    public DoodleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs,defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyle){
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        canvasPaint.setFlags(Paint.DITHER_FLAG);

    }

    public void clear() {
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        bitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
    }

    @Override
    public void onDraw (Canvas canvas) {
        canvas.drawBitmap(bitmap, 0, 0, canvasPaint);
        canvas.drawPath(path, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){
        float xPos = motionEvent.getX();
        float yPos = motionEvent.getY();

        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                path.moveTo(xPos, yPos);
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(xPos,yPos);
                break;
            case MotionEvent.ACTION_UP:
                canvas.drawPath(path, paint);
                path.reset();
                break;
        }

        invalidate();
        return true;
    }


}
