package com.wolas.awesomewallpaper;

import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.SurfaceHolder;

import java.util.Set;

/**
 * Created by wolas on 16.1.25.
 */
public class AwesomeWallpaperService extends WallpaperService {
    public static final int frameDuration = 60;
    private String TAG = "WallpaperService";

    private AwesomeWallpaperEngine engine;

    @Override
    public WallpaperService.Engine onCreateEngine() {
        if (engine!=null) {
           // engine.painting.stopPainting();
            Log.e(TAG, "DOUBLE ENGINEsss?");
            engine = null;
        }
        engine = new AwesomeWallpaperEngine();
        return engine;
    }


    private class AwesomeWallpaperEngine extends WallpaperService.Engine implements SharedPreferences.OnSharedPreferenceChangeListener {

        private boolean visible;
        private SurfaceHolder surfaceHolder;
        private Handler handler;
        private Animator animator;

        private Canvas canvas = null;

        SharedPreferences preferences;

        private long cpuTime = 0; //Used even out FPS
        private long wholeFrameDuration = 0;

        public AwesomeWallpaperEngine() {
            //android.os.Debug.waitForDebugger();

            preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            preferences.registerOnSharedPreferenceChangeListener(this);
            //onSharedPreferenceChanged(preferences, null);

            handler = new Handler();
            this.setTouchEventsEnabled(true);
            Utils utils = Utils.getInstance();
            //   utils.deleteContext();
            utils.putContext(getApplicationContext());
            animator = new Animator();

        }

        @Override
        public Bundle onCommand(String action, int x, int y, int z,
                                Bundle extras, boolean resultRequested) {

            Log.d(TAG, "action " + action);
            if (action.equals("android.wallpaper.tap")) {
                //TODO
            }

            return super.onCommand(action, x, y, z, extras, resultRequested);
        }

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            this.surfaceHolder = surfaceHolder;
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            Utils.getInstance().setDeviceResolution(width, height);
            animator.setScreenMode(getResources().getConfiguration().orientation);
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            visible = false;
            handler.removeCallbacks(drawFrame);
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            this.visible = visible;
            if (visible) {
                handler.post(drawFrame);
            } else {
                handler.removeCallbacks(drawFrame);
            }
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            handler.removeCallbacks(drawFrame);
            animator = null;
            canvas = null;
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            Log.v(TAG, "prefs changed");
            if (key.equals(SettingsActivity.B_FILTER) ) {
                SettingsActivity.BfilterValue=sharedPreferences.getBoolean(key,false);

            } else if (key.equals(SettingsActivity.AA_AND_DITHER)) {
                SettingsActivity.AAandDitherValue=sharedPreferences.getBoolean(key,false);
            }

            BitmapHolder.DeleteInstance();
            animator = new Animator();
            animator.setScreenMode(getResources().getConfiguration().orientation);
            Log.v(TAG, "quality changed in preference");
        }

        private void draw() {
            if (visible) {
                canvas =null;
                cpuTime = System.currentTimeMillis();

                //TODO This crashes in wallpaper selecting process when screen mode changing
                try {
                    canvas = surfaceHolder.lockCanvas();
                    if (canvas != null ) {
                        animator.draw(canvas);
                    }
                    surfaceHolder.unlockCanvasAndPost(canvas);
                } catch ( Exception e) {
                    e.printStackTrace();
                    handler.removeCallbacks(drawFrame);
                    return;
                }

                handler.removeCallbacks(drawFrame);

                cpuTime = System.currentTimeMillis() - cpuTime;

                cpuTime = frameDuration - cpuTime;
                if (cpuTime > 0 ) {
                    handler.postDelayed(drawFrame, cpuTime);
                } else {
                    //Log.d(TAG, "cpuTime is negative! posting at front " + cpuTime) ;
                    handler.postAtFrontOfQueue(drawFrame);
                }
            }
        }

        private Runnable drawFrame = new Runnable() {
            @Override
            public void run() {
                draw();
            }
        };

    }
}
