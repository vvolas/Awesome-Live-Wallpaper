package com.wolas.awesomewallpaper;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

/**
 * Created by wolas on 16.2.8.
 */
public class AwesomeSun extends AdjustedBitmap{

    private static String TAG = "AwesomeSun";
    private static String awesomeFile = "Awesome.png";

    private double sunRadius = 110;

    private int sunRaysCount = 20;
    private float sunRaysSpinTime = 200000;
    private float raysLenghtMultiplier = 3;
    //private PointF rayCoordinates;
    private Path rayTriangle;
    private boolean distortion = true;
    private Paint raysPaint;

    private long startingTime;
    private long timePassed;


    public AwesomeSun() {
        super(awesomeFile);

        setScaleToScreenRatio(scale);

        raysPaint = new Paint();
        raysPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        raysPaint.setAntiAlias(true);
        raysPaint.setColor(Color.rgb(254, 222, 88));

        rayTriangle = new Path();

        //rayCoordinates = new PointF();

        startingTime = System.currentTimeMillis();
    }

    @Override
    public void setScaleToScreenRatio(float scale) {
        super.setScaleToScreenRatio(scale);
        sunRadius=bitmap.getWidth()* ratioToScreen /2;
    }

    @Override
    public void draw (Canvas canvas) {
        timePassed = System.currentTimeMillis() - startingTime;

        drawRays(canvas);
        super.draw(canvas);
    }


    private void drawRays(Canvas canvas) {

        double stepDegrees = 360/sunRaysCount;

        double currentDegree = 0;

        float previousInnerX = 0;
        float previousInnerY = 0;

        sunRadius=bitmap.getWidth()* ratioToScreen /2;

        float degrees = 360 / sunRaysSpinTime * timePassed;

        rayTriangle.reset();

        for (int i = 0; i < sunRaysCount + 1; i++) {
            currentDegree = ((stepDegrees * i) + degrees) * Math.PI / 180;

            float xInner = (float) (centeredCoordinates.x + sunRadius * Math.cos(currentDegree));
            float yInner = (float) (centeredCoordinates.y + sunRadius * Math.sin(currentDegree));

            float xOuter;
            float yOuter;
            //apply sun ray distortion
//            if (distortion) {
                xOuter = (float) (centeredCoordinates.x + ((sunRadius + sunRadius*raysLenghtMultiplier) + Math.sin(i * 7)
                        * sunRadius*raysLenghtMultiplier / 4 + Math.sin(currentDegree * 5) * sunRadius*raysLenghtMultiplier / 2) * Math.cos(currentDegree));
                yOuter = (float) (centeredCoordinates.y + ((sunRadius + sunRadius*raysLenghtMultiplier) + Math.sin(i * 7)
                        * sunRadius*raysLenghtMultiplier / 4 + Math.sin(currentDegree * 5) * sunRadius*raysLenghtMultiplier / 2) * Math.sin(currentDegree));
//            }

            if (i == 0) {
                rayTriangle.moveTo(previousInnerX, previousInnerY);
            } else {
                rayTriangle.lineTo(previousInnerX, previousInnerY);
                rayTriangle.lineTo(xOuter, yOuter);
                rayTriangle.lineTo(xInner, yInner);
            }

            previousInnerX = xInner;
            previousInnerY = yInner;

        }
        rayTriangle.close();
        canvas.drawPath(rayTriangle, raysPaint);
    }
}