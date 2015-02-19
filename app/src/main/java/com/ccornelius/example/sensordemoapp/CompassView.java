package com.ccornelius.example.sensordemoapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by craig on 2/16/15.
 * Ideas borrows defom vogella.com/tutorials/AndroidSensor/article.html
 */
public class CompassView extends View {

    private Paint paint;
    private float position = 0;

    public CompassView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Init();
    }

    private void Init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(2);
        paint.setTextSize(25);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int xPoint = getMeasuredWidth() / 2;
        int yPoint = getMeasuredHeight() / 2;

        float radius = (float)(Math.max(xPoint, yPoint) * 0.6);

        canvas.drawCircle(xPoint, yPoint, radius, paint);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), paint);

        // Put the line in direction of north, always. 90 offset is because N is at -90 degrees
        // math-wise.
        double radianVal = -(position +90)  / 180.0 * 3.14159;
        canvas.drawLine((float)xPoint, (float)yPoint,
                (float)(xPoint + radius * Math.cos(radianVal)),
                (float)(yPoint + radius * Math.sin(radianVal)),
                paint);

        canvas.drawText(String.valueOf(position), xPoint, yPoint, paint);

    }

    public void updateData(float position) {
        this.position = position;
        invalidate();
    }
}
