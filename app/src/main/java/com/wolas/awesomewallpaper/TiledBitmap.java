package com.wolas.awesomewallpaper;

import android.graphics.Canvas;
import android.util.Log;

/**
 * Created by wolas on 16.2.16.
 */
public class TiledBitmap extends AdjustedBitmap {
    private String TAG= "TiledBitmap";

    private float topBoundary=0; //percents
    private float botBoundary=0;

    private int xTileCount=2;
    private int yTileCount=2;



    public TiledBitmap(String tileFile) {
        super(tileFile);
    }

    public void setTilingBoundaries(float topPercent, float botPercent) {
        this.topBoundary = topPercent;
        this.botBoundary = botPercent;

        xTileCount=deviceResolution.x / imgResolution.x + 2;

        float top = deviceResolution.y * topBoundary /100;
        float bot = deviceResolution.y * botBoundary /100;
        yTileCount=(int)(bot-top) / imgResolution.y + 2;

        coordinates.y = top;

        Log.d(TAG, "top " + top + " bot " + bot);
    }

    public void drawTiled(Canvas canvas) {

        float x = coordinates.x;
        float y = coordinates.y;

        for (int ii = 0; ii < yTileCount; ii++ ) {

            for (int i = 0; i < xTileCount; i++) {
                if (x > deviceResolution.x) {
                    x = x - xTileCount * imgResolution.x;
                }
                if (x < -imgResolution.x) {
                    x = x + imgResolution.x * xTileCount;
                }
                canvas.drawBitmap(bitmap, x, y, bitmapPaint);
                //Log.d(TAG, "coord x " + coordinates.x + " x " + x);
                x += imgResolution.x;
            }
            y += imgResolution.y;
        }

        coordinates.x += scrollingSpeedPercentX * deviceResolution.x / 100 ;

        if (coordinates.x > imgResolution.x * xTileCount) { //scrolling forward
            coordinates.x = 0;
        } else if (coordinates.x < imgResolution.x * -1 ) { //scrolling backward
            //coordinates.x =   deviceResolution.x + imgResolution.x;
            coordinates.x = imgResolution.x * xTileCount;
        }
    }
}
