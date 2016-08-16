package com.wolas.awesomewallpaper;

import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by wolas on 16.2.9.
 */
public class Utils {
    private static final String TAG = "Utils";
    private Context context = null;
    private PointF resolution = null;
    private Integer orientation = null;
    private static Utils ref;

    private Utils() {    }

    public static Utils getInstance() {
        if (ref == null) {
            ref = new Utils();
        }
        return ref;
    }

    public void putContext(Context context) {
        if (this.context == null) {
            this.context = context;
            Log.d(TAG, "context was set");
        }
    }

    public void deleteContext() {
        this.context = null;
    }

    public Context getContext() {
        return context;
    }

    public PointF getDeviceResolution () {
        if (resolution == null) {
            resolution = new PointF();
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point out = new Point();
            display.getSize(out);
            resolution.set(out.x,out.y);
        }
        return resolution;
    }

    public void setDeviceResolution(int width, int height) {
        if (resolution == null) {
            resolution = new PointF();
        }
        this.resolution.x = width;
        this.resolution.y = height;

    }

    public Integer getOrientation() {
        return context.getResources().getConfiguration().orientation;
    }
}
