package com.wolas.awesomewallpaper;

import android.content.res.Configuration;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.SurfaceHolder;
import android.widget.Toast;

/**
 * Created by wolas on 16.1.25.
 */
public class AwesomeWallpaperService extends WallpaperService {
    public static final int frameDuration = 60;
    private String TAG = "WallpaperService";

    @Override
    public WallpaperService.Engine onCreateEngine() {
        return new AwesomeWallpaperEngine();
    }

    private class AwesomeWallpaperEngine extends WallpaperService.Engine {

        private boolean visible;
        private SurfaceHolder surfaceHolder;
        private Handler handler;
        private Animator animator;

        private Canvas canvas = null;

        private long cpuTime = 0; //Used even out FPS
        private long wholeFrameDuration=0;

        public AwesomeWallpaperEngine() {
            //android.os.Debug.waitForDebugger();
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

        private void draw() {
            if (visible) {
                cpuTime = System.currentTimeMillis();

                if (canvas == null) {
                    canvas = surfaceHolder.lockCanvas();
                }
                surfaceHolder.unlockCanvasAndPost(canvas);
//                wholeFrameDuration = System.currentTimeMillis() - wholeFrameDuration;
//                Log.d(TAG, "wholeFrameDuration " + wholeFrameDuration);
//                wholeFrameDuration = System.currentTimeMillis();

                canvas = surfaceHolder.lockCanvas();
                long time = System.currentTimeMillis();
                animator.draw(canvas);

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
    }






}
