package com.wolas.awesomewallpaper;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

/**
 * Created by wolas on 16.2.11.
 */
public class Background {
    private static String TAG = "Background";

    private Paint backgroundPaint;
    private boolean dayTime = true;
    private boolean transitioning = false;

    private int dayColor = Color.rgb(135, 206, 250);
    private int nightColor = Color.rgb(0, 0, 0);

    private float currentRed = 0;
    private float currentGreen = 0;
    private float currentBlue = 0;

    private float redTransitionSpeed = 0;
    private float greenTransitionSpeed = 0;
    private float blueTransitionSpeed = 0;

    public Background() {
        backgroundPaint = new Paint();
        backgroundPaint.setStyle(Paint.Style.FILL);
        backgroundPaint.setColor(dayColor);
    }

    public void draw(Canvas canvas) {

        if (transitioning) {
            currentRed = currentRed + redTransitionSpeed;
            currentGreen = currentGreen + greenTransitionSpeed;
            currentBlue = currentBlue + blueTransitionSpeed;


            int currentColor = Color.rgb((int)currentRed, (int)currentGreen, (int)currentBlue);

            if (currentColor == dayColor || currentColor == nightColor) {
                transitioning = false;
            }
            backgroundPaint.setColor(currentColor);
        }

        canvas.drawPaint(backgroundPaint);
    }


    public void setTransitionTime(long transitionTime) {
        dayTime=!dayTime;
        transitioning = true;
        int red = 0,green = 0,blue = 0;

        if (dayTime) {
            red = Color.red(dayColor) - Color.red(nightColor);
            green = Color.green(dayColor) - Color.green(nightColor);
            blue = Color.blue(dayColor) - Color.blue(nightColor);

            currentRed = Color.red(nightColor);
            currentGreen = Color.green(nightColor);
            currentBlue = Color.blue(nightColor);

        } else {
            red = Color.red(nightColor) - Color.red(dayColor);
            green = Color.green(nightColor) - Color.green(dayColor);
            blue = Color.blue(nightColor) - Color.blue(dayColor);

            currentRed = Color.red(dayColor);
            currentGreen = Color.green(dayColor);
            currentBlue = Color.blue(dayColor);
        }

        redTransitionSpeed = red / (float)(transitionTime/AwesomeWallpaperService.frameDuration);
        greenTransitionSpeed = green / (float)(transitionTime/AwesomeWallpaperService.frameDuration);
        blueTransitionSpeed = blue / (float)(transitionTime/AwesomeWallpaperService.frameDuration);
    }
}
