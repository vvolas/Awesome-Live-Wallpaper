package com.wolas.awesomewallpaper;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.preference.PreferenceManager;
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
    protected boolean AntialiasingDither = false;
    protected boolean BFilter = false;

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
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(utils.getContext());
//        AntialiasingDither=prefs.getBoolean(SettingsActivity.AA_AND_DITHER, false);
//        BFilter=prefs.getBoolean(SettingsActivity.B_FILTER, false);
        bitmapPaint.setAntiAlias(SettingsActivity.AAandDitherValue);
        bitmapPaint.setDither(SettingsActivity.AAandDitherValue);
        bitmapPaint.setFilterBitmap(SettingsActivity.BfilterValue);
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
