package com.wolas.awesomewallpaper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import java.io.IOException;

/**
 * Created by wolas on 16.2.11.
 */
public class AdjustedBitmap extends CoordinateAdapter {
    private String TAG= "AdjustedBitmap";

    protected Bitmap bitmap;
    protected Paint bitmapPaint;
    protected String fileName;

    public AdjustedBitmap(String filename) {
        this.fileName = filename;
        bitmap = BitmapHolder.getInstance().getBitmap(filename);

        if (bitmap == null) {
            try {
                bitmap = BitmapFactory.decodeStream(Utils.getInstance().getContext().getResources().getAssets().open(filename));
                BitmapHolder.getInstance().putBitmap(filename, bitmap);
                Log.d(TAG, "Opening new bitmap " + filename);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        setImgResolution(bitmap.getWidth(),bitmap.getHeight());

        bitmapPaint = new Paint();
        //bitmapPaint.setAntiAlias(true);
        //bitmapPaint.setDither(true);
        bitmapPaint.setFilterBitmap(true);
    }

    public void setBitmapPaint(Paint paint) {
        bitmapPaint=paint;
    }

    public void draw(Canvas canvas) {

        if (visible) {
            canvas.save();
            canvas.rotate(rotation, centeredCoordinates.x, centeredCoordinates.y);
            canvas.scale(ratioToScreen, ratioToScreen, centeredCoordinates.x, centeredCoordinates.y);
            canvas.drawBitmap(bitmap, coordinates.x, coordinates.y, bitmapPaint);
            canvas.restore();
        }
        updateScrollLocation();
        //Log.d(TAG, "coordinates set to " + coordinates.x + " " + coordinates.y);
        //Log.d(TAG, "centeredCoordinates set to " + centeredCoordinates.x + " " + centeredCoordinates.y);
    }


}
